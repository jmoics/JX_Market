package pe.com.jx_market.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Cliente;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.domain.DTO_Usuario;
import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

public class PO_EmpLoginAsociado
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

    public void authenticate()
    {
        DTO_Usuario usuario = new DTO_Usuario();
        usuario.setUsername(txtUser.getValue());
        usuario.setContrasena(txtPass.getValue());
        usuario.setEmpresa(1);

        DTO_Usuario validado = (DTO_Usuario) getUsuario(usuario);
        if (validado != null) {
            getDesktop().getSession().setAttribute("login", validado);
            Executions.sendRedirect("empMenuPrincAsociado.zul");
        } else {
            txtUser.setText("");
            txtUser.setFocus(true);
            txtPass.setText("");
            getFellow("badauth").setVisible(true);
        }
    }

    public void obtenerEmpresas()
    {
        List<DTO_Empresa> empresas;
        BusinessService empresaService = Utility.getService(this, "empresaService");
        DTO_Input input = new DTO_Input();
        input.setVerbo("list");

        DTO_Output output = empresaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            empresas = (List<DTO_Empresa>) output.getLista();
            for (DTO_Empresa emp : empresas) {
                Comboitem item = new Comboitem();
                item.setLabel(emp.getRazonsocial());
                item.setAttribute("empresa", emp);
                cmbEmp.appendChild(item);
            }
        } else {
            empresas = null;
        }
    }

    public DTO_Usuario getUsuario(DTO_Usuario C)
    {
        DTO_Usuario usuario;
        BusinessService authService = Utility.getService(this, "authService");
        DTO_Input input = new DTO_Input(C);

        DTO_Output output = authService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            usuario = (DTO_Usuario) output.getObject();
        } else {
            usuario = null;
        }

        return usuario;
    }
}
