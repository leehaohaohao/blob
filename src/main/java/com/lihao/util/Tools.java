package com.lihao.util;


import com.lihao.constants.ExceptionConstants;
import com.lihao.exception.GlobalException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class Tools {
    public static HttpServletRequest getRequest() throws GlobalException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes == null){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
        return attributes.getRequest();
    }

    /**
     * 判断是否为空或者为null
     * @param vars 判断的字符串数组
     * @return
     */
    public static Boolean isBlank(String...vars){
        for(String var:vars){
            if(var == null || var.equals("null") || var.isEmpty()){
                return true;
            }
        }
        return false;
    }
}
