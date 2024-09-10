package com.lihao.enums;

public enum NoteEnum {
    NORMAL(0,"正常"),
    DELETE(1,"删除");
    private Integer status;
    private String type;

    public Integer getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    NoteEnum(Integer status, String type) {
        this.status = status;
        this.type = type;
    }
    public static NoteEnum getTypeEnum(Integer status){
        for(NoteEnum typeEnum : NoteEnum.values()){
            if(typeEnum.getStatus().equals(status)){
                return typeEnum;
            }
        }
        return null;
    }
}
