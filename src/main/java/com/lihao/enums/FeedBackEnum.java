package com.lihao.enums;

public enum FeedBackEnum {
    UNRESOLVED(0,"未解决"),
    RESOLVED(1,"已解决");
    private Integer status;
    private String type;

    public Integer getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    FeedBackEnum(Integer status, String type) {
        this.status = status;
        this.type = type;
    }
}
