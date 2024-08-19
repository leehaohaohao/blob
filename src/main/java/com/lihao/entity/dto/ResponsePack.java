package com.lihao.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponsePack<T> implements Serializable {
    private Boolean success;
    private T data;
    private String message;
}
