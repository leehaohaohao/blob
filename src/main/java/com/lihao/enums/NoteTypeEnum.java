package com.lihao.enums;

public enum NoteTypeEnum {
    SYSTEM(0,"系统公告"),
    ACTIVITY(1,"活动公告");
    private Integer status;
    private String type;

    public Integer getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    NoteTypeEnum(Integer status, String type) {
        this.status = status;
        this.type = type;
    }
}
