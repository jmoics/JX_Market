package pe.com.jx_market.controller;

import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Empleado;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;


public abstract class SecuredWindow extends Window
{
    private static final long serialVersionUID = 3258515575680520815L;
    private BusinessService autorizacionService;

    public void onCreate()
    {
        final DTO_Empleado empleado = (DTO_Empleado) getDesktop().getSession().getAttribute("empleado");
        if (empleado == null) {
            throw new RuntimeException("La sesión se perdió. Vuelva a ingresar por favor.");
        }
        if (!empleado.getCompany().equals(Constantes.INSTITUCION_JX_MARKET)) {
            checkResources(empleado);
        }
        realOnCreate();
    }

    private void checkResources(final DTO_Empleado empleado)
    {
        autorizacionService = ContextLoader.getService(this, "autorizacionService");
        final String[] resources = requiredResources();
        if (resources == null || resources.length == 0) {
            return;
        }
        final ServiceInput<DTO_Empleado> input = new ServiceInput<DTO_Empleado>();
        input.addMapPair("empleado", empleado);
        input.addMapPair("modulo-array", resources);
        final ServiceOutput<DTO_Empleado> output = autorizacionService.execute(input);
        if (output.getErrorCode() == Constantes.AUTH_ERROR) {
            throw new RuntimeException("Acceso no autorizado!");
        }
    }

    abstract void realOnCreate();

    abstract String[] requiredResources();
}
