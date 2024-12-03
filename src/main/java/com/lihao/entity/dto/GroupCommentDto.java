package com.lihao.entity.dto;

import com.lihao.constants.StringConstants;
import lombok.Data;


@Data
public class GroupCommentDto {
    private String ownerId;
    private String groupId;
    private String userId;
    private String name;
    private String avatar;
    private String content;
    private Integer status;
    public void setAvatar(String avatar) {
        this.avatar = StringConstants.URL+avatar;
    }
}
