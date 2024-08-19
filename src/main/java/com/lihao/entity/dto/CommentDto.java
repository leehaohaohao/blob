package com.lihao.entity.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentDto {
    private String commentId;
    private String userId;
    private String photo;
    private String userName;
    private String parentId;
    private String parentName;
    private String commentContent;
    private String topId;
    private Date commentDate;
    private List<CommentDto> childCommentDto;
}
