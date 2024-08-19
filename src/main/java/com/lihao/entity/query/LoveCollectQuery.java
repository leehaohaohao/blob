package com.lihao.entity.query;

import com.lihao.entity.po.Page;
import lombok.Data;

import java.util.Date;
@Data
public class LoveCollectQuery {
    private String userId;
    private String postId;
    private Date loveTime;
    private Date collectTime;
    private Boolean love;
    private Boolean collect;
    private Page page;
    private String orderBy;
}
