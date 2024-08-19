package com.lihao.enums;

public enum JudgeEnum {
    ADD(0,"增加"),
    CANCEL(1,"取消");
    private Integer status;
    private String type;

    public Integer getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    JudgeEnum(Integer status, String type) {
        this.status = status;
        this.type = type;
    }
    public static JudgeEnum getJudgeEnumByStatus(Integer status){
        for(JudgeEnum judgeEnum:JudgeEnum.values()){
            if(judgeEnum.getStatus().equals(status)){
                return judgeEnum;
            }
        }
        return null;
    }
}
