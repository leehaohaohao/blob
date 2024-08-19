package com.lihao.controller;

import com.lihao.annotation.LNote;
import com.lihao.annotation.Login;
import com.lihao.annotation.Manager;
import com.lihao.constants.ExceptionConstants;
import com.lihao.entity.dto.ResponsePack;
import com.lihao.entity.po.Note;
import com.lihao.entity.po.Page;
import com.lihao.entity.query.NoteQuery;
import com.lihao.enums.FeedBackTypeEnum;
import com.lihao.enums.NoteEnum;
import com.lihao.enums.NoteTypeEnum;
import com.lihao.enums.UidPrefixEnum;
import com.lihao.exception.GlobalException;
import com.lihao.service.NoteService;
import com.lihao.util.StringUtil;
import com.lihao.util.Tools;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/note")
@CrossOrigin
public class NoteController extends BaseController {
    @Resource
    private NoteService noteService;
    @PostMapping("/publish")
    @LNote
    public ResponsePack publish(String content,Integer status) throws GlobalException {
        String userId = StringUtil.getUserId();
        if(content == null || content.isEmpty()){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        if(status != null && FeedBackTypeEnum.getTypeEnum(status) == null){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        if(status == null){
            status= NoteTypeEnum.SYSTEM.getStatus();
        }
        Note note = new Note();
        note.setNoteContent(content);
        note.setNoteDate(new Date());
        note.setNoteType(status);
        note.setNoteId(StringUtil.getId(UidPrefixEnum.NOTE.getPrefix()));
        note.setUserId(userId);
        noteService.publish(note);
        return getSuccessResponsePack(null);
    }
    @PostMapping("/select")
    public ResponsePack select(NoteQuery noteQuery){
        if(noteQuery.getNoteStatus() == null){
            noteQuery.setNoteStatus(NoteEnum.NORMAL.getStatus());
        }
        noteQuery.setNoteDate(new Date());
        if(noteQuery.getOrderBy()==null){
            noteQuery.setOrderBy("note_date desc");
        }
        return getSuccessResponsePack(noteService.select(noteQuery));
    }
    @PostMapping("/manager/select")
    @Login
    @Manager
    public ResponsePack managerSelect(Page page,Integer noteStatus,Integer noteType) throws GlobalException {
        if(page==null ||noteStatus == null || noteType == null){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        NoteQuery noteQuery = new NoteQuery();
        noteQuery.setNoteStatus(noteStatus);
        noteQuery.setPage(new Page(page.getPageSize(), page.getPageNum()));
        noteQuery.setNoteType(noteType);
        return getSuccessResponsePack(noteService.managerSelect(noteQuery));
    }
    @PostMapping("/manager/update")
    @Login
    @Manager
    public ResponsePack managerUpdate(Note note) throws GlobalException {
        if(note == null || Tools.isBlank(note.getNoteId())){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        NoteQuery noteQuery = new NoteQuery();
        noteQuery.setNoteId(note.getNoteId());
        note.setNoteId(null);
        note.setNoteDate(new Date());
        noteService.managerUpdate(note,noteQuery);
        return getSuccessResponsePack(null);
    }
}
