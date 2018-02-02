package org.luvx.listener;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;

/**
 * ServletRequest对象的添加,删除等
 */
@WebListener
public class RequestAttributeListener implements ServletRequestAttributeListener {
    @Override
    public void attributeAdded(ServletRequestAttributeEvent srab) {
        System.out.println("LuVx:ServletRequestAttributeListener:add Attribute:" + srab.getName());
    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent srab) {
         System.out.println("LuVx:ServletRequestAttributeListener:remove Attribute:" + srab.getName());
    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent srab) {
        System.out.println("LuVx:ServletRequestAttributeListener:replace Attribute:" + srab.getName());
    }
}
