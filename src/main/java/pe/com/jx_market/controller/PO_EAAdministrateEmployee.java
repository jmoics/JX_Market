package pe.com.jx_market.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
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
    private Label lblUser, lblName, lblLastName, lblSecLastName, lblDocType, lblDocument, lblRole,
                  lblPhone, lblMail, lblCity, lblStatus;
    @Wire
    private Combobox cmbRole, cmbEstado, cmbDocType;
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
        buildParameterCombo(this.cmbDocType, Constantes.PARAM_DOCUMENT_TYPE);
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
    public void loadEmployeeDetail()
    {
        if (this.lstEmp.getSelectedItem() != null) {
            final DTO_Employee employee = (DTO_Employee) this.lstEmp.getSelectedItem().getAttribute(
                            Constantes.ATTRIBUTE_EMPLOYEE);
            this.lblUser.setValue((employee.getUser()).getUsername());
            this.lblName.setValue(employee.getEmployeeName());
            this.lblLastName.setValue(employee.getEmployeeLastName());
            this.lblRole.setValue(employee.getUser().getRole().getRoleName());
            this.lblCity.setValue(employee.getCity());
            this.lblDocument.setValue(employee.getDocumentNumber());
            final String estado = Constantes.ST_ACTIVO.equals(employee.getActive()) ? Constantes.STATUS_ACTIVO
                            : Constantes.STATUS_INACTIVO;
            this.lblStatus.setValue(estado);
            this.lblMail.setValue(employee.getEmail());
            this.lblPhone.setValue(employee.getPhone());
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
            final int countItem = 1;
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
                imgDet.setPopup(this.popDetails);
                imgDet.addEventListener(Events.ON_CLICK,
                    new org.zkoss.zk.ui.event.EventListener<Event>() {
                        @Override
                        public void onEvent(final Event _event)
                            throws UiException
                        {
                             cargarPop((DTO_Employee) ((Row)
                             _event.getTarget().getParent()).getAttribute(Constantes.ATTRIBUTE_EMPLOYEE));

                        }
                    });*/
                this.lstEmp.appendChild(item);
            }
        }
    }

    @Override
    String[] requiredResources()
    {
        return new String[]{Constantes.MODULE_ADM_EMPLOYEE };
    }
}
