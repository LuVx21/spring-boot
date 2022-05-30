package org.luvx.boot.mul.mybatis.config;

import java.util.Objects;

public class DSTypeContainer {
    private static final ThreadLocal<DS.DSType> TYPE = new ThreadLocal<>();

    public static void setDataSourceType(DS.DSType dataBase) {
        Objects.requireNonNull(dataBase);
        TYPE.set(dataBase);
    }

    public static String getDataSourceType() {
        return TYPE.get().name();
    }

    public static void clearDataSourceType() {
        TYPE.remove();
    }
}