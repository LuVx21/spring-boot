package org.luvx.core;

/**
 * Created by IDEA
 * 本地线程设置和获取数据源信息
 * User: mashaohua
 * Date: 2016-07-07 13:35
 * Desc:
 */
public class DynamicDataSourceHolder {

    private static final ThreadLocal<RorW> holder = new ThreadLocal<RorW>();

    public static void putDataSource(RorW dataSource) {
        holder.set(dataSource);
    }

    public static RorW getDataSource() {
        return holder.get();
    }

    public static void clearDataSource() {
        holder.remove();
    }

}