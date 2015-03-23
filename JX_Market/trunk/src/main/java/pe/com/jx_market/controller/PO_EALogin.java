package pe.com.jx_market.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
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
    @Autowired
    private BusinessService<DTO_Empresa> empresaService;
    @Autowired
    private BusinessService<DTO_Usuario> authService;
    @Autowired
    private BusinessService<DTO_Empleado> empleadoService;

    @Override
    public void doAfterCompose(final Window comp)
        throws Exception
    {
        super.doAfterCompose(comp);
        obtenerEmpresas();
        txtUser.setFocus(true);
    }

    @SuppressWarnings("unchecked")
    public void obtenerEmpresas()
    {
        List<DTO_Empresa> empresas;
        empresaService = (BusinessService<DTO_Empresa>) Utility.getService(wEAL, "empresaService");
        final DTO_Input<DTO_Empresa> input = new DTO_Input<DTO_Empresa>();
        input.setVerbo("list");

        final DTO_Output<DTO_Empresa> output = empresaService.execute(input);
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

    @Listen("onClick = #btnIngresar; onOK = #btnIngresar")
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
                    desktop.getSession().setAttribute("empleado", empleado);
                    desktop.getSession().setAttribute("login", validado);
                    desktop.getSession().setAttribute("empresa", empresa);
                    if (empresa.getCodigo().equals(Constantes.INSTITUCION_JX_MARKET)) {
                        Executions.sendRedirect("eESolicitudesPendientes.zul");
                    } else {
                        Executions.sendRedirect("eAMenuPrinc.zul");
                    }
                } else {
                    txtUser.setText("");
                    txtUser.setFocus(true);
                    txtPass.setText("");
                    wEAL.getFellow("badauth").setVisible(true);
                }
            } else {
                Messagebox.show("No se cargo la empresa", "JX_Market", Messagebox.OK, Messagebox.INFORMATION);
            }
        } else {
            Messagebox.show("Debe seleccionar una empresa", "JX_Market", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @SuppressWarnings("unchecked")
    public DTO_Usuario getUsuario(final DTO_Usuario C)
    {
        DTO_Usuario usuario;
        authService = (BusinessService<DTO_Usuario>) Utility.getService(wEAL, "authService");
        final DTO_Input<DTO_Usuario> input = new DTO_Input<DTO_Usuario>(C);

        final DTO_Output<DTO_Usuario> output = authService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            usuario = output.getObject();
        } else {
            usuario = null;
        }

        return usuario;
    }

    @SuppressWarnings("unchecked")
    public DTO_Empleado getEmpleado(final DTO_Usuario usu)
    {
        empleadoService = (BusinessService<DTO_Empleado>) Utility.getService(wEAL, "empleadoService");
        final DTO_Empleado emp = new DTO_Empleado();
        emp.setEmpresa(usu.getEmpresa());
        emp.setUsuario(usu.getCodigo());
        final DTO_Input<DTO_Empleado> input = new DTO_Input<DTO_Empleado>(emp);
        input.setVerbo(Constantes.V_GET);
        final DTO_Output<DTO_Empleado> output = empleadoService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            return output.getObject();
        } else {
            return null;
        }
    }
}
