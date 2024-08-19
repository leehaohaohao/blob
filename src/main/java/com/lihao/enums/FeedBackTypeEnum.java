package com.lihao.enums;

public enum FeedBackTypeEnum {
    BUG(1,"页面bug"),
    OPTIMIZE(2,"优化"),
    FUNCTION(3,"功能"),
    DATA(4,"数据丢失"),
    SAFE(5,"安全"),
    OTHER(6,"其他");
    private Integer status;
    private String type;

    public Integer getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    FeedBackTypeEnum(Integer status, String type) {
        this.status = status;
        this.type = type;
    }
    public static FeedBackTypeEnum getTypeEnum(Integer status){
        for(FeedBackTypeEnum typeEnum : FeedBackTypeEnum.values()){
            if(typeEnum.getStatus().equals(status)){
                return typeEnum;
            }
        }
        return null;
    }
}
