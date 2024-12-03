package com.lihao.entity.dto;

import com.lihao.constants.StringConstants;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PostDto {
    private String postId;
    private String userId;
    private String photo;
    private String name;
    private String tag;
    private String title;
    private Integer status;
    private String postContent;
    private Integer postLike;
    private Integer collect;
    private String cover;
    private Date postTime;
    private Boolean isLove;
    private Boolean isCollect;
    private List<CommentDto> parentCommentDto;
    public void setCover(String cover){
        this.cover= StringConstants.URL+cover;
    }

}
