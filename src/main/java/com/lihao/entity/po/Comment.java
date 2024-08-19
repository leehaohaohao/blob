package com.lihao.entity.po;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private String commentId;
    private String userId;
    private String postId;
    private String parentId;
    private String commentContent;
    private String topId;
    private Date commentDate;
    private Integer commentStatus;
}
