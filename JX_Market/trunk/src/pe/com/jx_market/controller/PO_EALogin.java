package pe.com.jx_market.controller;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Empleado;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.domain.DTO_Usuario;
import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

public class PO_EALogin
    extends Window
{
    private Textbox txtUser, txtPass;
    private Combobox cmbEmp;

    public void onCreate()
    {
        txtUser = (Textbox) getFellow("txtUser");
        txtPass = (Textbox) getFellow("txtPass");
        cmbEmp = (Combobox) getFellow("cmbEmp");
        obtenerEmpresas();
        txtUser.setFocus(true);
    }

    public void obtenerEmpresas()
    {
        List<DTO_Empresa> empresas;
        final BusinessService empresaService = Utility.getService(this, "empresaService");
        final DTO_Input input = new DTO_Input();
        input.setVerbo("list");

        final DTO_Output output = empresaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            empresas = output.getLista();
            for (final DTO_Empresa emp : empresas) {
                final Comboitem item = new Comboitem();
                item.setLabel(emp.getRazonsocial());
                item.setAttribute("empresa", emp);
                cmbEmp.appendChild(item);
            }
        } else {
            empresas = null;
        }
    }

    public void authenticate()
        throws InterruptedException
    {
        final DTO_Usuario usuario = new DTO_Usuario();
        usuario.setUsername(txtUser.getValue());
        usuario.setContrasena(txtPass.getValue());
        if (cmbEmp.getSelectedItem() != null) {
            final DTO_Empresa empresa = (DTO_Empresa) cmbEmp.getSelectedItem().getAttribute("empresa");
            if (empresa != null) {
                usuario.setEmpresa(empresa.getCodigo());

                final DTO_Usuario validado = getUsuario(usuario);
                if (validado != null) {
                    final DTO_Empleado empleado = getEmpleado(validado);
                    getDesktop().getSession().setAttribute("empleado", empleado);
                    getDesktop().getSession().setAttribute("login", validado);
                    getDesktop().getSession().setAttribute("empresa", empresa);
                    Executions.sendRedirect("eAMenuPrinc.zul");
                } else {
                    txtUser.setText("");
                    txtUser.setFocus(true);
                    txtPass.setText("");
                    getFellow("badauth").setVisible(true);
                }
            } else {
                Messagebox.show("No se cargo la empresa", "JX_Market", Messagebox.OK, Messagebox.INFORMATION);
            }
        } else {
            Messagebox.show("Debe seleccionar una empresa", "JX_Market", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    public DTO_Usuario getUsuario(final DTO_Usuario C)
    {
        DTO_Usuario usuario;
        final BusinessService authService = Utility.getService(this, "authService");
        final DTO_Input input = new DTO_Input(C);

        final DTO_Output output = authService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            usuario = (DTO_Usuario) output.getObject();
        } else {
            usuario = null;
        }

        return usuario;
    }

    public DTO_Empleado getEmpleado(final DTO_Usuario usu)
    {
        final BusinessService empleadoService = Utility.getService(this, "empleadoService");
        final DTO_Empleado emp = new DTO_Empleado();
        emp.setEmpresa(usu.getEmpresa());
        emp.setUsuario(usu.getCodigo());
        final DTO_Input input = new DTO_Input(emp);
        input.setVerbo(Constantes.V_GET);
        final DTO_Output output = empleadoService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            return (DTO_Empleado) output.getObject();
        } else {
            return null;
        }
    }
}
