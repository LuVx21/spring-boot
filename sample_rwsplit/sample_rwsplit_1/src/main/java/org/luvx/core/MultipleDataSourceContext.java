package org.luvx.core;

import org.springframework.beans.factory.annotation.Value;

public class MultipleDataSourceContext {
    public static String slave;
    public static String master;

    private static final ThreadLocal<String> context = new ThreadLocal<>();

    public static void setKey(String name) {
        context.set(name);
    }

    public static String getKey() {
        return context.get();
    }

    @Value("${jdbc.key.slave}")
    void setSlave(String slave) {
        MultipleDataSourceContext.slave = slave;
    }

    @Value("${jdbc.key.master}")
    void setMaster(String master) {
        MultipleDataSourceContext.master = master;
    }

}