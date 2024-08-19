package com.lihao.service;

import com.lihao.entity.po.FeedBack;
import com.lihao.entity.po.FeedBackType;
import com.lihao.entity.po.Page;
import com.lihao.exception.GlobalException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FeedBackService {
    String publish(FeedBack feedBack, MultipartFile file) throws GlobalException;
    List<FeedBackType> getType();
    List<FeedBack> get(Page page);
}
