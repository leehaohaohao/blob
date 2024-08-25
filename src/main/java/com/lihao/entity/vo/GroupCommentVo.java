package com.lihao.entity.vo;

import com.lihao.entity.dto.GroupCommentDto;
import lombok.Data;

import java.util.List;

@Data
public class GroupCommentVo {
    private List<GroupCommentDto> groupCommentDtos;
    private String ownerId;
}
