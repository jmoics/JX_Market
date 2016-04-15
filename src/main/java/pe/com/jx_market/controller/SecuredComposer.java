package pe.com.jx_market.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Employee;
import pe.com.jx_market.domain.DTO_Module;
import pe.com.jx_market.domain.DTO_User;
import pe.com.jx_market.domain.ModuleAccessUI;
import pe.com.jx_market.domain.Parameter;
import pe.com.jx_market.domain.ParameterType;
import pe.com.jx_market.domain.Ubication;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
@VariableResolver(DelegatingVariableResolver.class)
public abstract class SecuredComposer<T extends Component>
    extends CommonComposer<T>
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
    @WireVariable
    private BusinessService<Ubication> ubicationService;
    @WireVariable
    private BusinessService<String> passwordHashService;

    @Override
    public void doAfterCompose(final T _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);
        final DTO_User user = (DTO_User) this.desktop.getSession().getAttribute(Constantes.ATTRIBUTE_USER);
        if (user == null) {
            throw new RuntimeException("La sesión se perdió. Vuelva a ingresar por favor.");
        }
        if (!user.getCompanyId().equals(Constantes.INSTITUCION_JX_MARKET)) {
            checkResources(user);
        }
        this.company = (DTO_Company) _comp.getDesktop().getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        getUbications();
    }

    /**
     * @param _user
     */
    private void checkResources(final DTO_User _user)
    {
        final String[] resources = requiredResources();
        if (resources == null || resources.length == 0) {
            return;
        }
        final ServiceInput<DTO_Employee> input = new ServiceInput<DTO_Employee>();
        input.addMapPair(Constantes.ATTRIBUTE_USER, _user);
        input.addMapPair(Constantes.ATTRIBUTE_MODULES, resources);
        final ServiceOutput<DTO_Employee> output = this.autorizacionService.execute(input);
        if (output.getErrorCode() == Constantes.AUTH_ERROR) {
            throw new RuntimeException("Acceso no autorizado!");
        }
    }

    protected void checkResources2(final DTO_User _user)
    {
        final String clazzName = this.getClass().getName();
        final List<DTO_Module> modules = _user.getRole().getModules();
        boolean band = false;
        for (final DTO_Module module : modules) {
            if (module.getAccessUisMap().containsKey(clazzName)) {
                band = true;
                break;
            }
        }
        if (!band) {
            throw new RuntimeException("Acceso no autorizado!");
        }
    }

    /**
     * Method to check if the employee have access to system.
     * @param _comp
     * @param _widget
     * @param _module
     * @param _user
     */
    public void setVisibilityByResource(final Component _comp,
                                        final String _widget,
                                        final String _module,
                                        final DTO_User _user)
    {
        final ServiceInput<DTO_Employee> input = new ServiceInput<DTO_Employee>();
        input.addMapPair(Constantes.ATTRIBUTE_USER, _user);
        input.addMapPair(Constantes.ATTRIBUTE_MODULE, _module);
        final ServiceOutput<DTO_Employee> output = this.autorizacionService.execute(input);
        if (output.getErrorCode() != Constantes.OK) {
            _comp.getFellow(_widget).setVisible(false);
        }
    }

    public boolean setVisibilityByResource2(final Component _comp,
                                            final String _widget,
                                            final DTO_User _user)
    {
        final String clazzName = this.getClass().getName();
        final List<DTO_Module> modules = _user.getRole().getModules();
        boolean band = false;
        for (final DTO_Module module : modules) {
            if (module.getAccessUisMap().containsKey(clazzName)) {
                final ModuleAccessUI modAccUi = module.getAccessUisMap().get(clazzName);
                if (modAccUi.getWidgetsUisMap().containsKey(_widget)) {
                    band = true;
                    break;
                }
            }
        }
        _comp.getFellow(_widget).setVisible(band);
        return band;
    }

    /**
     * @return
     */
    abstract String[] requiredResources();

    /**
     * @param _combo UI Combobox to add the parameters.
     * @param _parameterType Constant of the parameter type.
     * @param _selectId With the id to be select.
     */
    public void buildParameterCombo(final Combobox _combo,
                                    final String _parameterType,
                                    final Integer _selectId)
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
                    if (_selectId != null && _selectId.equals(param.getId())) {
                        _combo.setSelectedItem(item);
                    }
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

    /**
     * @return {@link Map} with ubications.
     */
    @SuppressWarnings("unchecked")
    private Map<Integer, Ubication> getUbications()
    {
        Map<Integer, Ubication> ret;
        // verificar cache de BD, para no tener que hacer un cache temporal en la sesion.
        if (this.desktop.getSession().getAttribute(Constantes.ATTRIBUTE_UBICATION) != null) {
            ret = (Map<Integer, Ubication>) this.desktop.getSession().getAttribute(Constantes.ATTRIBUTE_UBICATION);
        } else {
            ret = new HashMap<Integer, Ubication>();
            final Ubication ubicSe = new Ubication();
            final ServiceInput<Ubication> input = new ServiceInput<Ubication>();
            input.setAction(Constantes.V_LIST);
            input.setObject(ubicSe);
            final ServiceOutput<Ubication> output = this.ubicationService.execute(input);
            if (Constantes.OK == output.getErrorCode()) {
                for (final Ubication ubic : output.getLista()) {
                    ret.put(ubic.getId(), ubic);
                }
                this.desktop.getSession().setAttribute(Constantes.ATTRIBUTE_UBICATION, ret);
            }
        }
        return ret;
    }

    /**
     * @param _comboParent Current {@link Combobox} to add items.
     * @param _comboChild {@link Combobox} to build with the event generated in _comboParent.
     * @return Return {@link List} with the departments.
     */
    public Map<Integer, Ubication> getDepartments(final Combobox _comboParent,
                                                  final Combobox _comboChild)
    {
        final Map<Integer, Ubication> ubicMap = getUbications();
        final Map<Integer, Ubication> departMap = new HashMap<Integer, Ubication>();
        for (final Entry<Integer, Ubication> entry : ubicMap.entrySet()) {
            if (entry.getValue().getParentId() == null) {
                departMap.put(entry.getKey(), entry.getValue());
                if (_comboParent != null && entry.getValue() != null) {
                    buildCombo4Ubications(_comboParent, entry.getValue());
                }
            }
        }
        buildEvent4UbicationCombo(_comboParent, _comboChild);
        return departMap;
    }

    /**
     * @param _departmentId Id of the {@link Ubication} for department.
     * @param _comboParent Current {@link Combobox} to add items.
     * @param _comboChild {@link Combobox} to build with the event generated in _comboParent.
     * @return {@link List} with the provinces.
     */
    public Map<Integer, Ubication> getProvinces(final Integer _departmentId,
                                                final Combobox _comboParent,
                                                final Combobox _comboChild)
    {
        final Map<Integer, Ubication> ubicMap = getUbications();
        final Map<Integer, Ubication> departMap = getDepartments(null, null);
        final Map<Integer, Ubication> provinceMap = new HashMap<Integer, Ubication>();
        for (final Entry<Integer, Ubication> entry : ubicMap.entrySet()) {
            if (_departmentId == null) {
                if (departMap.containsKey(entry.getValue().getParentId())) {
                    provinceMap.put(entry.getKey(), entry.getValue());
                    if (_comboParent != null && entry.getValue() != null) {
                        buildCombo4Ubications(_comboParent, entry.getValue());
                    }
                }
            } else {
                if (_departmentId.equals(entry.getValue().getParentId())) {
                    provinceMap.put(entry.getKey(), entry.getValue());
                    if (_comboParent != null && entry.getValue() != null) {
                        buildCombo4Ubications(_comboParent, entry.getValue());
                    }
                }
            }
        }
        buildEvent4UbicationCombo(_comboParent, _comboChild);
        return provinceMap;
    }

    /**
     * @param _departmentId Id of the {@link Ubication} for department.
     * @param _provinceId Id of the {@link Ubication} for provinces.
     * @param _comboParent Current {@link Combobox} to add items.
     * @param _comboChild {@link Combobox} to build with the event generated in _comboParent.
     * @return {@link List} with the districts.
     */
    public Map<Integer, Ubication> getDistricts(final Integer _departmentId,
                                                final Integer _provinceId,
                                                final Combobox _comboParent,
                                                final Combobox _comboChild)
    {
        final Map<Integer, Ubication> ubicMap = getUbications();
        final Map<Integer, Ubication> provinceMap = getProvinces(_departmentId, null, null);
        final Map<Integer, Ubication> districtMap = new HashMap<Integer, Ubication>();
        for (final Entry<Integer, Ubication> entry : ubicMap.entrySet()) {
            if (_provinceId == null) {
                if (provinceMap.containsKey(entry.getValue().getParentId())) {
                    provinceMap.put(entry.getKey(), entry.getValue());
                    if (_comboParent != null && entry.getValue() != null) {
                        buildCombo4Ubications(_comboParent, entry.getValue());
                    }
                }
            } else {
                if (_provinceId.equals(entry.getValue().getParentId())) {
                    provinceMap.put(entry.getKey(), entry.getValue());
                    if (_comboParent != null && entry.getValue() != null) {
                        buildCombo4Ubications(_comboParent, entry.getValue());
                    }
                }
            }
        }
        buildEvent4UbicationCombo(_comboParent, _comboChild);
        return districtMap;
    }

    /**
     * @param _comboParent Current {@link Combobox} to add items.
     * @param _ubi {@link Ubication} to add in the {@link #Comboitem}.
     * @return Comboitem with the item.
     */
    protected Comboitem buildCombo4Ubications(final Combobox _comboParent,
                                              final Ubication _ubi)
    {
        final Comboitem item = new Comboitem(_ubi.getName());
        item.setValue(_ubi);
        _comboParent.appendChild(item);
        return item;
    }

    /**
     * @param _comboParent Current {@link Combobox} to add items.
     * @param _comboChild {@link Combobox} to build with the event generated in _comboParent.
     */
    private void buildEvent4UbicationCombo(final Combobox _comboParent,
                                           final Combobox _comboChild)
    {
        if (_comboChild != null) {
            _comboParent.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
                @Override
                public void onEvent(final Event _event)
                    throws UiException
                {
                    buildNextCombo(_comboChild);
                }
            });
        }
    }

    /**
     * @param _comboChild {@link Combobox} to build with the event generated in _comboParent.
     */
    protected void buildNextCombo(final Combobox _comboChild)
    {

    }
}
