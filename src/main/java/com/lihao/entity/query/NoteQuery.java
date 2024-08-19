package com.lihao.entity.query;

import com.lihao.entity.po.Page;
import lombok.Data;

import java.util.Date;
@Data
public class NoteQuery {
    private String noteId;
    private String userId;
    private Date noteDate;
    private String noteContent;
    private Integer noteStatus;
    private Integer noteType;
    private String orderBy;
    private Page page;
}
