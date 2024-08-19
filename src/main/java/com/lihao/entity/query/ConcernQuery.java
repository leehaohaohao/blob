package com.lihao.entity.query;

import com.lihao.entity.po.Page;
import lombok.Data;

import java.util.Date;

@Data
public class ConcernQuery {
    private String userId;
    private String concernId;
    private Date concernTime;
    private String orderBy;
    private Page page;
}
