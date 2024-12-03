package com.lihao.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lihao.constants.StringConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class UserInfoDto {
    private String userId;
    private String name;
    @Pattern(regexp = "^1[3-9]\\d{9}$",message = "电话号码格式不正确！")
    private String telephone;
    private Integer gender;
    @Email
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastOffTime;
    private String photo;
    private Long followers;
    private Integer concern;
    private Integer post;
    private String lastPostId;
    private String selfTag;
    private Integer status;
    private Integer love;
    private Integer collect;
    public void setPhoto(String photo) {
        this.photo = StringConstants.URL+photo;
    }
}
