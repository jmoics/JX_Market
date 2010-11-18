/**
 * 
 */
package pe.com.jx_market.controller;

import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Usuario;

/**
 * @author <._.> 
 *
 */
public class PO_EAHeader extends Window
{
    private Label userdata;
    
    public void onCreate ()
    {
        userdata = (Label) getFellow("userdata");

        DTO_Usuario usuario = (DTO_Usuario) getDesktop().getSession().getAttribute("login");
        if (usuario == null) {
            throw new RuntimeException("La sesion se perdio, vuelva a ingresar por favor");
        }
        //Aqui debo jalar al empleado
        userdata.setValue(usuario.getUsername());
    }
}
