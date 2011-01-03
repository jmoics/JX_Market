package pe.com.jx_market.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkforge.bwcaptcha.Captcha;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

import pe.com.jx_market.domain.DTO_Cliente;
import pe.com.jx_market.domain.DTO_Usuario;
import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

public class PO_CERegistroCliente extends Div
{
    static Log logger = LogFactory.getLog(PO_CERegistroCliente.class);
    private Textbox txtApellidos,txtNombres,txtMail,txtPass,txtRepPass,
                    txtTelef,txtCelu,txtCiudad,txtDireccion,txtCaptcha;
    private Captcha cpa;
    private Intbox intNumDoc;
    private Datebox datFecNacim;
    private BusinessService validationService, usuarioService, clienteService;

    public void onCreate() {
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

        validationService = Utility.getService(this, "validationService");
        usuarioService = Utility.getService(this, "usuarioService");
        clienteService = Utility.getService(this, "clienteService");

        try {
            datFecNacim.setValue(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1980"));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void registrarCliente() {
        if(txtCaptcha.getValue().toLowerCase().equals(cpa.getValue().trim().toLowerCase())){
            final DTO_Cliente cliente = new DTO_Cliente();
            final DTO_Usuario usuario = new DTO_Usuario();
            final List<String> lst = guardaDTO(cliente, usuario);
            if(lst != null) {
                int i = 0;
                String msg = "";
                while(i < lst.size()){
                    msg = msg + lst.get(i) + "\n";
                    i++;
                }
                //alertaInfo(msg, "", null);
                alertaInfo(msg, msg, null);
            } else {
                final Map<String, Object> map = new HashMap<String, Object>();
                map.put("cliente", cliente);
                map.put("usuario", usuario);
                final DTO_Input input = new DTO_Input();
                input.setVerbo(Constantes.V_REGISTER);
                input.setMapa(map);
                final DTO_Output output = clienteService.execute(input);
                if (output.getErrorCode() == Constantes.OK) {
                    try {
                        final int rpta = Messagebox.show("Su registro fue realizado correctamente",
                                                    "JX_Market", Messagebox.OK, Messagebox.INFORMATION);
                        if (rpta == Messagebox.OK) {
                            Executions.sendRedirect("index.zul");
                        }
                    } catch (final InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    alertaError("Hubo un error en el registro", "Error en el registro" + output.getErrorCode(), null);
                }
            }
        } else {
            txtCaptcha.setValue("");
            alertaInfo("El texto introducido no corresponde con el de la imagen", "codigo de captcha incorrecto", null);
        }
    }

    public List<String> guardaDTO(final DTO_Cliente cliente, final DTO_Usuario usuario){
        cliente.setEmpresa(Constantes.INSTITUCION_JX_MARKET);

        //modificar cuando se agregue activacion desde email
        cliente.setEstado(Constantes.ST_ACTIVO);

        cliente.setCiudad(txtCiudad.getValue());
        cliente.setRegion(txtCiudad.getValue());
        cliente.setDni(intNumDoc.getValue());

        cliente.setApellido(txtApellidos.getValue());
        cliente.setNombre(txtNombres.getValue());

        cliente.setFecNac(datFecNacim.getValue());

        cliente.setDireccion(txtDireccion.getValue());

        cliente.setTelefono(txtTelef.getValue());

        cliente.setEmail(txtMail.getValue());

        if(!verificaDisponibilidad(usuario)){
            final List <String>ans = new ArrayList<String>();
            ans.add("Ya existe un usuario con el e-mail ingresado");
            return ans;
        }

        List<String> lst = validacionDTO(cliente);
        if(lst == null){
            lst = new ArrayList<String>();
        }

        if (datFecNacim != null) {
            final int edad = edadEx(datFecNacim.getValue(), new Date());

            if(edad < 18){
                lst.add("Usted debe ser mayor de edad (18 años) para poder registrarse");
            }else{
                cliente.setFecNac(datFecNacim.getValue());
            }
        }

        if(validaCaracteres(txtTelef.getValue(), Constantes.VALID_TELEFONO)){
            cliente.setTelefono(txtTelef.getValue());
        }else{
            if(!txtTelef.getValue().isEmpty()){
                lst.add("El número de teléfono de domicilio solo debe contener números");
            }
        }

        if(validaCaracteres(txtCelu.getValue(), Constantes.VALID_TELEFONO)){
            cliente.setCelular(txtCelu.getValue());
        }else{
            if(!txtCelu.getValue().isEmpty()){
                lst.add("El número de teléfono celular solo debe contener números");
            }
        }

        usuario.setEmpresa(Constantes.INSTITUCION_JX_MARKET);
        usuario.setUsername(txtMail.getValue());

        if(!txtPass.getValue().isEmpty() || !txtRepPass.getValue().isEmpty()){
            if(txtPass.getValue().equals(txtRepPass.getValue())){
                if(txtPass.getValue().length() >= 4){
                    if(!txtPass.getValue().equals("1234")){
                        usuario.setContrasena(txtPass.getValue());
                    }else{
                        lst.add("Debe ingresar una contraseña más compleja");
                    }
                }else{
                    lst.add("Su contraseña debe tener al menos 4 caracteres");
                }
            }else{
                lst.add("Debe ingresar la misma contraseña en ambos campos");
            }
        }else{
            lst.add("Debe ingresar su contraseña (min. 4 caracteres)");
        }

        if(lst.isEmpty()){
            lst = null;
        }
        return lst;
    }

    @SuppressWarnings("unchecked")
    private List<String> validacionDTO(final DTO_Cliente cliente){
        final DTO_Input input = new DTO_Input(cliente, "registraCliente");
        final DTO_Output output = validationService.execute(input);
        if(output.getErrorCode() == Constantes.VALIDATION_ERROR){
            final List<String> errores = output.getLista();
            return errores;
            //alertaInfo(errores, "", null);
        }else if(output.getErrorCode() == Constantes.OK){
            return null;
            //alertaError("Error en la invocación del Servicio, consultar a Soporte", "Error al invocar el servicio de validacion", null);
        }else{
            throw new RuntimeException();
        }
    }

    private int edadEx(final Date fechaInicial, final Date fechaFinal){
        final int annos = fechaFinal.getYear() - fechaInicial.getYear();
        if(fechaFinal.getMonth() < fechaInicial.getMonth()){
            return annos-1;
        }else if(fechaFinal.getMonth() == fechaInicial.getMonth()){
            if(fechaFinal.getDay() <= fechaInicial.getDay()){
                return annos - 1;
            }
        }
        return annos;
    }

    private boolean validaCaracteres(final String codigo, final String tipo){
        if(codigo != null && !codigo.isEmpty()){
            if(!codigo.matches(tipo)) {
                return false;
            }else{
                return true;
            }
        }
        return false;
    }

    private boolean verificaDisponibilidad(final DTO_Usuario usuario) {
        boolean dis = true;
        final DTO_Input input = new DTO_Input(usuario);
        input.setVerbo("consultaSiEstaDisponible");
        final DTO_Output output = usuarioService.execute(input);
        if(Constantes.ALREADY_USED == output.getErrorCode()){
            dis = false;
        }else if(Constantes.OK == output.getErrorCode()){
            dis = true;
        }else{
            throw new RuntimeException();
        }
        return dis;
    }

    public void alertaInfo(final String txt,
                           final String txt2,
                           final Throwable t)
    {
        try {
            if (txt.length() > 0)
                Messagebox.show(txt, "JX_Market", 1, Messagebox.INFORMATION);
            if (t != null) {
                logger.info(txt2, t);
            } else {
                logger.info(txt2);
            }
        } catch (final InterruptedException ex) {
        }
    }

    public void alertaError(final String txt,
                            final String txt2,
                            final Throwable t)
    {
        try {
            if (txt.length() > 0)
                Messagebox.show(txt, "JX_Market", 1, Messagebox.EXCLAMATION);
            if (t != null) {
                logger.error(txt2, t);
            } else {
                logger.error(txt2);
            }
        } catch (final InterruptedException ex) {
        }

    }
}