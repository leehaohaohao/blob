package com.lihao.service;

import com.lihao.constants.RedisConstants;
import com.lihao.entity.po.ApiStatistics;
import com.lihao.entity.po.Post;
import com.lihao.entity.query.PostQuery;
import com.lihao.mapper.ApiStatisticsMapper;
import com.lihao.mapper.PostMapper;
import com.lihao.redis.RedisUtils;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class ScheduledService {
    @Resource
    private PostMapper<Post, PostQuery> postMapper;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ApiStatisticsMapper apiStatisticsMapper;
    @Scheduled(fixedRate = 240000)
    public void updateRand(){
        postMapper.updateRand();
    }
    @Scheduled(fixedRate = 10000)
    @Transactional
    public void storageApi(){
        Map<String, ApiStatistics> apiMap = redisUtils.hgetAll(RedisConstants.REDIS_API);
        for(Map.Entry<String, ApiStatistics> entry:apiMap.entrySet()){
            apiStatisticsMapper.insertOrUpdate(entry.getValue());
        }
    }
}
