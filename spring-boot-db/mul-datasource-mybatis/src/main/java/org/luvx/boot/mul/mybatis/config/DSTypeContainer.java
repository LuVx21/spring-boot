package org.luvx.boot.mul.mybatis.config;

import org.apache.commons.lang3.StringUtils;

public class DSTypeContainer {
    private static final ThreadLocal<String> TYPE = new ThreadLocal<>();

    public static String defaultType;

    public static void setDataBaseType(String dataBase) {
        if (StringUtils.isBlank(dataBase)) {
            dataBase = defaultType;
        }
        TYPE.set(dataBase);
    }

    public static String getDataBaseType() {
        return TYPE.get();
    }

    public static void clearDataBaseType() {
        TYPE.remove();
    }
}