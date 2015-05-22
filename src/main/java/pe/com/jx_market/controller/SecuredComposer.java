package pe.com.jx_market.controller;

import java.util.List;

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

import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Employee;
import pe.com.jx_market.domain.DTO_User;
import pe.com.jx_market.domain.Parameter;
import pe.com.jx_market.domain.ParameterType;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

@VariableResolver(DelegatingVariableResolver.class)
public abstract class SecuredComposer<T extends Component>
    extends SelectorComposer<T>
{

    private static final long serialVersionUID = 3258515575680520815L;
    private DTO_Company company;
    @WireVariable
    private Desktop desktop;
    @WireVariable
    private BusinessService<DTO_Employee> autorizacionService;
    @WireVariable
    private BusinessService<Parameter> parameterService;
    @WireVariable
    private BusinessService<ParameterType> parameterTypeService;

    @Override
    public void doAfterCompose(final T _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);
        final DTO_User user = (DTO_User) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_USER);
        if (user == null) {
            throw new RuntimeException("La sesión se perdió. Vuelva a ingresar por favor.");
        }
        if (!user.getCompanyId().equals(Constantes.INSTITUCION_JX_MARKET)) {
            checkResources(user);
        }
        this.company = (DTO_Company) _comp.getDesktop().getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
    }

    private void checkResources(final DTO_User user)
    {
        final String[] resources = requiredResources();
        if (resources == null || resources.length == 0) {
            return;
        }
        final ServiceInput<DTO_Employee> input = new ServiceInput<DTO_Employee>();
        input.addMapPair(Constantes.ATTRIBUTE_USER, user);
        input.addMapPair(Constantes.ATTRIBUTE_MODULES, resources);
        final ServiceOutput<DTO_Employee> output = autorizacionService.execute(input);
        if (output.getErrorCode() == Constantes.AUTH_ERROR) {
            throw new RuntimeException("Acceso no autorizado!");
        }
    }

    /**
     * Method to check if the employee have access to system.
     * @param comp
     * @param widget
     * @param module
     * @param employee
     */
    public void setVisibilityByResource(final Component comp,
                                        final String widget,
                                        final String module,
                                        final DTO_User user)
    {
        final ServiceInput<DTO_Employee> input = new ServiceInput<DTO_Employee>();
        input.addMapPair(Constantes.ATTRIBUTE_USER, user);
        input.addMapPair(Constantes.ATTRIBUTE_MODULE, module);
        final ServiceOutput<DTO_Employee> output = autorizacionService.execute(input);
        if (output.getErrorCode() != Constantes.OK) {
            comp.getFellow(widget).setVisible(false);
        }
    }

    abstract String[] requiredResources();

    /**
     * @param _logger
     * @param _txt
     * @param _txt2
     * @param _t
     * @return
     */
    public int alertaInfo(final Log _logger,
                          final String _txt,
                          final String _txt2,
                          final Throwable _t)
    {
        int ret = 0;
        if (_txt.length() > 0) {
            ret = Messagebox.show(_txt, this.company.getBusinessName(), Messagebox.OK, Messagebox.INFORMATION);
        }
        if (_t != null) {
            _logger.info(_txt2, _t);
        } else {
            _logger.info(_txt2);
        }
        return ret;
    }

    /**
     * @param _logger
     * @param _txt
     * @param _txt2
     * @param _t
     */
    public void alertaError(final Log _logger,
                            final String _txt,
                            final String _txt2,
                            final Throwable _t)
    {
        if (_txt.length() > 0) {
            Messagebox.show(_txt, this.company.getBusinessName(), Messagebox.OK, Messagebox.EXCLAMATION);
        }
        if (_t != null) {
            _logger.error(_txt2, _t);
        } else {
            _logger.error(_txt2);
        }
    }

    /**
     * @param _combo Combobox to add items.
     */
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

    /**
     * @param _combo UI Combobox to add the parameters.
     * @param _parameterType Constant of the parameter type.
     */
    public void buildParameterCombo(final Combobox _combo,
                                    final String _parameterType)
    {
        final ParameterType parTyp = new ParameterType();
        parTyp.setCompanyId(this.company.getId());
        parTyp.setParameterTypeName(_parameterType);
        final ServiceInput<ParameterType> input1 = new ServiceInput<ParameterType>();
        input1.setAction(Constantes.V_GET);
        input1.setObject(parTyp);
        final ServiceOutput<ParameterType> output1 = this.parameterTypeService.execute(input1);
        if (Constantes.OK == output1.getErrorCode()) {
            final Integer parameterTypeId = output1.getObject().getId();
            final Parameter paramSe = new Parameter();
            paramSe.setCompanyId(this.company.getId());
            paramSe.setParameterTypeId(parameterTypeId);
            final ServiceInput<Parameter> input = new ServiceInput<Parameter>();
            input.setAction(Constantes.V_LIST);
            input.setObject(paramSe);
            final ServiceOutput<Parameter> output = this.parameterService.execute(input);
            if (Constantes.OK == output.getErrorCode()) {
                final List<Parameter> lstParams = output.getLista();
                for (final Parameter param : lstParams) {
                    final Comboitem item = new Comboitem();
                    item.setLabel(param.getParameterName());
                    item.setValue(param);
                    _combo.appendChild(item);
                }
            }
        }
    }

    /**
     * @param _id Id of Parameter.
     * @return Parameter.
     */
    public Parameter getParameter(final Integer _id)
    {
        Parameter retPar = null;
        final Parameter paramSe = new Parameter();
        paramSe.setCompanyId(this.company.getId());
        paramSe.setId(_id);
        final ServiceInput<Parameter> input = new ServiceInput<Parameter>();
        input.setAction(Constantes.V_GET);
        input.setObject(paramSe);
        final ServiceOutput<Parameter> output = this.parameterService.execute(input);
        if (Constantes.OK == output.getErrorCode()) {
            retPar = output.getObject();
        }
        return retPar;
    }
}
