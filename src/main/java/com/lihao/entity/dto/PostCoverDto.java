package com.lihao.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PostCoverDto {
    private String userId;
    private String postId;
    private String tag;
    private Date postTime;
    private Integer postLike;
    private Integer collect;
    private String title;
    private String cover;
    private OtherInfoDto otherInfoDto;
}
