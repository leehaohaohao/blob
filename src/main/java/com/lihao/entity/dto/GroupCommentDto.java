package com.lihao.entity.dto;

import lombok.Data;


@Data
public class GroupCommentDto {
    private String userId;
    private String name;
    private String avatar;
    private String content;
    private Integer status;
}
