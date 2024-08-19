package com.lihao.entity.query;

import lombok.Data;

@Data
public class BaseParam {
	private Integer pageNo;
	private Integer pageSize;
	private String orderBy;
}
