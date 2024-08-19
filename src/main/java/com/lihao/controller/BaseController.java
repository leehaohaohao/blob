package com.lihao.controller;

import com.lihao.entity.dto.ResponsePack;

public class BaseController<T> {
    public ResponsePack<T> getSuccessResponsePack(T t){
        ResponsePack<T> responsePack = new ResponsePack<>();
        responsePack.setSuccess(true);
        responsePack.setData(t);
        return responsePack;
    }
}
