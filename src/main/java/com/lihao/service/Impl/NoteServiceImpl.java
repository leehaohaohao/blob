package com.lihao.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lihao.constants.ExceptionConstants;
import com.lihao.constants.NumberConstants;
import com.lihao.entity.dto.NoteDto;
import com.lihao.entity.po.Note;
import com.lihao.entity.po.Page;
import com.lihao.entity.po.UserInfo;
import com.lihao.entity.query.NoteQuery;
import com.lihao.entity.query.UserQuery;
import com.lihao.exception.GlobalException;
import com.lihao.mapper.NoteMapper;
import com.lihao.mapper.UserInfoMapper;
import com.lihao.service.NoteService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {
    @Resource
    private NoteMapper<Note,NoteQuery> noteMapper;
    @Resource
    private UserInfoMapper<UserInfo, UserQuery> userInfoMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publish(Note note) throws GlobalException {
        if(!noteMapper.insert(note).equals(NumberConstants.DEFAULT_UPDATE_INSERT)){
            throw new GlobalException(ExceptionConstants.PUBLISH_FAIL);
        }
    }

    @Override
    public List<Note> select(NoteQuery noteQuery) {
        return noteMapper.select(noteQuery);
    }

    @Override
    public List<NoteDto> managerSelect(NoteQuery noteQuery) {
        List<Note> notes = noteMapper.select(noteQuery);
        List<NoteDto> noteDtos = notes.parallelStream().map(note -> {
            NoteDto noteDto = new NoteDto();
            UserInfo userInfo = userInfoMapper.selectByUserId(note.getUserId());
            BeanUtils.copyProperties(note,noteDto);
            noteDto.setName(userInfo.getName());
            return noteDto;
        }).toList();
        return noteDtos;
    }

    @Override
    @Transactional
    public void managerUpdate(Note updateNote, NoteQuery noteQuery) throws GlobalException {
        //检查公告是否存在
        Note note = noteMapper.selectById(noteQuery, noteQuery.getNoteId());
        if(note == null){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        if(!noteMapper.update(updateNote,noteQuery).equals(1)){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
    }
}
