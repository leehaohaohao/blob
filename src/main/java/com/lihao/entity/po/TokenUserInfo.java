package com.lihao.entity.po;

import lombok.Data;

@Data
public class TokenUserInfo {
    private UserInfo userInfo;
    private String token;
}
