package com.lihao.mapper;

import com.lihao.entity.po.Tag;
import com.lihao.entity.query.TagQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface TagMapper {
    Integer insertOrUpdate(String tag);
    List<Tag> select();
    Integer selectCount();
    List<Tag> selectList(@Param("query") TagQuery tagQuery);
}
