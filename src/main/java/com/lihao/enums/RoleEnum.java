package com.lihao.enums;

public enum RoleEnum {
    ADMIN("R00000001","管理员"),
    NORMAL("R00000002","普通用户");
    private String roleId;
    private String type;

    public String getRoleId() {
        return roleId;
    }

    public String getType() {
        return type;
    }

    RoleEnum(String roleId, String type) {
        this.roleId = roleId;
        this.type = type;
    }
}
