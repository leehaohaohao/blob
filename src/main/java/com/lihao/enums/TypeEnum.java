package com.lihao.enums;

public enum TypeEnum {
    LOVE(0,"点赞"),
    COLLECT(1,"收藏");
    private Integer status;
    private String type;

    public Integer getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    TypeEnum(Integer status, String type) {
        this.status = status;
        this.type = type;
    }
    public static TypeEnum getTypeEnumByStatus(Integer status){
        for(TypeEnum typeEnum:TypeEnum.values()){
            if(typeEnum.getStatus().equals(status)){
                return typeEnum;
            }
        }
        return null;
    }
}
