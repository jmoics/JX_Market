/**
 * 
 */
package pe.com.jx_market.controller;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Borderlayout;

/**
 * @author George
 *
 */
public class PO_EmpMenuPrincAsociado extends Borderlayout
{
    
    public void onCreate()
    {
        incluir("fondo.zul");
    }

    public void incluir(String txt)
    {
        // getDesktop().getSession().setAttribute("paginaActual", txt);
        getDesktop().getSession().setAttribute("actualizar", "actualizar");
        Utility.saltar(this, txt);
    }

    public void salir()
    {
        getDesktop().getSession().removeAttribute("login");
        getDesktop().getSession().removeAttribute("actualizar");
        // getDesktop().getSession().removeAttribute("paginaActual");
        Executions.sendRedirect("login.zul");
    }

}
