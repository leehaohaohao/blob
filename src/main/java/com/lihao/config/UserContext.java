package com.lihao.config;

public class UserContext {
    private static ThreadLocal<String> userId = new ThreadLocal<>();
    public static void setUserId(String id) {
        userId.set(id);
    }
    public static String getUserId() {
        return userId.get();
    }
    public static void removeUserId() {
        userId.remove();
    }
}
