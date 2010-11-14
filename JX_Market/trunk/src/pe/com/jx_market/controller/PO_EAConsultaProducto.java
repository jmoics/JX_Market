/**
 * 
 */
package pe.com.jx_market.controller;

import org.zkoss.zul.Window;
/**
 * @author George
 *
 */
public class PO_EAConsultaProducto extends Window
{
    public void onCreate () {
        
    }
    
    public void incluir(String txt) {
        //getDesktop().getSession().setAttribute("paginaActual", txt);
        getDesktop().getSession().setAttribute("actualizar", "actualizar");
        Utility.saltar(this, txt);
    }
}
