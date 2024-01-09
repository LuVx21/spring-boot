package org.luvx.boot.flowable.util;

public class SecurityUtils {
    public static final ThreadLocal<String> userIdHolder = new ThreadLocal<>();

    public static String getUserId() {
        return userIdHolder.get();
    }

    public static void setUserId(String userId) {
        userIdHolder.set(userId);
    }
}
