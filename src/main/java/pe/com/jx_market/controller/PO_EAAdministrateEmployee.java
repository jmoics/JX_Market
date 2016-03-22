package pe.com.jx_market.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

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

public class PO_EAAdministrateEmployee
    extends SecuredComposer<Window>
{

    private final Log logger = LogFactory.getLog(PO_EAAdministrateEmployee.class);
    @Wire
    private Listbox lstEmp;
    @Wire
    private Popup popDetails;
    @Wire
    private Label lblUser, lblName, lblLastName, lblSecLastName, lblDocType, lblDocument, lblRole, lblUbication,
                  lblPhone, lblMail, lblCity, lblStatus, lblBirthday, lblAddress, lblUbigeo, lblCivilState, lblSex;
    @Wire
    private Button btnView;
    @Wire
    private Combobox cmbRole, cmbEstado, cmbDocType;
    @Wire
    private Window wEAAE;
    @Wire
    private Textbox txtDocument, txtLastName, txtSecLastName, txtName;
    @WireVariable
    private BusinessService<DTO_Employee> employeeService;
    @WireVariable
    private BusinessService<DTO_Role> roleService;
    @WireVariable
    private BusinessService<DTO_User> userService;
    @WireVariable
    private Desktop desktop;
    private DTO_Company company;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);
        this.company = (DTO_Company) this.desktop.getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        buildParameterCombo(this.cmbDocType, Constantes.PARAM_DOCUMENT_TYPE, null);
        // this.btnView.setPopup(this.popDetails);
    }

    /**
     *
     */
    @Listen("onClick = #btnClean")
    public void cleanSearch()
    {
        this.cmbDocType.setSelectedItem(null);
        this.txtDocument.setValue(null);
        this.txtLastName.setValue(null);
        this.txtSecLastName.setValue(null);
    }

    /**
     *
     */
    @SuppressWarnings("unchecked")
    public void loadEmployeeDetail()
    {
        if (this.lstEmp.getSelectedItem() != null) {
            final DTO_Employee employee = (DTO_Employee) this.lstEmp.getSelectedItem().getAttribute(
                            Constantes.ATTRIBUTE_EMPLOYEE);
            this.lblUser.setValue((employee.getUser()).getUsername());
            this.lblName.setValue(employee.getEmployeeName());
            this.lblLastName.setValue(employee.getEmployeeLastName());
            this.lblSecLastName.setValue(employee.getEmployeeLastName2());
            this.lblRole.setValue(employee.getUser().getRole().getRoleName());
            this.lblCity.setValue(employee.getCity());
            this.lblDocType.setValue(getParameter(employee.getDocumentTypeId()).getParameterName());
            this.lblDocument.setValue(employee.getDocumentNumber());
            this.lblBirthday.setValue(new SimpleDateFormat("dd/MM/yyyy").format(employee.getBirthday()));
            this.lblSex.setValue(getParameter(employee.getSexId()).getParameterName());
            this.lblCivilState.setValue(getParameter(employee.getCivilStateId()).getParameterName());
            this.lblAddress.setValue(employee.getAddress());
            this.lblUbigeo.setValue(employee.getUbigeo());
            final String estado = Constantes.ST_ACTIVO.equals(employee.isActive()) ? Constantes.STATUS_ACTIVO
                            : Constantes.STATUS_INACTIVO;
            this.lblStatus.setValue(estado);
            this.lblMail.setValue(employee.getEmail());
            this.lblPhone.setValue(employee.getPhone());
            this.lblStatus.setValue(employee.isActive() ? Labels.getLabel("pe.com.jx_market.Active.TRUE")
                            : Labels.getLabel("pe.com.jx_market.Active.FALSE"));
            final StringBuilder strBuilder = new StringBuilder();
            final Map<Integer, Ubication> mapUbi = (Map<Integer, Ubication>) this.desktop.getSession()
                            .getAttribute(Constantes.ATTRIBUTE_UBICATION);
            if (employee.getDepartmentId() != null && employee.getProvinceId() != null
                            && employee.getDistrictId() != null) {
                strBuilder.append(mapUbi.get(employee.getDepartmentId()).getName()).append(" - ")
                            .append(mapUbi.get(employee.getProvinceId()).getName()).append(" - ")
                            .append(mapUbi.get(employee.getDistrictId()).getName());
                this.lblUbication.setValue(strBuilder.toString());
            }
        }
    }

    /**
     *
     */
    @Listen("onClick = #btnSearch")
    public void searchEmployees()
    {
        this.lstEmp.getItems().clear();
        final DTO_Employee emp = new DTO_Employee();
        emp.setCompanyId(this.company.getId());
        if (this.cmbDocType.getSelectedItem() != null && this.txtDocument.getValue() != null) {
            emp.setDocumentTypeId(((Parameter) this.cmbDocType.getSelectedItem().getValue()).getId());
            emp.setDocumentNumber(this.txtDocument.getValue());
        }
        if (this.txtLastName.getValue() != null && !this.txtLastName.getValue().isEmpty()) {
            emp.setEmployeeLastName(this.txtLastName.getValue());
            if (this.txtSecLastName.getValue() != null && !this.txtSecLastName.getValue().isEmpty()) {
                emp.setEmployeeLastName2(this.txtSecLastName.getValue());
            }
            if (this.txtName.getValue() != null && !this.txtName.getValue().isEmpty()) {
                emp.setEmployeeName(this.txtName.getValue());
            }
        }
        final ServiceInput<DTO_Employee> input = new ServiceInput<DTO_Employee>(emp);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput<DTO_Employee> output = this.employeeService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Employee> lstEmployee = output.getLista();
            int countItem = 1;
            for (final DTO_Employee emplo : lstEmployee) {
                final Listitem item = new Listitem();
                item.setAttribute(Constantes.ATTRIBUTE_EMPLOYEE, emplo);
                // Fecha Creacion
                /*
                 fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
                 .format(uOut.getFecha_creacion())));
                 */
                // Username
                // fila.appendChild(new Label(uOut.getUser()));
                Listcell cell = new Listcell();
                cell.setLabel("" + countItem);
                item.appendChild(cell);
                cell = new Listcell();
                cell.setLabel(getParameter(emplo.getDocumentTypeId()).getParameterName());
                item.appendChild(cell);
                cell = new Listcell();
                cell.setLabel(emplo.getDocumentNumber());
                item.appendChild(cell);
                cell = new Listcell();
                cell.setLabel(emplo.getEmployeeLastName());
                item.appendChild(cell);
                cell = new Listcell();
                cell.setLabel(emplo.getEmployeeLastName2());
                item.appendChild(cell);
                cell = new Listcell();
                cell.setLabel(emplo.getEmployeeName());
                item.appendChild(cell);
                cell = new Listcell();
                cell.setLabel(emplo.getUser().getUsername());
                item.appendChild(cell);
                cell = new Listcell();
                cell.setLabel(emplo.getUser().getRole().getRoleName());
                item.appendChild(cell);

                /*final Image imgDet = new Image("media/details.png");
                imgDet.setStyle("cursor:pointer");
                imgDet.setPopup(this.popDetails);*/
                item.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                    @Override
                    public void onEvent(final Event _event)
                        throws UiException
                    {
                        loadEmployeeDetail();
                        popDetails.open(_event.getTarget().getParent(), "overlap");
                    }
                });
                this.lstEmp.appendChild(item);
                countItem++;
            }
        }
    }

    /**
     * @param _event Event
     */
    @Listen("onClick = #btnView")
    public void runWindowView(final MouseEvent _event)
    {
        if (this.lstEmp.getSelectedItem() != null) {
            loadEmployeeDetail();
            this.popDetails.open(this.lstEmp.getSelectedItem().getParent(), "overlap");
        } else {
            alertaInfo(this.logger,
                            Labels.getLabel("pe.com.jx_market.PO_EAAdministrateEmployee.runWindowView.Info.Label"),
                            "No se selecciono un registro a consultar", null);
        }
    }

    /**
     * @param _event Event.
     */
    @Listen("onClick = #btnEdit")
    public void runWindowEdit(final MouseEvent _event)
    {
        if (this.lstEmp.getSelectedItem() != null) {
            this.desktop.getSession().setAttribute(Constantes.ATTRIBUTE_EMPLOYEE,
                            this.lstEmp.getSelectedItem().getAttribute(Constantes.ATTRIBUTE_EMPLOYEE));
            final Map<String, Object> dataArgs = new HashMap<String, Object>();
            dataArgs.put(Constantes.ATTRIBUTE_PARENTFORM, this);
            final Window w = (Window) Executions.createComponents(Constantes.Form.EMPLOYEE_EDIT_FORM.getForm(),
                            null, dataArgs);
            w.setPage(this.wEAAE.getPage());
            // w.setParent(wEAT);
            w.doModal();
            // w.doHighlighted();
            // w.doEmbedded();
        } else {
            alertaInfo(this.logger,
                            Labels.getLabel("pe.com.jx_market.PO_EAAdministrateEmployee.runWindowEdit.Info.Label"),
                            "No se selecciono un registro a editar", null);
        }
    }

    /**
     * @param _event Event.
     */
    @Listen("onClick = #btnCreate")
    public void runWindowCreate(final MouseEvent _event)
    {
        final Map<String, Object> dataArgs = new HashMap<String, Object>();
        dataArgs.put(Constantes.ATTRIBUTE_PARENTFORM, this);
        final Window w = (Window) Executions.createComponents(Constantes.Form.EMPLOYEE_CREATE_FORM.getForm(),
                        null, dataArgs);
        w.setPage(this.wEAAE.getPage());
        // w.setParent(wEAT);
        // w.doOverlapped();
        w.doModal();
        // w.doEmbedded();
    }

    @Override
    String[] requiredResources()
    {
        return new String[]{Constantes.MODULE_ADM_EMPLOYEE };
    }
}
