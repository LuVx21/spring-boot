package org.luvx.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * 监听ServletRequest的创建,销毁
 */
@WebListener()
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("LuVx:ServletRequestListener:请求创建了");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("LuVx:ServletRequestListener:请求已销毁");
    }

}
