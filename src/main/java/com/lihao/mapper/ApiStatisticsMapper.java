package com.lihao.mapper;

import com.lihao.entity.po.ApiStatistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApiStatisticsMapper {
    List<ApiStatistics> selectList();
    Integer insertOrUpdate(@Param("bean") ApiStatistics apiStatistics);
}
