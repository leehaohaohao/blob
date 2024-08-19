package com.lihao.entity.query;

import com.lihao.entity.po.Page;
import lombok.Data;

import java.util.Date;

@Data
public class FeedBackQuery {
    private String feedbackId;
    private String userId;
    private Integer status;
    private Date time;
    private String content;
    private String file;
    private Integer type;
    private String orderBy;
    private Page page;
}
