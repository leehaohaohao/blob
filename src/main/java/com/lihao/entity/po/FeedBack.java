package com.lihao.entity.po;

import lombok.Data;

import java.util.Date;
@Data
public class FeedBack {
    private String feedbackId;
    private String userId;
    private Integer status;
    private Date time;
    private String content;
    private String file;
    private Integer type;
}
