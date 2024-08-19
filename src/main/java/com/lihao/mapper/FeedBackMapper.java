package com.lihao.mapper;

import com.lihao.entity.po.FeedBack;
import com.lihao.entity.query.FeedBackQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FeedBackMapper {
    Integer insert(@Param("bean") FeedBack feedBack);
    List<FeedBack> selectList(@Param("query") FeedBackQuery feedBackQuery);
    Integer update(@Param("bean") FeedBack feedBack,@Param("query")FeedBackQuery feedBackQuery);
}
