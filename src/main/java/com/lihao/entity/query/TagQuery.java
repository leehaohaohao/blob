package com.lihao.entity.query;

import com.lihao.entity.po.Page;
import lombok.Data;

@Data
public class TagQuery {
    private String tag;
    private Long nums;
    private String orderBy;
    private Page page;
}
