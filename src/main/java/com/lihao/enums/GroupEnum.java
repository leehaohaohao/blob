package com.lihao.enums;



public enum GroupEnum {
    NORMAL(0,"正常"),
    ABNORMAL(1,"不正常");
    private Integer status;
    private String type;

    GroupEnum(Integer status, String type) {
        this.status = status;
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }
    public static GroupEnum getGroupEnum(Integer status){
        for(GroupEnum groupEnum : GroupEnum.values()){
            if(groupEnum.getStatus().equals(status)){
                return groupEnum;
            }
        }
        return null;
    }

}
