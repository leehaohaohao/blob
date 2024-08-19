package com.lihao.enums;

public enum RelationshipEnum {
    STRANGER(0,"陌生人"),
    CONCERN(1,"关注者"),
    FAN(2,"粉丝"),
    FRIEND(3,"朋友"),
    MINE(4,"自己");
    private Integer status;
    private String type;

    RelationshipEnum(Integer status, String type) {
        this.status = status;
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public static RelationshipEnum getRelationshipEnum(Integer status){
        for(RelationshipEnum relationshipEnum:RelationshipEnum.values()){
            if(relationshipEnum.getStatus().equals(status)){
                return relationshipEnum;
            }
        }
        return null;
    }
}
