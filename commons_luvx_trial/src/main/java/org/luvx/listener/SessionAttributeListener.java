package org.luvx.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * HttpSession对象的添加,删除等
 */
@WebListener
public class SessionAttributeListener implements HttpSessionAttributeListener {
    @Override
    public void attributeAdded(HttpSessionBindingEvent hsbe) {
        System.out.println("LuVx:HttpSessionAttributeListener:add Attribute:" + hsbe.getName());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent hsbe) {
        System.out.println("LuVx:HttpSessionAttributeListener: remove Attribute:" + hsbe.getName());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent hsbe) {
        System.out.println("LuVx:HttpSessionAttributeListener:replace Attribute:" + hsbe.getName());
    }
}
