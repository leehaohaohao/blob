package com.lihao.entity.po;

import lombok.Data;

import java.util.Date;

@Data
public class Note {
    private String noteId;
    private String userId;
    private Date noteDate;
    private String noteContent;
    private Integer noteStatus;
    private Integer noteType;
}
