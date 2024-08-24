package com.lihao.entity.po;

import lombok.Data;

import java.util.Date;

@Data
public class Group {
    private String id;
    private String userId;
    private String avatar;
    private String name;
    private Date time;
    private Integer status;
}
