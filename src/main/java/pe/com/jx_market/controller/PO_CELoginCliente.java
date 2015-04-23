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

import pe.com.jx_market.domain.DTO_Cliente;
import pe.com.jx_market.domain.DTO_Product;
import pe.com.jx_market.domain.DTO_User;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_CELoginCliente extends Div
{
    static Log logger = LogFactory.getLog(PO_CELoginCliente.class);
    private Textbox txtUser, txtPass;
    private BusinessService validationService, userService, clienteService;

    public void onCreate() {
        txtUser = (Textbox) getFellow("txtUser");
        txtPass = (Textbox) getFellow("txtPass");
        validationService = ContextLoader.getService(this, "validationService");
        userService = ContextLoader.getService(this, "userService");
        clienteService = ContextLoader.getService(this, "clienteService");
    }

    public void iniciarSesion() {
        final DTO_User user = new DTO_User();
        user.setUsername(txtUser.getValue());
        user.setPassword(txtPass.getValue());
        user.setCompanyId(Constantes.INSTITUCION_JX_MARKET);

        final DTO_User validado = getUser(user);
        if (validado != null) {
            final DTO_Cliente cliente = getCliente(validado);
            if (cliente != null) {
                getDesktop().getSession().setAttribute("cliente", cliente);
                final Map<Integer, Map<DTO_Product, Integer>> map = new HashMap<Integer, Map<DTO_Product, Integer>>();
                getDesktop().getSession().setAttribute("carrito", map);
                final Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("total", BigDecimal.ZERO);
                map2.put("cantidad", 0);
                getDesktop().getSession().setAttribute("totales", map2);
                getDesktop().getSession().setAttribute("sendPage", getDesktop().getBookmark());

                Executions.sendRedirect("index.zul");
            } else {
                // Hacer algo para que indique que no se pudo loguear
            }
        } else {
            txtUser.setText("");
            txtUser.setFocus(true);
            txtPass.setText("");
        }
    }

    public DTO_Cliente getCliente(final DTO_User usu)
    {
        final DTO_Cliente cli = new DTO_Cliente();
        cli.setCompany(usu.getCompanyId());
        cli.setUser(usu.getId());
        final ServiceInput input = new ServiceInput(cli);
        input.setAccion(Constantes.V_GET);
        final ServiceOutput output = clienteService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            return (DTO_Cliente) output.getObject();
        } else {
            return null;
        }
    }

    public DTO_User getUser(final DTO_User C)
    {
        DTO_User user;
        final BusinessService authService = ContextLoader.getService(this, "authService");
        final ServiceInput input = new ServiceInput(C);

        final ServiceOutput output = authService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            user = (DTO_User) output.getObject();
        } else {
            user = null;
        }

        return user;
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
