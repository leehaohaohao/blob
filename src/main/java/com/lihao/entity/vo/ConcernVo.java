package com.lihao.entity.vo;

import com.lihao.entity.po.Page;
import lombok.Data;

@Data
public class ConcernVo {
    private Page page;
    private Integer status;
    private String otherId;
}
