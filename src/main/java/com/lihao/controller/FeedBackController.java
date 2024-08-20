package com.lihao.controller;

import com.lihao.annotation.Login;
import com.lihao.annotation.Manager;
import com.lihao.constants.ExceptionConstants;
import com.lihao.entity.dto.FeedBackDto;
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
import com.lihao.util.Tools;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/error")
@CrossOrigin
public class FeedBackController extends BaseController {
    @Resource
    private FeedBackService feedBackService;
    @PostMapping("/publish")
    @Login
    public ResponsePack publish(Integer status, String content,MultipartFile file) throws GlobalException {
        String userId = StringUtil.getUserId();
        if(Tools.isBlank(content)){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        Optional.ofNullable(status)
                .filter(s -> FeedBackTypeEnum.getTypeEnum(s) != null)
                .orElseThrow(() -> new GlobalException(ExceptionConstants.INVALID_PARAM));

        status = Optional.ofNullable(status).orElse(FeedBackTypeEnum.OTHER.getStatus());
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
    @Login
    @Manager
    public ResponsePack get(Page page) throws GlobalException {
        CheckUtil.checkPage(page);
        return getSuccessResponsePack(feedBackService.get(page));
    }
    @PostMapping("/update")
    @Login
    @Manager
    public ResponsePack update(@RequestBody FeedBack feedBack) throws GlobalException {
        Optional.ofNullable(feedBack.getFeedbackId())
                .orElseThrow(() -> new GlobalException(ExceptionConstants.INVALID_PARAM));
        feedBackService.update(feedBack);
        return getSuccessResponsePack(null);
    }
}
