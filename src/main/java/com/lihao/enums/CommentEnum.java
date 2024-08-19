package com.lihao.enums;

public enum CommentEnum {
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

    CommentEnum(Integer status, String type) {
        this.status = status;
        this.type = type;
    }
    public static CommentEnum getCommentEnumByStatus(Integer status){
        for(CommentEnum commentEnum:CommentEnum.values()){
            if(commentEnum.getStatus().equals(status)){
                return commentEnum;
            }
        }
        return null;
    }
}
