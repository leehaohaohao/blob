package com.lihao.service;

import com.lihao.entity.dto.NoteDto;
import com.lihao.entity.po.Note;
import com.lihao.entity.po.Page;
import com.lihao.entity.query.NoteQuery;
import com.lihao.exception.GlobalException;

import java.util.Date;
import java.util.List;

public interface NoteService {
    void publish(Note note) throws GlobalException;
    List<Note> select(NoteQuery noteQuery);
    List<NoteDto> managerSelect(NoteQuery noteQuery);
    void managerUpdate(Note note,NoteQuery noteQuery) throws GlobalException;
}
