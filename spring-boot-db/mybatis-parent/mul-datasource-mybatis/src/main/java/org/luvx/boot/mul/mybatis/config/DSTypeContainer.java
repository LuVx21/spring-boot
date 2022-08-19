package org.luvx.boot.mul.mybatis.config;

import java.util.Objects;

public class DSTypeContainer {
    private static final ThreadLocal<DS.DSType> TYPE = new ThreadLocal<>();

    public static void setDataSourceType(DS.DSType dataBase) {
        Objects.requireNonNull(dataBase);
        TYPE.set(dataBase);
    }

    public static DS.DSType getDataSourceType() {
        return TYPE.get() == null ? DS.DSType.ds1 : TYPE.get();
    }

    public static void clearDataSourceType() {
        TYPE.remove();
    }
}