package org.luvx.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * 绑定和解绑
 * jsp:
 * <%session.setAttribute("p", new Person(1,"LuVx")); %>
 * ${p2.name }
 * <%session.removeAttribute("p");%>
 */
@WebListener
public class SessionBindingListener implements HttpSessionBindingListener {
    @Override
    public void valueBound(HttpSessionBindingEvent hsbe) {
        System.out.println("LuVx:HttpSessionBindingListener:Bound Session:" + hsbe.getName());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent hsbe) {
        System.out.println("LuVx:HttpSessionBindingListener:Unbound Session:" + hsbe.getName());
    }
}
