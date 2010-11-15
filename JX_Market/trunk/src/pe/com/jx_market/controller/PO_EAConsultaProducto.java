/*
 * Author:          jx_market
 * Revision:        $Rev: 1 $
 * Last Changed:    $Date: 2010-11-11 10:53:13 -0500 (jue, 11 nov 2010) $
 * Last Changed By: $Author: Jorge Cueva $
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

    public void incluir(final String txt) {
        //getDesktop().getSession().setAttribute("paginaActual", txt);
        getDesktop().getSession().setAttribute("actualizar", "actualizar");
        Utility.saltar(this, txt);
    }
}
