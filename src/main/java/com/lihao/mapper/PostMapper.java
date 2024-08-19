package com.lihao.mapper;

import com.lihao.entity.dto.PostCoverDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  数据库操作接口
 */
public interface PostMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 根据PostId更新
	 */
	 Integer updateByPostId(@Param("bean") T t,@Param("postId") String postId);


	/**
	 * 根据PostId删除
	 */
	 Integer deleteByPostId(@Param("postId") String postId);


	/**
	 * 根据PostId获取对象
	 */
	 T selectByPostId(@Param("postId") String postId);
	 List<T> selectRandom(@Param("status") Integer status,@Param("pageSize")Integer pageSize);
	 List<T> selectMainList(@Param("query") P p);
	 List<PostCoverDto> selectCoverList(@Param("query")P p);
	 Integer selectCount(Integer status);
}
