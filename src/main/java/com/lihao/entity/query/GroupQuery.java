package com.lihao.entity.query;

import com.lihao.entity.po.Page;
import lombok.Data;


@Data
public class GroupQuery {
    private String id;
    private String userId;
    private String name;
    private Integer status;
    private String orderBy;
    private Page page;
}
