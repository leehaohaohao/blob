package com.lihao.enums;

public enum UserStatusEnum {
    NORMAL(0,"正常"),
    ABNORMAL(1,"异常");
    private Integer status;
    private String type;

    UserStatusEnum(Integer status, String type) {
        this.status = status;
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public static UserStatusEnum getUserStatusEnum(Integer status){
        for(UserStatusEnum statusEnum:UserStatusEnum.values()){
            if(statusEnum.getStatus().equals(status)){
                return statusEnum;
            }
        }
        return null;
    }
}
