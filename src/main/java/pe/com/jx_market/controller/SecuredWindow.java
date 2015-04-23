package pe.com.jx_market.controller;

import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Employee;
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
        final DTO_Employee employee = (DTO_Employee) getDesktop().getSession().getAttribute("employee");
        if (employee == null) {
            throw new RuntimeException("La sesión se perdió. Vuelva a ingresar por favor.");
        }
        if (!employee.getCompanyId().equals(Constantes.INSTITUCION_JX_MARKET)) {
            checkResources(employee);
        }
        realOnCreate();
    }

    private void checkResources(final DTO_Employee employee)
    {
        autorizacionService = ContextLoader.getService(this, "autorizacionService");
        final String[] resources = requiredResources();
        if (resources == null || resources.length == 0) {
            return;
        }
        final ServiceInput<DTO_Employee> input = new ServiceInput<DTO_Employee>();
        input.addMapPair("employee", employee);
        input.addMapPair("module-array", resources);
        final ServiceOutput<DTO_Employee> output = autorizacionService.execute(input);
        if (output.getErrorCode() == Constantes.AUTH_ERROR) {
            throw new RuntimeException("Acceso no autorizado!");
        }
    }

    abstract void realOnCreate();

    abstract String[] requiredResources();
}
