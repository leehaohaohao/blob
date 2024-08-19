package com.lihao.controller;

import com.lihao.annotation.Login;
import com.lihao.constants.ExceptionConstants;
import com.lihao.entity.dto.ResponsePack;
import com.lihao.entity.po.FeedBack;
import com.lihao.entity.po.Page;
import com.lihao.enums.FeedBackEnum;
import com.lihao.enums.FeedBackTypeEnum;
import com.lihao.enums.UidPrefixEnum;
import com.lihao.exception.GlobalException;
import com.lihao.service.FeedBackService;
import com.lihao.util.CheckUtil;
import com.lihao.util.StringUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("/error")
@CrossOrigin
public class FeedBackController extends BaseController {
    @Resource
    private FeedBackService feedBackService;
    @PostMapping("/publish")
    @Login
    public ResponsePack publish(HttpServletRequest request ,Integer status, String content,MultipartFile file) throws GlobalException {
        String userId = StringUtil.getUserId(request);
        if(content == null || content.isEmpty()){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        if(status != null && FeedBackTypeEnum.getTypeEnum(status) == null){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        if(status == null){
            status= FeedBackTypeEnum.OTHER.getStatus();
        }
        FeedBack feedBack = new FeedBack();
        feedBack.setContent(content);
        feedBack.setFeedbackId(StringUtil.getId(UidPrefixEnum.FEEDBACK.getPrefix()));
        feedBack.setUserId(userId);
        feedBack.setStatus(FeedBackEnum.UNRESOLVED.getStatus());
        feedBack.setType(status);
        feedBack.setTime(new Date());
        return getSuccessResponsePack(feedBackService.publish(feedBack,file));
    }
    @GetMapping("/getType")
    @Login
    public ResponsePack getType(){
        return getSuccessResponsePack(feedBackService.getType());
    }
    @PostMapping("/get")
    public ResponsePack get(Page page) throws GlobalException {
        CheckUtil.checkPage(page);
        return getSuccessResponsePack(feedBackService.get(page));
    }
}
