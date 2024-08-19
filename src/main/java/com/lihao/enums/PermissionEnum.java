package com.lihao.enums;

public enum PermissionEnum {
    PUBLISH_ARTICLES("P00000001","发布文章"),
    PUBLISH_COMMENTS("P00000003","发表评论"),
    EDIT_ARTICLES("P00000003","编辑文章"),
    DELETE_ARTICLES("P00000004","删除文章"),
    VIEW_ARTICLES("P00000005","查看文章"),
    PUBLISH_NOTE("P00000006","发布公告"),
    APPROVAL_POST("P00000007","审核文章");
    private String permissionId;
    private String type;

    public String getPermissionId() {
        return permissionId;
    }

    public String getType() {
        return type;
    }

    PermissionEnum(String permissionId, String type) {
        this.permissionId = permissionId;
        this.type = type;
    }
}
