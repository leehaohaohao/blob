package com.lihao.entity.query;

import com.lihao.entity.po.Page;
import lombok.Data;


@Data
public class GroupCommentQuery {
    private String id;
    private String userId;
    private String groupId;
    private String orderBy;
    private Page page;
}
