package com.lihao.enums;

public enum UidPrefixEnum {
    USER("U","用户"),
    POST("P","帖子"),
    FEEDBACK("FB","问题反馈"),
    HISTORY("H","历史浏览"),
    NOTE("N","公告"),
    COMMENT("C","评论"),
    GROUP("G","群组"),
    GROUP_COMMENT("GC","群组评论");
    private String prefix;
    private String type;
    public String getPrefix() {
        return prefix;
    }

    public String getType() {
        return type;
    }

    UidPrefixEnum(String prefix, String type) {
        this.prefix = prefix;
        this.type = type;
    }
}
