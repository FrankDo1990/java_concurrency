package com.wenwen.jcip.threadsafe;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by dufeng on 2016/11/4.
 */
public class MyServlet implements Servlet {
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {

    }
}
