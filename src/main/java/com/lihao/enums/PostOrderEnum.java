package com.lihao.enums;

public enum PostOrderEnum {
    SORT_LIKE(0,"post_like desc"),
    SORT_COLLECT(1,"collect desc"),
    SORT_TIME(2,"post_time desc");
    private Integer status;
    private String type;

    public Integer getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    PostOrderEnum(Integer status, String type) {
        this.status = status;
        this.type = type;
    }
    public static PostOrderEnum getPostOrderEnumByStatus(Integer status){
        for(PostOrderEnum postOrderEnum:PostOrderEnum.values()){
            if(postOrderEnum.getStatus().equals(status)){
                return postOrderEnum;
            }
        }
        return null;
    }
}
