package com.lihao.enums;

public enum PostEnum {
    NORMAL(0,"正常"),
    DELETED(1,"不正常"),
    REVIEWING(2,"审核中"),
    REVIEWING_FAULT(3,"审核失败");
    private Integer status;
    private String type;

    PostEnum(Integer status, String type) {
        this.type = type;
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public static PostEnum getPostEnum(Integer status){
        for(PostEnum postEnum : PostEnum.values()){
            if(postEnum.getStatus().equals(status)){
                return postEnum;
            }
        }
        return null;
    }
}
