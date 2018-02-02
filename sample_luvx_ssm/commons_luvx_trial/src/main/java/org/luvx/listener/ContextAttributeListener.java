package org.luvx.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

/**
 * 监听ServletContext对象属性的添加,删除等
 * jsp:
 * <% application.setAttribute("username", "LuVx"); %>
 * <% application.setAttribute("username", "LuVx1"); %>
 * <% application.removeAttribute("username"); %>
 */
@WebListener()
public class ContextAttributeListener implements ServletContextAttributeListener {
    @Override
    //添加
    public void attributeAdded(ServletContextAttributeEvent scab) {
        System.out.println("LuVx:ServletContextAttributeListener:添加了一个属性,属性为:" + scab.getName());
    }

    @Override
    //替换
    public void attributeReplaced(ServletContextAttributeEvent scab) {
        System.out.println("LuVx:ServletContextAttributeListener:替换了一个属性,属性为:" + scab.getName());

    }

    @Override
    //移除
    public void attributeRemoved(ServletContextAttributeEvent scab) {
        System.out.println("LuVx:ServletContextAttributeListener:移除了一个属性,属性为:" + scab.getName());

    }
}
