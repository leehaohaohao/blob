package com.lihao.service.Impl;

import com.lihao.constants.ExceptionConstants;
import com.lihao.constants.NumberConstants;
import com.lihao.constants.StringConstants;
import com.lihao.entity.po.FeedBack;
import com.lihao.entity.po.FeedBackType;
import com.lihao.entity.po.Page;
import com.lihao.entity.query.FeedBackQuery;
import com.lihao.exception.GlobalException;
import com.lihao.mapper.FeedBackMapper;
import com.lihao.mapper.FeedBackTypeMapper;
import com.lihao.service.FeedBackService;
import com.lihao.util.CheckUtil;
import com.lihao.util.FileUtil;
import com.lihao.util.StringUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FeedBackServiceImpl implements FeedBackService {
    @Resource
    private FeedBackMapper feedBackMapper;
    @Resource
    private FeedBackTypeMapper feedBackTypeMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String publish(FeedBack feedBack, MultipartFile file) throws GlobalException {
        String[] ss = null;
        if(CheckUtil.checkFile(file,false)){
            CheckUtil.checkImageType(file);
            ss = FileUtil.fileBookLoad(file, StringUtil.getFeedBackPath());
        }
        if(ss!=null && ss.length == NumberConstants.FILE_ARRAY_LENGTH){
            feedBack.setFile(ss[0]);
        }
        if(!feedBackMapper.insert(feedBack).equals(NumberConstants.DEFAULT_UPDATE_INSERT)){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
        return StringConstants.SOLVE_IT;
    }

    @Override
    public List<FeedBackType> getType() {
        return feedBackTypeMapper.selectList();
    }

    @Override
    public List<FeedBack> get(Page page) {
        FeedBackQuery feedBackQuery = new FeedBackQuery();
        feedBackQuery.setPage(page);
        feedBackQuery.setOrderBy("time desc");
        feedBackQuery.setStatus(0);
        return feedBackMapper.selectList(feedBackQuery);
    }
}
