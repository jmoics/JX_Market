package pe.com.jx_market.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listgroup;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Area;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Employee;
import pe.com.jx_market.domain.DTO_Role;
import pe.com.jx_market.domain.DTO_User;
import pe.com.jx_market.domain.Ubication;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_EAAdministrateEmployeeEdit
    extends SecuredComposerModal<Window>
{

    private final Log logger = LogFactory.getLog(PO_EAAdministrateEmployeeEdit.class);
    @Wire
    private Textbox txtUser, txtPass, txtName, txtLastName, txtLastName2, txtDocument, txtPhone, txtCellphone, txtEMail,
                    txtAddress, txtUbigeo, txtCity, txtRepPass;
    @Wire
    private Datebox datBirthday;
    @Wire
    private Combobox cmbDocType, cmbActive, cmbSex, cmbCivilState, cmbDepartment, cmbProvince, cmbDistrict;
    @Wire
    private Bandbox bndRole;
    @Wire
    private Window wEAAEE;
    @Wire
    private Checkbox chbCreateUser;
    @WireVariable
    private BusinessService<DTO_Employee> employeeService;
    @WireVariable
    private BusinessService<DTO_Role> roleService;
    @WireVariable
    private BusinessService<DTO_User> userService;
    @WireVariable
    private Desktop desktop;
    private DTO_Company company;
    private DTO_Employee employee;
    private PO_EAAdministrateEmployee employeeParentUI;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        this.company = (DTO_Company) this.desktop.getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        this.employee = (DTO_Employee) this.desktop.getSession().getAttribute(Constantes.ATTRIBUTE_EMPLOYEE);
        if (this.employee == null) {
            alertaInfo(logger, "", "No se encontro empleado, retornando a busqueda", null);
        } else {
            // Obtenemos el controlador de la pantalla principal de marcas.
            final Map<?, ?> mapArg = this.desktop.getExecution().getArg();
            this.employeeParentUI = (PO_EAAdministrateEmployee) mapArg.get(Constantes.ATTRIBUTE_PARENTFORM);
            loadData();
        }
    }

    private void loadData()
    {
        buildRoles();
        buildActiveCombo(this.cmbActive);
        buildParameterCombo(this.cmbDocType, Constantes.PARAM_DOCUMENT_TYPE, this.employee.getDocumentTypeId());
        buildParameterCombo(this.cmbSex, Constantes.PARAM_SEX_TYPE, this.employee.getSexId());
        buildParameterCombo(this.cmbCivilState, Constantes.PARAM_CIVILSTATE_TYPE, this.employee.getCivilStateId());
        if (this.employee.getDepartmentId() != null) {
            buildDepartmentCombo();
            /*buildProvinceCombo(this.employee.getDepartmentId());
            if (this.employee.getProvinceId() != null) {
                buildDistrictCombo(this.employee.getDepartmentId(), this.employee.getProvinceId());
            }*/
        }
        this.txtName.setValue(this.employee.getEmployeeLastName());
        this.txtLastName.setValue(this.employee.getEmployeeLastName());
        this.txtLastName2.setValue(this.employee.getEmployeeLastName2());
        this.txtDocument.setValue(this.employee.getDocumentNumber());
        this.txtAddress.setValue(this.employee.getAddress() != null ? this.employee.getAddress() : "");
        this.txtPhone.setValue(this.employee.getPhone() != null ? this.employee.getPhone() : "");
        this.txtCellphone.setValue(this.employee.getCellPhone() != null ? this.employee.getCellPhone() : "");
        // this.txtCity.setValue(this.employee.getCity() != null ? this.employee.getCity() : "");
        this.txtEMail.setValue(this.employee.getEmail() != null ? this.employee.getEmail() : "");

        txtUser.setAttribute("user", employee);
        txtUser.setReadonly(true);
        //txtUser.setValue((getUser(employee.getUserId())).getUsername());
    }

    public void cancelar()
    {
        txtUser.setReadonly(false);
        //CargarTabla();
    }

    @Listen("onClick = #btnSave")
    public void editEmployee()
    {
        /*if (!txtUser.getValue().equals("") && !txtNombre.getValue().equals("") &&
                        !txtApellidos.getValue().equals("") && !txtDNI.getValue().equals("") &&
                        cmbRole.getSelectedItem() != null && cmbEstado.getSelectedItem() != null) {

            editar((DTO_Employee) txtUser.getAttribute("user"), txtUser.getValue(),
                            txtPass.getValue(), txtNombre.getValue(), txtApellidos.getValue(),
                            txtDNI.getValue(), txtTelefono.getValue(), txtMail.getValue(),
                            (Boolean)cmbEstado.getSelectedItem().getValue(),
                            ((DTO_Role) cmbRole.getSelectedItem().getAttribute("role")).getId(),
                            txtCelular.getValue(), txtCiudad.getValue(), txtDireccion.getValue(), txtRegion.getValue());
            limpiarCrear();
            txtUser.setReadonly(false);
        } else { // validar para cada campo obligatorio
            alertaInfo(logger, "Faltan llenar algunos campos", "No se llenaron los campos obligatorios", null);
        }*/
    }

    @Listen("onClick = #btnClose")
    public void close(final Event _event)
    {
        wEAAEE.detach();
    }

    /*public void limpiarCrear()
    {
        txtNombre.setValue("");
        txtApellidos.setValue("");
        txtTelefono.setValue("");
        txtMail.setValue("");
        txtUser.setValue("");
        txtPass.setValue("");
        cmbEstado.setSelectedItem(null);
        cmbRole.setSelectedItem(null);
    }*/

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
                } else {
                    final List<DTO_Role> lstRoles4Map = new ArrayList<DTO_Role>();
                    lstRoles4Map.add(role);
                    mapArea2Roles.put(role.getArea(), lstRoles4Map);
                }
            }
            final Bandpopup bandPop = new Bandpopup();
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
                    if (this.employee.getUser() != null
                                    && this.employee.getUser().getRole().getId().equals(role.getId())) {
                        this.bndRole.setValue(role.getRoleName());
                        this.bndRole.setAttribute(Constantes.ATTRIBUTE_ROLE, role);
                    }
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

    @Override
    protected Comboitem buildCombo4Ubications(final Combobox _comboParent,
                                              final Ubication _ubi)
    {
        final Comboitem item = super.buildCombo4Ubications(_comboParent, _ubi);
        if (_comboParent.equals(this.cmbDepartment)) {
            if (this.employee.getDepartmentId().equals(_ubi.getId())) {
                this.cmbDepartment.setSelectedItem(item);
            }
        } else if (_comboParent.equals(this.cmbProvince)) {
            if (this.employee.getProvinceId().equals(_ubi.getId())) {
                this.cmbProvince.setSelectedItem(item);
            }
        } else if (_comboParent.equals(this.cmbDistrict)) {
            if (this.employee.getDistrictId().equals(_ubi.getId())) {
                this.cmbDistrict.setSelectedItem(item);
            }
        }
        return item;
    }

    @Override
    public void buildActiveCombo(final Combobox _combo)
    {
        super.buildActiveCombo(this.cmbActive);
        for (final Comboitem item : this.cmbActive.getItems()) {
            if (this.employee.isActive().equals(item.getValue())) {
                this.cmbActive.setSelectedItem(item);
            }
        }
    }

    @Override
    String[] requiredResources()
    {
        return new String[]{Constantes.MODULE_ADM_EMPLOYEE };
    }
}
