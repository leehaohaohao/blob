
package com.lihao.entity.po;

import lombok.Data;

@Data
public class ApiStatistics {
    private String id;
    private String name;
    private long count;
    private long maxTime;
    private double averageTime;
    private long minTime;
}
