package pe.com.jx_market.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class DataBaseInitialize
    extends HttpServlet
{
    public void init()
        throws ServletException
    {
        System.out.println("----------");
        System.out.println("---------- CrunchifyExample Servlet Initialized successfully ----------");
        System.out.println("----------");
    }
}
