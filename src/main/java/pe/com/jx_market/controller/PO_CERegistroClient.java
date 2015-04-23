package pe.com.jx_market.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Captcha;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

import pe.com.jx_market.domain.DTO_Client;
import pe.com.jx_market.domain.DTO_Solicitud;
import pe.com.jx_market.domain.DTO_User;
import pe.com.jx_market.service.EnviarCorreo;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_CERegistroClient
    extends Div
{
    static Log logger = LogFactory.getLog(PO_CERegistroClient.class);
    private Textbox txtApellidos, txtNombres, txtMail, txtPass, txtRepPass,
                    txtTelef, txtCelu, txtCiudad, txtDireccion, txtCaptcha, txtRS, txtMailEmp, txtRuc;
    private Captcha cpa;
    private Intbox intNumDoc;
    private Datebox datFecNacim;
    private Grid grdRegCli, grdRegEmp;
    private BusinessService validationService, userService, clientService, solicitudService;

    public void onCreate()
    {
        grdRegEmp = (Grid) getFellow("grdRegEmp");
        grdRegCli = (Grid) getFellow("grdRegCli");
        intNumDoc = (Intbox) getFellow("intNumDoc");
        txtPass = (Textbox) getFellow("txtPass");
        txtRepPass = (Textbox) getFellow("txtRepPass");
        txtCiudad = (Textbox) getFellow("txtCiudad");
        txtApellidos = (Textbox) getFellow("txtApellidos");
        txtNombres = (Textbox) getFellow("txtNombres");
        txtTelef = (Textbox) getFellow("txtTelef");
        txtCelu = (Textbox) getFellow("txtCelu");
        txtMail = (Textbox) getFellow("txtMail");
        txtCaptcha = (Textbox) getFellow("txtCaptcha");
        cpa = (Captcha) getFellow("cpa");
        datFecNacim = (Datebox) getFellow("datFecNacim");
        txtDireccion = (Textbox) getFellow("txtDireccion");

        txtRS = (Textbox) getFellow("txtRS");
        txtMailEmp = (Textbox) getFellow("txtMailEmp");
        txtRuc = (Textbox) getFellow("txtRuc");

        validationService = ContextLoader.getService(this, "validationService");
        userService = ContextLoader.getService(this, "userService");
        clientService = ContextLoader.getService(this, "clientService");
        solicitudService = ContextLoader.getService(this, "solicitudService");

        try {
            datFecNacim.setValue(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1980"));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void registrarClient()
    {
        if (txtCaptcha.getValue().toLowerCase().equals(cpa.getValue().trim().toLowerCase())) {
            if (grdRegCli.isVisible()) {
                final DTO_Client client = new DTO_Client();
                final DTO_User user = new DTO_User();
                final List<String> lst = guardaDTO(client, user);
                if (lst != null) {
                    int i = 0;
                    String msg = "";
                    while (i < lst.size()) {
                        msg = msg + lst.get(i) + "\n";
                        i++;
                    }
                    // alertaInfo(msg, "", null);
                    alertaInfo(msg, msg, null);
                } else {
                    final Map<String, Object> map = new HashMap<String, Object>();
                    map.put("client", client);
                    map.put("user", user);
                    final ServiceInput input = new ServiceInput();
                    input.setAccion(Constantes.V_REGISTER);
                    input.setMapa(map);
                    final ServiceOutput output = clientService.execute(input);
                    if (output.getErrorCode() == Constantes.OK) {
                        final int rpta = Messagebox.show("Su registro fue realizado correctamente",
                                                    "JX_Market", Messagebox.OK, Messagebox.INFORMATION);
                        if (rpta == Messagebox.OK) {
                        	final EnviarCorreo correo = new EnviarCorreo();
                        	final boolean enviado = correo.enviarCorreo(client.getEmail(),"Bienvenido a JXMARKET","Su registro fue exitoso");
                        	System.out.println("enviado: "+enviado);

                            getDesktop().getSession().setAttribute("sendPage", getDesktop().getBookmark());
                            Executions.sendRedirect("index.zul");
                        }
                    } else {
                        alertaError("Hubo un error en el registro", "Error en el registro" + output.getErrorCode(), null);
                    }
                }
            } else if (grdRegEmp.isVisible()) {
                final DTO_Solicitud solicitud = new DTO_Solicitud();
                final List<String> lst = guardaSolicitud(solicitud);
                if (lst != null) {
                    int i = 0;
                    String msg = "";
                    while (i < lst.size()) {
                        msg = msg + lst.get(i) + "\n";
                        i++;
                    }
                    // alertaInfo(msg, "", null);
                    alertaInfo(msg, msg, null);
                } else {
                    final ServiceInput input = new ServiceInput();
                    input.setAccion(Constantes.V_REGISTER);
                    input.setObject(solicitud);
                    final ServiceOutput output = solicitudService.execute(input);
                    if (output.getErrorCode() == Constantes.OK) {
                        int rpta;
                        final EnviarCorreo correo = new EnviarCorreo();
                        final boolean enviado = correo.enviarCorreo(solicitud.getCorreo(),"Solicitud Recibida JXMarket","Su solicitud fue recibida, espera nuestra confirmaci�n.");
                        System.out.println("enviado: "+enviado);

                        rpta = Messagebox.show("Se registro correctamente su solicitud, "
                                                    + "revise su correo para revisa la confirmacion",
                                                                "JX_Market", Messagebox.OK, Messagebox.INFORMATION);
                        if (rpta == Messagebox.OK) {
                            getDesktop().getSession().setAttribute("sendPage", getDesktop().getBookmark());
                            Executions.sendRedirect("index.zul");
                        }
                    } else {
                        alertaError("Error al registrar la solicitud", "fallo registro de solicitud", null);
                    }
                }
            }
        } else {
            txtCaptcha.setValue("");
            alertaInfo("El texto introducido no corresponde con el de la imagen", "codigo de captcha incorrecto", null);
        }
    }

    public List<String> guardaDTO(final DTO_Client client,
                                  final DTO_User user)
    {
        client.setCompanyId(Constantes.INSTITUCION_JX_MARKET);

        // modificar cuando se agregue activacion desde email
        client.setActive(Constantes.STB_ACTIVO);

        client.setCity(txtCiudad.getValue());
        client.setUbigeo(txtCiudad.getValue());
        client.setDocumentNumber(""+intNumDoc.getValue());

        client.setClientLastName(txtApellidos.getValue());
        client.setClientName(txtNombres.getValue());

        client.setBirthday(datFecNacim.getValue());

        client.setAddress(txtDireccion.getValue());

        client.setPhone(txtTelef.getValue());

        client.setEmail(txtMail.getValue());

        if (!verificaDisponibilidad(user)) {
            final List<String> ans = new ArrayList<String>();
            ans.add("Ya existe un user con el e-mail ingresado");
            return ans;
        }

        List<String> lst = validacionDTO(client);
        if (lst == null) {
            lst = new ArrayList<String>();
        }

        if (datFecNacim != null) {
            final int edad = edadEx(datFecNacim.getValue(), new Date());

            if (edad < 18) {
                lst.add("Usted debe ser mayor de edad (18 años) para poder registrarse");
            } else {
                client.setBirthday(datFecNacim.getValue());
            }
        }

        if (validaCaracteres(txtTelef.getValue(), Constantes.VALID_TELEFONO)) {
            client.setPhone(txtTelef.getValue());
        } else {
            if (!txtTelef.getValue().isEmpty()) {
                lst.add("El número de teléfono de domicilio solo debe contener números");
            }
        }

        if (validaCaracteres(txtCelu.getValue(), Constantes.VALID_TELEFONO)) {
            client.setCellPhone(txtCelu.getValue());
        } else {
            if (!txtCelu.getValue().isEmpty()) {
                lst.add("El número de teléfono celular solo debe contener números");
            }
        }

        user.setCompanyId(Constantes.INSTITUCION_JX_MARKET);
        user.setUsername(txtMail.getValue());

        if (!txtPass.getValue().isEmpty() || !txtRepPass.getValue().isEmpty()) {
            if (txtPass.getValue().equals(txtRepPass.getValue())) {
                if (txtPass.getValue().length() >= 4) {
                    if (!txtPass.getValue().equals("1234")) {
                        user.setPassword(txtPass.getValue());
                    } else {
                        lst.add("Debe ingresar una contraseña más compleja");
                    }
                } else {
                    lst.add("Su contraseña debe tener al menos 4 caracteres");
                }
            } else {
                lst.add("Debe ingresar la misma contraseña en ambos campos");
            }
        } else {
            lst.add("Debe ingresar su contraseña (min. 4 caracteres)");
        }

        if (lst.isEmpty()) {
            lst = null;
        }
        return lst;
    }

    private List<String> guardaSolicitud(final DTO_Solicitud solicitud) {
        solicitud.setCorreo(txtMailEmp.getValue());
        solicitud.setFecha(new Date());
        solicitud.setRazon(txtRS.getValue());
        solicitud.setRuc(txtRuc.getValue());
        solicitud.setEstado(Constantes.ST_PENDIENTE);

        List<String> lst = validacionSolicitud(solicitud);

        if (validaCaracteres(txtRuc.getValue(), Constantes.VALID_TELEFONO)) {
            solicitud.setRuc(txtRuc.getValue());
        } else {
            if (!txtRuc.getValue().isEmpty()) {
                lst.add("El ruc solo debe contener números");
            }
        }

        if (lst == null) {
            lst = new ArrayList<String>();
        }
        if (lst.isEmpty()) {
            lst = null;
        }
        return lst;
    }

    @SuppressWarnings("unchecked")
    private List<String> validacionDTO(final DTO_Client client)
    {
        final ServiceInput input = new ServiceInput(client, "registraClient");
        final ServiceOutput output = validationService.execute(input);
        if (output.getErrorCode() == Constantes.VALIDATION_ERROR) {
            final List<String> errores = output.getLista();
            return errores;
            // alertaInfo(errores, "", null);
        } else if (output.getErrorCode() == Constantes.OK) {
            return null;
            // alertaError("Error en la invocación del Servicio, consultar a Soporte",
            // "Error al invocar el servicio de validacion", null);
        } else {
            throw new RuntimeException();
        }
    }

    @SuppressWarnings("unchecked")
    private List<String> validacionSolicitud(final DTO_Solicitud solicitud)
    {
        final ServiceInput input = new ServiceInput(solicitud, "registraSolicitud");
        final ServiceOutput output = validationService.execute(input);
        if (output.getErrorCode() == Constantes.VALIDATION_ERROR) {
            final List<String> errores = output.getLista();
            return errores;
            // alertaInfo(errores, "", null);
        } else if (output.getErrorCode() == Constantes.OK) {
            return null;
            // alertaError("Error en la invocación del Servicio, consultar a Soporte",
            // "Error al invocar el servicio de validacion", null);
        } else {
            throw new RuntimeException();
        }
    }

    private int edadEx(final Date fechaInicial,
                       final Date fechaFinal)
    {
        final int annos = fechaFinal.getYear() - fechaInicial.getYear();
        if (fechaFinal.getMonth() < fechaInicial.getMonth()) {
            return annos - 1;
        } else if (fechaFinal.getMonth() == fechaInicial.getMonth()) {
            if (fechaFinal.getDay() <= fechaInicial.getDay()) {
                return annos - 1;
            }
        }
        return annos;
    }

    private boolean validaCaracteres(final String codigo,
                                     final String tipo)
    {
        if (codigo != null && !codigo.isEmpty()) {
            if (!codigo.matches(tipo)) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean verificaDisponibilidad(final DTO_User user)
    {
        boolean dis = true;
        final ServiceInput input = new ServiceInput(user);
        input.setAccion("consultaSiEstaDisponible");
        final ServiceOutput output = userService.execute(input);
        if (Constantes.ALREADY_USED == output.getErrorCode()) {
            dis = false;
        } else if (Constantes.OK == output.getErrorCode()) {
            dis = true;
        } else {
            throw new RuntimeException();
        }
        return dis;
    }

    public void esCompany()
    {
        grdRegEmp.setVisible(true);
        grdRegCli.setVisible(false);
    }

    public void esClient()
    {
        grdRegEmp.setVisible(false);
        grdRegCli.setVisible(true);
    }

    public void saltarPagina(final String txt,
                             final boolean anotherPage)
    {
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
