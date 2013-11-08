package pe.com.jx_market.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Div;
import org.zkoss.zul.Textbox;

import pe.com.jx_market.domain.DTO_Articulo;
import pe.com.jx_market.domain.DTO_Cliente;
import pe.com.jx_market.domain.DTO_Usuario;
import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

public class PO_CELoginCliente extends Div
{
    static Log logger = LogFactory.getLog(PO_CELoginCliente.class);
    private Textbox txtUser, txtPass;
    private BusinessService validationService, usuarioService, clienteService;

    public void onCreate() {
        txtUser = (Textbox) getFellow("txtUser");
        txtPass = (Textbox) getFellow("txtPass");
        validationService = Utility.getService(this, "validationService");
        usuarioService = Utility.getService(this, "usuarioService");
        clienteService = Utility.getService(this, "clienteService");
    }

    public void iniciarSesion() {
        final DTO_Usuario usuario = new DTO_Usuario();
        usuario.setUsername(txtUser.getValue());
        usuario.setContrasena(txtPass.getValue());
        usuario.setEmpresa(Constantes.INSTITUCION_JX_MARKET);

        final DTO_Usuario validado = getUsuario(usuario);
        if (validado != null) {
            final DTO_Cliente cliente = getCliente(validado);
            getDesktop().getSession().setAttribute("cliente", cliente);
            final Map<Integer, Map<DTO_Articulo, Integer>> map = new HashMap<Integer, Map<DTO_Articulo, Integer>>();
            getDesktop().getSession().setAttribute("carrito", map);
            final Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("total", BigDecimal.ZERO);
            map2.put("cantidad", 0);
            getDesktop().getSession().setAttribute("totales", map2);
            getDesktop().getSession().setAttribute("sendPage", getDesktop().getBookmark());
            Executions.sendRedirect("index.zul");
        } else {
            txtUser.setText("");
            txtUser.setFocus(true);
            txtPass.setText("");
        }
    }

    public DTO_Cliente getCliente(final DTO_Usuario usu)
    {
        final DTO_Cliente cli = new DTO_Cliente();
        cli.setEmpresa(usu.getEmpresa());
        cli.setUsuario(usu.getCodigo());
        final DTO_Input input = new DTO_Input(cli);
        input.setVerbo(Constantes.V_GET);
        final DTO_Output output = clienteService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            return (DTO_Cliente) output.getObject();
        } else {
            return null;
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

    public void saltarPagina(final String txt, final boolean anotherPage) {
        if (getDesktop().getBookmark().contains(txt)) {
            Executions.sendRedirect(null);
        } else if (anotherPage) {
            getDesktop().getSession().setAttribute("sendPage", getDesktop().getBookmark());
            Executions.sendRedirect(txt);
        }
    }

    public void alertaInfo(final String txt,
                           final String txt2,
                           final Throwable t)
    {
        if (txt.length() > 0) {
            Messagebox.show(txt, "JX_Market", 1, Messagebox.INFORMATION);
        }
        if (t != null) {
            logger.info(txt2, t);
        } else {
            logger.info(txt2);
        }
    }

    public void alertaError(final String txt,
                            final String txt2,
                            final Throwable t)
    {
        if (txt.length() > 0) {
            Messagebox.show(txt, "JX_Market", 1, Messagebox.EXCLAMATION);
        }
        if (t != null) {
            logger.error(txt2, t);
        } else {
            logger.error(txt2);
        }

    }
}
