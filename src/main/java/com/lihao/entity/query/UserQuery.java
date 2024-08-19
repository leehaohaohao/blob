package com.lihao.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lihao.entity.po.Page;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UserQuery {
    private String userId;
    private String photo;
    private String name;
    private Boolean nameFuzzy = false;
    @Pattern(regexp = "^1[3-9]\\d{9}$",message = "电话号码格式不正确！")
    private String telephone;
    @Size(min = 1,max = 1,message = "长度必须为1")
    private int gender;
    @Email
    private String email;
    @Length(min = 8,max = 20,message = "密码长度必须为8-20之间")
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastOffTime;
    private Integer status;
    private String orderBy;
    private Page page;
}
