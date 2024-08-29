package com.lihao.entity.po;

import lombok.Data;

@Data
public class ApiStatistics {
    private String id;
    private long count;
    private long time;
    private long averageTime;
}
