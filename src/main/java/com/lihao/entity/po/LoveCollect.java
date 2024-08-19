package com.lihao.entity.po;

import lombok.Data;

import java.util.Date;

@Data
public class LoveCollect {
    private String userId;
    private String postId;
    private Date loveTime;
    private Date collectTime;
    private Boolean love;
    private Boolean collect;
}
