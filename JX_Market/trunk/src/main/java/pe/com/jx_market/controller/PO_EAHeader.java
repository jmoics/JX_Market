/**
 *
 */
package pe.com.jx_market.controller;

import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Empleado;

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

        final DTO_Empleado empleado = (DTO_Empleado) getDesktop().getSession().getAttribute("empleado");
        if (empleado == null) {
            throw new RuntimeException("La sesion se perdio, vuelva a ingresar por favor");
        }
        userdata.setValue(empleado.getApellido() + " " + empleado.getNombre());
    }
}
