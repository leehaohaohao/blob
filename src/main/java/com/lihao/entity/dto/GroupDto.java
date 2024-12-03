package com.lihao.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lihao.constants.StringConstants;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class GroupDto {
    private String id;
    private String userId;
    private String avatar;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    private Integer status;
    private String userName;
    public void setAvatar(String avatar) {
        if(avatar.contains(StringConstants.URL)){
            this.avatar = avatar;
            return;
        }
        this.avatar = StringConstants.URL+avatar;
    }
}
