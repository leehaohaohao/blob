package com.lihao.entity.po;

import lombok.Data;

import java.util.Date;

@Data
public class Concern {
    private String userId;
    private String concernId;
    private Date concernTime;
}
