package com.lihao.service.Impl;

import com.lihao.redis.RedisTools;
import com.lihao.util.CommonUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {
    private Integer code;
    @Value("${spring.mail.username}")
    private String from="";
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private CommonUtil commonUtil;
    @Resource
    private RedisTools redisTools;
    public String email(String to) {
        //获取邮箱验证码，10分钟内多次请求发送同一个码
        code = redisTools.getEmailCode(to);
        if(code == null){
            code = Integer.valueOf(commonUtil.getRandomNum(6));
            redisTools.setEmailCode(code,to);
        }
        //发送邮件的一些配置
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("lihao-blob");
        simpleMailMessage.setText("邮箱验证码:" + code.toString()+"请在10分钟内输入此验证码，否则将会过期失效！");
        javaMailSender.send(simpleMailMessage);
        return code.toString();
    }
}
