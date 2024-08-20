package com.lihao.controller;

import com.lihao.annotation.Login;
import com.lihao.annotation.Manager;
import com.lihao.constants.ExceptionConstants;
import com.lihao.entity.dto.ResponsePack;
import com.lihao.entity.po.Page;
import com.lihao.entity.po.UserInfo;
import com.lihao.enums.UserStatusEnum;
import com.lihao.exception.GlobalException;
import com.lihao.service.BackService;
import com.lihao.util.CheckUtil;
import com.lihao.util.Tools;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/back")
@CrossOrigin
public class BackController extends BaseController{
    @Resource
    private BackService backServiceImpl;

    /**
     * 获取网站的一些数据信息
     * @return
     */
    @GetMapping("/num")
    @Login
    @Manager
    public ResponsePack num(){
        return getSuccessResponsePack(backServiceImpl.num());
    }

    /**
     * 获取热点文章
     * @param page
     * @return
     * @throws GlobalException
     */
    @PostMapping("/hot/post")
    @Login
    @Manager
    public ResponsePack getHotPost(Page page) throws GlobalException {
        CheckUtil.checkPage(page);
        return getSuccessResponsePack(backServiceImpl.getHotPost(page));
    }

    /**
     * 获取热门话题
     * @param page
     * @return
     * @throws GlobalException
     */
    @PostMapping("/hot/tag")
    @Login
    @Manager
    public ResponsePack getHotTag(Page page) throws GlobalException{
        CheckUtil.checkPage(page);
        return getSuccessResponsePack(backServiceImpl.getHotTag(page));
    }

    /**
     * 获取人员信息
     * @param page 分页信息
     * @return
     * @throws GlobalException
     */
    @PostMapping("/person")
    @Login
    @Manager
    public ResponsePack getPerson(Page page,Integer status) throws GlobalException {
        CheckUtil.checkPage(page);
        if(UserStatusEnum.getUserStatusEnum(status)==null){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        return getSuccessResponsePack(backServiceImpl.getPerson(page,status));
    }

    /**
     * 管理员更新用户信息
     * @param userInfo 用户信息
     * @return
     */
    @PostMapping("/update/person")
    @Login
    @Manager
    public ResponsePack updatePerson(UserInfo userInfo, MultipartFile file) throws GlobalException {
        if(userInfo == null || Tools.isBlank(userInfo.getUserId())){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        UserInfo updateInfo = new UserInfo();
        updateInfo.setUserId(userInfo.getUserId());
        if(Tools.isBlank(userInfo.getTelephone())){
            userInfo.setTelephone(null);
        }
        updateInfo.setTelephone(userInfo.getTelephone());
        if(Tools.isBlank(userInfo.getName())){
            userInfo.setName(null);
        }
        updateInfo.setName(userInfo.getName());
        updateInfo.setGender(userInfo.getGender());
        updateInfo.setStatus(userInfo.getStatus());
        backServiceImpl.updatePerson(updateInfo,file);
        return getSuccessResponsePack(null);
    }
}
