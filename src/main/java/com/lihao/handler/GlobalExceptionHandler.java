package com.lihao.handler;

import com.lihao.constants.ExceptionConstants;
import com.lihao.entity.dto.ResponsePack;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponsePack<String> clasp(Exception e){
        e.printStackTrace();
        ResponsePack<String> responsePack = new ResponsePack<>();
        responsePack.setSuccess(false);
        if(e instanceof PersistenceException){
            responsePack.setMessage(ExceptionConstants.INVALID_PARAM);
        }else if(e instanceof DataIntegrityViolationException){
            responsePack.setMessage(ExceptionConstants.INVALID_PARAM);
        }else if(e instanceof MailAuthenticationException){
            responsePack.setMessage(ExceptionConstants.EMAIL_FAULT);
        }else if(e instanceof NullPointerException){
            responsePack.setMessage(ExceptionConstants.INVALID_PARAM);
        }else if(e instanceof NoResourceFoundException){
            responsePack.setMessage(ExceptionConstants.INVALID_RESOURCE);
        }else if(e instanceof QueryTimeoutException){
            responsePack.setMessage(ExceptionConstants.SERVER_ERROR);
        }else if(e instanceof BadSqlGrammarException){
            responsePack.setMessage(ExceptionConstants.INVALID_PARAM);
        }else if(e instanceof ExpiredJwtException){
            responsePack.setMessage(ExceptionConstants.EXPIRE_LOG);
        }else{
            responsePack.setMessage(e.getMessage());
        }
        return responsePack;
    }
}
