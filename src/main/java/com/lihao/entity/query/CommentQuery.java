package com.lihao.entity.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentQuery extends BaseParam {
	private String commentId;
	private String commentIdFuzzy;
	private String userId;
	private String userIdFuzzy;
	private String postId;
	private String postIdFuzzy;
	private String parentId;
	private String parentIdFuzzy;
	private String commentContent;
	private String commentContentFuzzy;
	private String commentDate;
	private String commentDateStart;
	private String commentDateEnd;
	private Integer commentStatus;
	private String topId;
	private Boolean isNull;
}
