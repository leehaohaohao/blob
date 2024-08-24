package com.lihao.entity.po;
import lombok.Data;

import java.util.Date;

@Data
public class GroupComment {
    private String id;
    private String userId;
    private String groupId;
    private String content;
    private Date time;
}
