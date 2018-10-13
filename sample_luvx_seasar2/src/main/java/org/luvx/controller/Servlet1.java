package org.luvx.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

@WebServlet("/serv1")
public class Servlet1 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        S2Container container = S2ContainerFactory.create("app.dicon");
        container.init();
        TestBeanImpl bean = (TestBeanImpl) container.getComponent("mybean");

        bean.addData("1", new java.util.Date(), "This is test.");
        bean.addData("2", new java.util.Date(), "这是测试。");

        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        java.io.PrintWriter out = resp.getWriter();
        out.print("<html><head></head>");
        out.print("<body>");
        out.print("<h5>");
        out.print(bean.toString());
        out.print("</h5>");
        out.print("</body></html>");

    }

}
