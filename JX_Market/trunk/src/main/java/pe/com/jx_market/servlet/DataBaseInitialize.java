package pe.com.jx_market.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class DataBaseInitialize
    extends HttpServlet
{
    @Override
    public void init()
        throws ServletException
    {
        System.out.println("----------");
        System.out.println("---------- Servlet para Carga de Base de Datos inicializado satisfactoriamente ----------");
        System.out.println("----------");
    }
}
