package pe.com.jx_market.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listgroup;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Area;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Employee;
import pe.com.jx_market.domain.DTO_Role;
import pe.com.jx_market.domain.DTO_User;
import pe.com.jx_market.domain.Parameter;
import pe.com.jx_market.domain.Ubication;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_EAAdministrateEmployeeCreate
    extends SecuredComposerModal<Window>
{

    private final Log logger = LogFactory.getLog(PO_EAAdministrateEmployeeCreate.class);
    @Wire
    private Textbox txtUser, txtPass, txtName, txtLastName, txtLastName2, txtDocument, txtPhone, txtCellphone, txtEMail,
                    txtAddress, txtUbigeo, txtCity, txtRepPass;
    @Wire
    private Datebox datBirthday;
    @Wire
    private Combobox cmbDocType, cmbActive, cmbSex, cmbCivilState, cmbDepartment, cmbProvince, cmbDistrict;
    @Wire
    private Window wEAAEC;
    @Wire
    private Checkbox chbCreateUser;
    @Wire
    private Bandbox bndRole;
    @WireVariable
    private BusinessService<DTO_Employee> employeeService;
    @WireVariable
    private BusinessService<DTO_Role> roleService;
    @WireVariable
    private BusinessService<DTO_User> userService;
    @WireVariable
    private Desktop desktop;
    private DTO_Company company;
    private PO_EAAdministrateEmployee moduleParentUI;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);
        this.company = (DTO_Company) this.desktop.getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        buildRoles();
        buildActiveCombo(this.cmbActive);
        buildParameterCombo(this.cmbDocType, Constantes.PARAM_DOCUMENT_TYPE, null);
        buildParameterCombo(this.cmbSex, Constantes.PARAM_SEX_TYPE, null);
        buildParameterCombo(this.cmbCivilState, Constantes.PARAM_CIVILSTATE_TYPE, null);
        buildDepartmentCombo();

        // Obtenemos el controlador de la pantalla principal de empleados.
        final Map<?, ?> mapArg = this.desktop.getExecution().getArg();
        this.moduleParentUI = (PO_EAAdministrateEmployee) mapArg.get(Constantes.ATTRIBUTE_PARENTFORM);
    }

    /**
     *
     */
    @Listen("onClick = #btnSave")
    public void createEmployee()
    {
        if (!this.txtName.getValue().isEmpty() && !this.txtLastName.getValue().isEmpty()
                        && !this.txtDocument.getValue().isEmpty() && this.cmbDocType.getSelectedItem() != null
                        && this.cmbActive.getSelectedItem() != null) {
            final DTO_Employee employee = new DTO_Employee();
            employee.setCompanyId(this.company.getId());
            employee.setEmployeeName(this.txtName.getValue());
            employee.setEmployeeLastName(this.txtLastName.getValue());
            employee.setEmployeeLastName2(this.txtLastName2.getValue());
            employee.setDocumentTypeId(((Parameter) this.cmbDocType.getSelectedItem().getValue()).getId());
            employee.setDocumentNumber(this.txtDocument.getValue());
            employee.setAddress(this.txtAddress.getValue());
            employee.setBirthday(this.datBirthday.getValue());
            employee.setPhone(this.txtPhone.getValue());
            employee.setCellPhone(this.txtCellphone.getValue());
            // employee.setCity(this.txtCity != null ? this.txtCity.getValue() : null);
            if (this.cmbCivilState.getSelectedItem() != null) {
                employee.setCivilStateId(((Parameter) this.cmbCivilState.getSelectedItem().getValue()).getId());
            }
            if (this.cmbSex.getSelectedItem() != null) {
                employee.setSexId(((Parameter) this.cmbSex.getSelectedItem().getValue()).getId());
            }
            employee.setActive((Boolean) this.cmbActive.getSelectedItem().getValue());
            employee.setEmail(this.txtEMail.getValue());
            if (this.cmbDepartment.getSelectedItem() != null) {
                employee.setDepartmentId(((Ubication) this.cmbDepartment.getSelectedItem().getValue()).getId());
                if (this.cmbProvince.getSelectedItem() != null) {
                    employee.setProvinceId(((Ubication) this.cmbProvince.getSelectedItem().getValue()).getId());
                    if (this.cmbDistrict.getSelectedItem() != null) {
                        employee.setDistrictId(((Ubication) this.cmbDistrict.getSelectedItem().getValue()).getId());
                        employee.setUbigeo(((Ubication) this.cmbDistrict.getSelectedItem().getValue()).getCode());
                    } else {
                        employee.setUbigeo(((Ubication) this.cmbProvince.getSelectedItem().getValue()).getCode());
                    }
                } else {
                    employee.setUbigeo(((Ubication) this.cmbDepartment.getSelectedItem().getValue()).getCode());
                }
            }
            if (this.chbCreateUser.isChecked()) {
                final DTO_User user = createUser();
                employee.setUserId(user.getId());
            }
            final ServiceInput<DTO_Employee> input = new ServiceInput<DTO_Employee>();
            input.setAction(Constantes.V_REGISTER);
            input.setObject(employee);
            final ServiceOutput<DTO_Employee> output = this.employeeService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                final int resp = alertaInfo(this.logger,
                        Labels.getLabel("pe.com.jx_market.PO_EAAdministrateEmployeeCreate.createEmployee.Info.Label"),
                        Labels.getLabel("pe.com.jx_market.PO_EAAdministrateEmployeeCreate.createEmployee.Info.Label"),
                                    null);
                if (resp == Messagebox.OK) {
                    this.moduleParentUI.searchEmployees();
                }
            } else {
                alertaError(this.logger,
                        Labels.getLabel("pe.com.jx_market.PO_EAAdministrateEmployeeCreate.createEmployee.Error2.Label"),
                        Labels.getLabel("pe.com.jx_market.PO_EAAdministrateEmployeeCreate.createEmployee.Error2.Label"),
                        null);
            }
            cleanFields();
        } else {
            alertaInfo(this.logger,
                        Labels.getLabel("pe.com.jx_market.PO_EAAdministrateEmployeeCreate.createEmployee.Info2.Label"),
                        Labels.getLabel("pe.com.jx_market.PO_EAAdministrateEmployeeCreate.createEmployee.Info2.Label"),
                        null);
        }
    }

    /**
     * @return {@link DTO_User} with the user created.
     */
    protected DTO_User createUser()
    {
        final DTO_User user = new DTO_User();
        user.setCompanyId(this.company.getId());
        user.setUsername(this.txtUser.getValue());
        user.setRoleId(((DTO_Role) this.bndRole.getAttribute(Constantes.ATTRIBUTE_ROLE)).getId());
        user.setPassword(this.txtPass.getValue());
        if (this.txtPass.getValue().equals(this.txtRepPass.getValue())) {
            final ServiceInput<DTO_User> input2 = new ServiceInput<DTO_User>();
            input2.setAction(Constantes.V_REGISTER);
            input2.setObject(user);
            final ServiceOutput<DTO_User> output2 = this.userService.execute(input2);
            if (Constantes.OK == output2.getErrorCode()) {
                alertaInfo(this.logger, "",
                        Labels.getLabel("pe.com.jx_market.PO_EAAdministrateEmployeeCreate.createEmployee.Info3.Label"),
                        null);
            } else {
                alertaError(this.logger,
                        Labels.getLabel("pe.com.jx_market.PO_EAAdministrateEmployeeCreate.createEmployee.Error.Label"),
                        Labels.getLabel("pe.com.jx_market.PO_EAAdministrateEmployeeCreate.createEmployee.Error.Label"),
                        null);
            }
        }
        return user;
    }

    /**
     *
     */
    public void cleanFields()
    {
        this.txtName.setValue("");
        this.txtLastName.setValue("");
        this.txtLastName2.setValue("");
        this.cmbDocType.setSelectedItem(null);
        this.txtDocument.setValue("");
        this.datBirthday.setValue(null);
        this.txtPhone.setValue("");
        this.txtCellphone.setValue("");
        this.txtEMail.setValue("");
        this.txtUser.setValue("");
        this.txtPass.setValue("");
        this.txtRepPass.setValue("");
        this.txtAddress.setValue("");
        this.cmbDepartment.setSelectedItem(null);
        this.cmbProvince.setSelectedItem(null);
        this.cmbDistrict.setSelectedItem(null);
        this.cmbActive.setSelectedItem(null);
        this.bndRole.setValue(null);
        this.bndRole.removeAttribute(Constantes.ATTRIBUTE_ROLE);
        this.cmbCivilState.setSelectedItem(null);
        this.cmbSex.setSelectedItem(null);
    }

    /**
     *
     */
    public void buildRoles()
    {
        final DTO_Role roleSe = new DTO_Role();
        roleSe.setCompanyId(this.company.getId());
        final ServiceInput<DTO_Role> input = new ServiceInput<DTO_Role>(roleSe);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput<DTO_Role> output = this.roleService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final Map<DTO_Area, List<DTO_Role>> mapArea2Roles = new HashMap<DTO_Area, List<DTO_Role>>();
            final List<DTO_Role> lstRoles = output.getLista();
            for (final DTO_Role role : lstRoles) {
                if (mapArea2Roles.containsKey(role.getArea())) {
                    final List<DTO_Role> lstRoles4Map = mapArea2Roles.get(role.getArea());
                    lstRoles4Map.add(role);
                    // mapArea2Roles.put(role.getArea(), lstRoles4Map);
                } else {
                    final List<DTO_Role> lstRoles4Map = new ArrayList<DTO_Role>();
                    lstRoles4Map.add(role);
                    mapArea2Roles.put(role.getArea(), lstRoles4Map);
                }
                /*
                 * final Comboitem item = new Comboitem(role.getRoleName());
                 * item.setAttribute("role", role); cmbRole.appendChild(item);
                 */
            }
            final Bandpopup bandPop = new Bandpopup();
            /*for (final Entry<DTO_Area, List<DTO_Role>> entry : mapArea2Roles.entrySet()) {
                final Listbox lstBRoles = new Listbox();
                lstBRoles.setWidth("250px");
                final Listhead lstHead = new Listhead();
                final Listheader lstHeader = new Listheader(entry.getKey().getAreaName());
                lstHead.appendChild(lstHeader);
                lstBRoles.appendChild(lstHead);
                for (final DTO_Role role : entry.getValue()) {
                    final Listitem item = new Listitem();
                    item.setValue(role);
                    final Listcell cell = new Listcell(role.getRoleName());
                    item.appendChild(cell);
                    item.addEventListener(Events.ON_CLICK, new EventListener<Event>()
                    {
                        @Override
                        public void onEvent(final Event _event)
                            throws UiException
                        {
                            final Bandbox bandBox = (Bandbox) _event.getTarget().getParent().getParent().getParent();
                            final DTO_Role rol = ((Listitem) _event.getTarget()).getValue();
                            bandBox.setValue(rol.getRoleName());
                            bandBox.setAttribute(Constantes.ATTRIBUTE_ROLE, role);
                            bandBox.close();
                        }
                    });
                    lstBRoles.appendChild(item);
                }
                bandPop.appendChild(lstBRoles);
            }*/
            final Listbox lstBRoles = new Listbox();
            lstBRoles.setWidth("250px");
            final Listhead lstHead = new Listhead();
            final Listheader lstHeader = new Listheader();
            lstHead.appendChild(lstHeader);
            lstBRoles.appendChild(lstHead);
            for (final Entry<DTO_Area, List<DTO_Role>> entry : mapArea2Roles.entrySet()) {
                final Listgroup lstGrp = new Listgroup();
                lstGrp.appendChild(new Listcell(entry.getKey().getAreaName()));
                lstBRoles.appendChild(lstGrp);
                for (final DTO_Role role : entry.getValue()) {
                    final Listitem item = new Listitem();
                    item.setValue(role);
                    final Listcell cell = new Listcell(role.getRoleName());
                    item.appendChild(cell);
                    item.addEventListener(Events.ON_CLICK, new EventListener<Event>()
                    {
                        @Override
                        public void onEvent(final Event _event)
                            throws UiException
                        {
                            final Bandbox bandBox = (Bandbox) _event.getTarget().getParent().getParent().getParent();
                            final DTO_Role rol = ((Listitem) _event.getTarget()).getValue();
                            bandBox.setValue(rol.getRoleName());
                            bandBox.setAttribute(Constantes.ATTRIBUTE_ROLE, role);
                            bandBox.close();
                        }
                    });
                    lstBRoles.appendChild(item);
                }
            }
            bandPop.appendChild(lstBRoles);
            this.bndRole.appendChild(bandPop);
        } else {
            alertaError(this.logger, "Error en la carga de roles",
                            "error al cargar los roles", null);
        }
    }

    /**
     *
     */
    protected void buildDepartmentCombo()
    {
        getDepartments(this.cmbDepartment, this.cmbProvince);
    }

    /**
     * @param _departmentId
     */
    protected void buildProvinceCombo(final Integer _departmentId)
    {
        getProvinces(_departmentId, this.cmbProvince, this.cmbDistrict);
    }

    /**
     * @param _departmentId
     * @param _provinceId
     */
    protected void buildDistrictCombo(final Integer _departmentId,
                                      final Integer _provinceId)
    {
        getDistricts(_departmentId, _provinceId, this.cmbDistrict, null);
    }

    @Override
    protected void buildNextCombo(final Combobox _combo)
    {
        if (_combo.equals(this.cmbProvince)) {
            buildProvinceCombo(((Ubication) this.cmbDepartment.getSelectedItem().getValue()).getId());
        } else if (_combo.equals(this.cmbDistrict)) {
            buildDistrictCombo(((Ubication) this.cmbDepartment.getSelectedItem().getValue()).getId(),
                            ((Ubication) this.cmbProvince.getSelectedItem().getValue()).getId());
        }
    }

    /**
     * @param _event Event
     */
    @Listen("onClick = #btnClose")
    public void close(final Event _event)
    {
        this.wEAAEC.detach();
    }

    @Override
    String[] requiredResources()
    {
        return new String[]{Constantes.MODULE_ADM_EMPLOYEE_CREATE };
    }
}
