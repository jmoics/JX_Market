/**
 * 
 */
package pe.com.jx_market.controller;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Image;

import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.domain.DTO_Usuario;

/**
 * @author George
 *
 */
public class PO_EAMenuPrinc extends Borderlayout
{
    private Image imaLogo;
    
    public void onCreate()
    {
        imaLogo = (Image) getFellow("imaLogo");
        incluir("fondo.zul");
        DTO_Usuario usuario = (DTO_Usuario) getDesktop().getSession().getAttribute("login");
        DTO_Empresa empresa = (DTO_Empresa) getDesktop().getSession().getAttribute("empresa");
        imaLogo.setSrc("Service/"+empresa.getDominio().toUpperCase()+"/"+empresa.getDominio()+".png");
        if (usuario == null) {
            throw new RuntimeException("La sesion se perdio, vuelva a ingresar por favor");
        }

        if(getDesktop().getSession().getAttribute("actualizar") == null){
            incluir("fondo.zul");
        }

        getPage().addEventListener(Events.ON_BOOKMARK_CHANGE, 
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(Event arg0) throws Exception {
                                try {
                                    incluir(getDesktop().getBookmark());
                                } catch (org.zkoss.zk.ui.ComponentNotFoundException ex) {
                                    incluir("fondo.zul");
                                }
                            }
                      });
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
        getDesktop().getSession().removeAttribute("empresa");
        // getDesktop().getSession().removeAttribute("paginaActual");
        Executions.sendRedirect("eAlogin.zul");
    }

}
