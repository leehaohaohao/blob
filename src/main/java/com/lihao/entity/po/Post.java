package com.lihao.entity.po;

import lombok.Data;

import java.util.Date;

@Data
public class Post {
    private String postId;
    private String userId;
    private String postContent;
    private String tag;
    private Date postTime;
    private Integer postLike;
    private Integer collect;
    private Integer postStatus;
    private String title;
    private String cover;
    private Date approvalTime;
    private String approvalId;
}
