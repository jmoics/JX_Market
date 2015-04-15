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
import pe.com.jx_market.domain.DTO_Empleado;
import pe.com.jx_market.domain.DTO_Usuario;
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
    private BusinessService<DTO_Usuario> authService;
    @WireVariable
    private BusinessService<DTO_Empleado> empleadoService;

    @Override
    public void doAfterCompose(final Window comp)
        throws Exception
    {
        super.doAfterCompose(comp);
        obtenerEmpresas();
        txtUser.setFocus(true);
    }

    public void obtenerEmpresas()
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
        final DTO_Usuario usuario = new DTO_Usuario();
        usuario.setUsername(txtUser.getValue());
        usuario.setContrasena(txtPass.getValue());
        if (cmbEmp.getSelectedItem() != null) {
            final DTO_Company company = (DTO_Company) cmbEmp.getSelectedItem().getAttribute("company");
            if (company != null) {
                usuario.setEmpresa(company.getId());

                final DTO_Usuario validado = getUsuario(usuario);
                if (validado != null) {
                    final DTO_Empleado empleado = getEmpleado(validado);
                    desktop.getSession().setAttribute("empleado", empleado);
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

    public DTO_Usuario getUsuario(final DTO_Usuario C)
    {
        DTO_Usuario usuario;
        //authService = (BusinessService<DTO_Usuario>) ContextLoader.getService(wEAL, "authService");
        final ServiceInput<DTO_Usuario> input = new ServiceInput<DTO_Usuario>(C);

        final ServiceOutput<DTO_Usuario> output = authService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            usuario = output.getObject();
        } else {
            usuario = null;
        }

        return usuario;
    }

    public DTO_Empleado getEmpleado(final DTO_Usuario usu)
    {
        //empleadoService = (BusinessService<DTO_Empleado>) ContextLoader.getService(wEAL, "empleadoService");
        final DTO_Empleado emp = new DTO_Empleado();
        emp.setEmpresa(usu.getEmpresa());
        emp.setUsuario(usu.getCodigo());
        final ServiceInput<DTO_Empleado> input = new ServiceInput<DTO_Empleado>(emp);
        input.setAccion(Constantes.V_GET);
        final ServiceOutput<DTO_Empleado> output = empleadoService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            return output.getObject();
        } else {
            return null;
        }
    }
}
