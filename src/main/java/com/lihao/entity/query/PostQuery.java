package com.lihao.entity.query;

import com.lihao.entity.po.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostQuery extends BaseParam {
	private String postId;
	private String postIdFuzzy;
	private String userId;
	private String userIdFuzzy;
	private String postContent;
	private String postContentFuzzy;
	private String tag;
	private String tagFuzzy;
	private String postTime;
	private String postTimeStart;
	private String postTimeEnd;
	private Long postLike;
	private Long collect;
	private Integer postStatus;
	private String title;
	private String titleFuzzy;
	private String cover;
	private Date approvalTimeEnd;
	private String approvalId;
	private String approvalIdFuzzy;
	private Page page;
}
