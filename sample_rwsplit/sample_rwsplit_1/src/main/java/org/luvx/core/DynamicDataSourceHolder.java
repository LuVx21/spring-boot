package org.luvx.core;

import org.springframework.beans.factory.annotation.Value;

public class DynamicDataSourceHolder {
    public static String slave;
    public static String master;

    private static final ThreadLocal<String> holder = new ThreadLocal<>();

    public static void putDataSource(String name) {
        holder.set(name);
    }

    public static String getDataSouce() {
        return holder.get();
    }

    @Value("${jdbc.key.slave}")
    void setSlave(String slave) {
        this.slave = slave;
    }

    @Value("${jdbc.key.master}")
    void setMaster(String master) {
        this.master = master;
    }

}