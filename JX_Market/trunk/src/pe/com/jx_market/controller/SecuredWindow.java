package pe.com.jx_market.controller;

import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Empleado;
import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

public abstract class SecuredWindow
    extends Window
{

    public void onCreate()
    {
        final DTO_Empleado empleado = (DTO_Empleado) getDesktop().getSession().getAttribute("empleado");
        if (empleado == null) {
            throw new RuntimeException("La sesión se perdió. Vuelva a ingresar por favor.");
        }
        checkResources(empleado);
        realOnCreate();
    }

    private void checkResources(final DTO_Empleado empleado)
    {
        final String[] resources = requiredResources();
        if (resources == null || resources.length == 0) {
            return;
        }
        final BusinessService autorizacionService = Utility.getService(this, "autorizacionService");
        final DTO_Input input = new DTO_Input();
        input.addMapPair("empleado", empleado);
        input.addMapPair("modulo-array", resources);
        final DTO_Output output = autorizacionService.execute(input);
        if (output.getErrorCode() == Constantes.AUTH_ERROR) {
            throw new RuntimeException("Acceso no autorizado!");
        }
    }

    abstract void realOnCreate();

    abstract String[] requiredResources();
}