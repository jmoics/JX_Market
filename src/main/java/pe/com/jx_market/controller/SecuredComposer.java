package pe.com.jx_market.controller;

import org.apache.commons.logging.Log;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Messagebox;

import pe.com.jx_market.domain.DTO_Empleado;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

@VariableResolver(DelegatingVariableResolver.class)
public abstract class SecuredComposer<T extends Component>
    extends SelectorComposer<T>
{

    private static final long serialVersionUID = 3258515575680520815L;
    private DTO_Empresa empresa;
    @WireVariable
    private Desktop desktop;
    @WireVariable
    private BusinessService<DTO_Empleado> autorizacionService;

    @Override
    public void doAfterCompose(final T _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);
        final DTO_Empleado empleado = (DTO_Empleado) desktop.getSession().getAttribute("empleado");
        if (empleado == null) {
            throw new RuntimeException("La sesión se perdió. Vuelva a ingresar por favor.");
        }
        if (!empleado.getEmpresa().equals(Constantes.INSTITUCION_JX_MARKET)) {
            checkResources(empleado);
        }
        empresa = (DTO_Empresa) _comp.getDesktop().getSession().getAttribute("empresa");
    }

    private void checkResources(final DTO_Empleado empleado)
    {
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

    abstract String[] requiredResources();

    public void alertaInfo(final Log _logger,
                           final String txt,
                           final String txt2,
                           final Throwable t)
    {
        if (txt.length() > 0) {
            Messagebox.show(txt, empresa.getRazonsocial(), 1, Messagebox.INFORMATION);
        }
        if (t != null) {
            _logger.info(txt2, t);
        } else {
            _logger.info(txt2);
        }
    }

    public void alertaError(final Log _logger,
                            final String txt,
                            final String txt2,
                            final Throwable t)
    {
        if (txt.length() > 0) {
            Messagebox.show(txt, empresa.getRazonsocial(), 1, Messagebox.EXCLAMATION);
        }
        if (t != null) {
            _logger.error(txt2, t);
        } else {
            _logger.error(txt2);
        }
    }

    public void buildActiveCombo(final Combobox _combo)
    {
        Comboitem item = new Comboitem();
        item.setLabel(Labels.getLabel("pe.com.jx_market.Active.TRUE"));
        item.setValue(Constantes.STB_ACTIVO);
        _combo.appendChild(item);
        item = new Comboitem();
        item.setLabel(Labels.getLabel("pe.com.jx_market.Active.FALSE"));
        item.setValue(Constantes.STB_INACTIVO);
        _combo.appendChild(item);
    }
}
