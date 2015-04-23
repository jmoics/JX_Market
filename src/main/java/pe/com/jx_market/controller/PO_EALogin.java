package pe.com.jx_market.controller;

import java.util.List;

import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Employee;
import pe.com.jx_market.domain.DTO_User;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/* Permite tener acceso a los servicios*/
@VariableResolver(DelegatingVariableResolver.class)
public class PO_EALogin
    extends SelectorComposer<Window>
{

    private static final long serialVersionUID = -1869322490528675540L;
    @Wire
    private Textbox txtUser, txtPass;
    @Wire
    private Combobox cmbEmp;
    @WireVariable
    private Desktop desktop;
    @Wire
    private Window wEAL;
    // En este caso companyService esta siendo obtenido de los beans de spring.
    @WireVariable
    private BusinessService<DTO_Company> companyService;
    @WireVariable
    private BusinessService<DTO_User> authService;
    @WireVariable
    private BusinessService<DTO_Employee> employeeService;

    @Override
    public void doAfterCompose(final Window comp)
        throws Exception
    {
        super.doAfterCompose(comp);
        obtenerCompanys();
        txtUser.setFocus(true);
    }

    public void obtenerCompanys()
    {
        List<DTO_Company> companys;
        //companyService = (BusinessService<DTO_Company>) ContextLoader.getService(wEAL, "companyService");
        final ServiceInput<DTO_Company> input = new ServiceInput<DTO_Company>();
        input.setAccion("list");

        final ServiceOutput<DTO_Company> output = companyService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            companys = output.getLista();
            for (final DTO_Company emp : companys) {
                final Comboitem item = new Comboitem();
                item.setLabel(emp.getBusinessName());
                item.setAttribute("company", emp);
                cmbEmp.appendChild(item);
            }
        } else {
            companys = null;
        }
    }

    @Listen("onClick = #btnIngresar; onOK = #txtPass")
    public void authenticate()
        throws InterruptedException
    {
        final DTO_User user = new DTO_User();
        user.setUsername(txtUser.getValue());
        user.setPassword(txtPass.getValue());
        if (cmbEmp.getSelectedItem() != null) {
            final DTO_Company company = (DTO_Company) cmbEmp.getSelectedItem().getAttribute("company");
            if (company != null) {
                user.setCompanyId(company.getId());

                final DTO_User validado = getUser(user);
                if (validado != null) {
                    final DTO_Employee employee = getEmployee(validado);
                    desktop.getSession().setAttribute("employee", employee);
                    desktop.getSession().setAttribute("login", validado);
                    desktop.getSession().setAttribute("company", company);
                    if (company.getId().equals(Constantes.INSTITUCION_JX_MARKET)) {
                        Executions.sendRedirect("eESolicitudesPendientes.zul");
                    } else {
                        Executions.sendRedirect("eAMainMenu.zul");
                    }
                } else {
                    txtUser.setText("");
                    txtUser.setFocus(true);
                    txtPass.setText("");
                    wEAL.getFellow("badauth").setVisible(true);
                }
            } else {
                Messagebox.show("No se cargo la company", "JX_Market", Messagebox.OK, Messagebox.INFORMATION);
            }
        } else {
            Messagebox.show("Debe seleccionar una company", "JX_Market", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    public DTO_User getUser(final DTO_User C)
    {
        DTO_User user;
        //authService = (BusinessService<DTO_User>) ContextLoader.getService(wEAL, "authService");
        final ServiceInput<DTO_User> input = new ServiceInput<DTO_User>(C);

        final ServiceOutput<DTO_User> output = authService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            user = output.getObject();
        } else {
            user = null;
        }

        return user;
    }

    public DTO_Employee getEmployee(final DTO_User usu)
    {
        //employeeService = (BusinessService<DTO_Employee>) ContextLoader.getService(wEAL, "employeeService");
        final DTO_Employee emp = new DTO_Employee();
        emp.setCompanyId(usu.getCompanyId());
        emp.setUserId(usu.getId());
        final ServiceInput<DTO_Employee> input = new ServiceInput<DTO_Employee>(emp);
        input.setAccion(Constantes.V_GET);
        final ServiceOutput<DTO_Employee> output = employeeService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            return output.getObject();
        } else {
            return null;
        }
    }
}
