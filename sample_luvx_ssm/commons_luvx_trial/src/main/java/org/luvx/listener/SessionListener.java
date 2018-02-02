package org.luvx.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 监听HttpSession的创建,销毁
 */
@WebListener()
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("LuVx:HttpSessionListener:创建");
    }

    /**
     * 销毁
     * JSP:
     * <% session.invalidate(); %>
     *
     * @param se
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("LuVx:HttpSessionListener:销毁");
    }
}
