package pe.com.jx_market.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import pe.com.jx_market.domain.DTO_Empleado;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.domain.DTO_Perfil;
import pe.com.jx_market.domain.DTO_Usuario;
import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

public class PO_EAAdministraEmpleado
    extends SecuredWindow
{

    static Log logger = LogFactory.getLog(PO_EAAdministraEmpleado.class);
    private Grid grdEmp;
    private Textbox txtUsuario, txtPass, txtNombre, txtApellidos, txtDNI, txtTelefono, txtCelular, txtMail,
                    txtDireccion, txtCiudad, txtRegion;
    private Label lblUsuario, lblNombre, lblApellidos, lblDNI, lblPerfil, lblTelefono, lblMail, lblCiudad, lblEstado;
    private Datebox datNac;
    private Button btnInfo, btnCancel, btnCrear;
    private Caption capNombre, capInfo;
    private Groupbox grpEmpleados;
    private Popup popDetails;
    private Combobox cmbPerfil, cmbEstado;
    private DTO_Empresa empresa;
    private BusinessService empleadoService, perfilService, usuarioService;

    @Override
    public void realOnCreate()
    {
        grdEmp = (Grid) getFellow("grdEmp");
        txtUsuario = (Textbox) getFellow("txtUsuario");
        txtPass = (Textbox) getFellow("txtPass");
        txtNombre = (Textbox) getFellow("txtNombre");
        txtApellidos = (Textbox) getFellow("txtApellidos");
        txtDNI = (Textbox) getFellow("txtDNI");
        txtTelefono = (Textbox) getFellow("txtTelefono");
        txtCelular = (Textbox) getFellow("txtCelular");
        txtMail = (Textbox) getFellow("txtMail");
        txtDireccion = (Textbox) getFellow("txtDireccion");
        txtCiudad = (Textbox) getFellow("txtCiudad");
        txtRegion = (Textbox) getFellow("txtRegion");
        lblUsuario = (Label) getFellow("lblUsuario");
        lblNombre = (Label) getFellow("lblNombre");
        lblApellidos = (Label) getFellow("lblApellidos");
        lblPerfil = (Label) getFellow("lblPerfil");
        lblDNI = (Label) getFellow("lblDNI");
        lblTelefono = (Label) getFellow("lblTelefono");
        lblMail = (Label) getFellow("lblMail");
        lblCiudad = (Label) getFellow("lblCiudad");
        lblEstado = (Label) getFellow("lblEstado");
        cmbPerfil = (Combobox) getFellow("cmbPerfil");
        cmbEstado = (Combobox) getFellow("cmbEstado");
        btnInfo = (Button) getFellow("btnInfo");
        btnCancel = (Button) getFellow("btnCancel");
        btnCrear = (Button) getFellow("btnCrear");
        grpEmpleados = (Groupbox) getFellow("grpEmpleados");
        capNombre = (Caption) getFellow("capNombre");
        capInfo = (Caption) getFellow("capInfo");
        popDetails = (Popup) getFellow("popDetails");
        empleadoService = Utility.getService(this, "empleadoService");
        perfilService = Utility.getService(this, "perfilService");
        usuarioService = Utility.getService(this, "usuarioService");

        empresa = (DTO_Empresa) getDesktop().getSession().getAttribute("empresa");
        cargarPerfiles();
        CargarTabla();
    }

    public void elimina(final DTO_Empleado empleado)
    {

        final DTO_Input input = new DTO_Input(empleado);
        input.setVerbo("delete");
        final DTO_Output output = empleadoService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            logger.info("El contacto se elimino correctamente");
        } else {
            logger.error("Error al eliminar contacto");

        }
        CargarTabla();

    }

    public void editar(final DTO_Empleado emp,
                       final String username,
                       final String pass,
                       final String nombre,
                       final String apellido,
                       final String dni,
                       final String telefono,
                       final String mail,
                       final Integer estado,
                       final Integer perfil,
                       final String celular,
                       final String ciudad,
                       final String direccion,
                       final String region)
    {
        final DTO_Empleado empleado = new DTO_Empleado(); // nuevo usuario
        empleado.setNombre(nombre);
        empleado.setApellido(apellido);
        empleado.setDni(dni);
        empleado.setTelefono(telefono);
        empleado.setEmail(mail);
        empleado.setEstado(estado);
        empleado.setPerfil(perfil);
        empleado.setEmpresa(empresa.getCodigo());
        empleado.setCelular(celular);
        empleado.setCiudad(ciudad);
        empleado.setDireccion(direccion);
        empleado.setRegion(region);

        final DTO_Input input = new DTO_Input();
        final Map<String, Object> map = new HashMap<String,Object>();
        map.put("empleado", empleado);
        if(emp == null) {
            final DTO_Usuario usuario = new DTO_Usuario();
            usuario.setUsername(username);
            usuario.setContrasena(pass);
            usuario.setEmpresa(empresa.getCodigo());

            map.put("usuario", usuario);
        } else if (emp != null && pass != null && pass.length() != 0) {
            //para cambiar pass
            empleado.setCodigo(emp.getCodigo());
            final DTO_Usuario usuario = new DTO_Usuario();
            usuario.setCodigo(emp.getUsuario());
            usuario.setUsername(username);
            usuario.setContrasena(pass);
            usuario.setEmpresa(empresa.getCodigo());

            map.put("usuario", usuario);
        } else {
            empleado.setCodigo(emp.getCodigo());
        }
        input.setVerbo(Constantes.V_REGISTER);
        input.setMapa(map);
        final DTO_Output output = empleadoService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("El empleado se registro correctamente", "El contacto se registro correctamente", null);
        } else {
            alertaError("Error al registrar el contacto", "Error al registrar el contacto", null);
        }
    }

    public void cancelar()
    {
        txtUsuario.setReadonly(false);
        CargarTabla();
    }

    public void actualizar()
    {
        if (!txtUsuario.getValue().equals("") && !txtNombre.getValue().equals("") &&
                        !txtApellidos.getValue().equals("") && !txtDNI.getValue().equals("") &&
                        cmbPerfil.getSelectedItem() != null && cmbEstado.getSelectedItem() != null) {

            editar((DTO_Empleado) txtUsuario.getAttribute("usuario"), txtUsuario.getValue(),
                            txtPass.getValue(), txtNombre.getValue(), txtApellidos.getValue(),
                            txtDNI.getValue(), txtTelefono.getValue(), txtMail.getValue(),
                            Integer.parseInt((String)cmbEstado.getSelectedItem().getValue()),
                            ((DTO_Perfil) cmbPerfil.getSelectedItem().getAttribute("perfil")).getCodigo(),
                            txtCelular.getValue(), txtCiudad.getValue(), txtDireccion.getValue(), txtRegion.getValue());
            CargarTabla();
            limpiarCrear();
            btnCrear.setVisible(true);
            btnInfo.setVisible(false);
            btnCancel.setVisible(false);
            capInfo.setLabel("Nuevo Contacto");
            txtUsuario.setReadonly(false);
        } else { // validar para cada campo obligatorio
            alertaInfo("Faltan llenar algunos campos", "No se llenaron los campos obligatorios", null);
        }
    }

    public void crear()
    {
        if (!txtPass.getValue().equals("") && !txtUsuario.getValue().equals("") && !txtNombre.getValue().equals("") &&
                        !txtApellidos.getValue().equals("") && !txtDNI.getValue().equals("") &&
                        cmbPerfil.getSelectedItem() != null && cmbEstado.getSelectedItem() != null) {
            editar(null, txtUsuario.getValue(), txtPass.getValue(), txtNombre.getValue(), txtApellidos.getValue(),
                            txtDNI.getValue(), txtTelefono.getValue(), txtMail.getValue(),
                            Integer.parseInt((String)cmbEstado.getSelectedItem().getValue()),
                            ((DTO_Perfil) cmbPerfil.getSelectedItem().getAttribute("perfil")).getCodigo(),
                            txtCelular.getValue(), txtCiudad.getValue(), txtDireccion.getValue(), txtRegion.getValue());
            CargarTabla();
            limpiarCrear();
        } else {
            alertaInfo("Faltan llenar algunos campos", "No se llenaron algunos campos", null);
        }
    }

    public void limpiarCrear()
    {
        txtNombre.setValue("");
        txtApellidos.setValue("");
        txtTelefono.setValue("");
        txtMail.setValue("");
        txtUsuario.setValue("");
        txtPass.setValue("");
        cmbEstado.setSelectedItem(null);
        cmbPerfil.setSelectedItem(null);
    }

    @SuppressWarnings("unchecked")
    public void cargarInformacionContacto(final DTO_Empleado empleado)
    {
        capInfo.setLabel("Información del Contacto");
        btnCrear.setVisible(false);
        txtUsuario.setAttribute("usuario", empleado);
        txtUsuario.setReadonly(true);
        txtUsuario.setValue((getUsuario(empleado.getUsuario())).getUsername());
        txtNombre.setValue(empleado.getNombre());
        txtApellidos.setValue(empleado.getApellido());
        txtDNI.setValue(empleado.getDni());
        txtDireccion.setValue(empleado.getDireccion() != null ? empleado.getDireccion() : "");
        txtCelular.setValue(empleado.getCelular() != null ? empleado.getCelular() : "");
        txtCiudad.setValue(empleado.getCiudad() != null ? empleado.getCiudad() : "");
        txtRegion.setValue(empleado.getRegion() != null ? empleado.getRegion() : "");
        txtTelefono.setValue(empleado.getTelefono() != null ? empleado.getTelefono() : "");
        txtMail.setValue(empleado.getEmail());
        txtPass.setValue("");

        final List<Comboitem> perfiles = cmbPerfil.getItems();
        for(final Comboitem item : perfiles) {
            final DTO_Perfil perfil = (DTO_Perfil) item.getAttribute("perfil");
            if(perfil.getCodigo().equals(empleado.getPerfil())) {
                cmbPerfil.setSelectedItem(item);
            }
        }
        final List<Comboitem> estados = cmbEstado.getItems();
        for(final Comboitem item : estados) {
            final Integer stat = Integer.parseInt((String) item.getValue());
            if(stat.equals(empleado.getEstado())) {
                cmbEstado.setSelectedItem(item);
            }
        }
    }

    public void cargarPop(final DTO_Empleado empleado)
    {
        lblUsuario.setValue((getUsuario(empleado.getUsuario())).getUsername());
        lblNombre.setValue(empleado.getNombre());
        lblApellidos.setValue(empleado.getApellido());
        lblPerfil.setValue((getPerfil(empleado.getPerfil()).getFuncion()));
        lblCiudad.setValue(empleado.getCiudad());
        lblDNI.setValue(empleado.getDni());
        final String estado = (String) (empleado.getEstado().equals(Constantes.ST_ACTIVO ) ? Constantes.STATUS_ACTIVO
                                                                                     : Constantes.ST_INACTIVO);
        lblEstado.setValue(estado);
        lblMail.setValue(empleado.getEmail());
        lblTelefono.setValue(empleado.getTelefono());
    }

    public void CargarTabla()
    {
        grdEmp.getRows().getChildren().clear();
        final DTO_Empleado emp = new DTO_Empleado();
        emp.setEmpresa(empresa.getCodigo());
        final DTO_Input input = new DTO_Input(emp);
        input.setVerbo(Constantes.V_LIST);
        final DTO_Output output = empleadoService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Empleado> ulist = output.getLista();
            for (final DTO_Empleado uOut : ulist) {
                final Row fila = new Row();
                fila.setAttribute("empleado", uOut);
                // Fecha Creacion
                /*
                 fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
                 .format(uOut.getFecha_creacion())));
                 */
                // Username
                // fila.appendChild(new Label(uOut.getUsuario()));
                fila.appendChild(new Label(uOut.getNombre()));
                fila.appendChild(new Label(uOut.getApellido()));
                fila.appendChild(new Label(getUsuario(uOut.getUsuario()).getUsername()));
                fila.appendChild(new Label((getPerfil(uOut.getPerfil())).getFuncion()));

                final Image ImDetalles = new Image("media/details.png");
                ImDetalles.setStyle("cursor:pointer");
                ImDetalles.setPopup(popDetails);
                ImDetalles.addEventListener("onClick",
                                new org.zkoss.zk.ui.event.EventListener() {
                                    @Override
                                    public void onEvent(final Event e)
                                        throws UiException
                                    {
                                         cargarPop((DTO_Empleado) ((Row)
                                         e.getTarget().getParent()).getAttribute("empleado"));

                                    }
                                });
                fila.appendChild(ImDetalles);
                final Image ImEditar = new Image("media/edit.png");
                ImEditar.setStyle("cursor:pointer");
                ImEditar.addEventListener("onClick",
                                new org.zkoss.zk.ui.event.EventListener() {
                                    @Override
                                    public void onEvent(final Event e)
                                        throws UiException
                                    {
                                        for (int i = 0; i < grdEmp.getRows()
                                                        .getChildren().size(); i++) {

                                            ((Image) ((Div) (((Row) (((Rows) e
                                                            .getTarget().getParent()
                                                            .getParent().getParent())
                                                            .getChildren().get(i)))
                                                            .getChildren().get(6)))
                                                            .getChildren().get(0))
                                                            .setVisible(false);
                                            ((Image) ((Div) (((Row) (((Rows) e
                                                            .getTarget().getParent()
                                                            .getParent().getParent())
                                                            .getChildren().get(i)))
                                                            .getChildren().get(6)))
                                                            .getChildren().get(1))
                                                            .setVisible(true);
                                            ((Image) ((Div) (((Row) (((Rows) e
                                                            .getTarget().getParent()
                                                            .getParent().getParent())
                                                            .getChildren().get(i)))
                                                            .getChildren().get(5)))
                                                            .getChildren().get(0))
                                                            .setVisible(false);
                                            ((Image) ((Div) (((Row) (((Rows) e
                                                            .getTarget().getParent()
                                                            .getParent().getParent())
                                                            .getChildren().get(i)))
                                                            .getChildren().get(5)))
                                                            .getChildren().get(1))
                                                            .setVisible(true);
                                        }
                                        btnCancel.setVisible(true);
                                        btnInfo.setVisible(true);

                                        cargarInformacionContacto((DTO_Empleado) ((Row) e
                                        .getTarget().getParent().getParent())
                                        .getAttribute("empleado"));
                                    }
                                });

                final Image ImEditarDisab = new Image("media/editdelete.png");
                ImEditarDisab.setVisible(false);
                final Div Diveditar = new Div();
                Diveditar.appendChild(ImEditar);
                Diveditar.appendChild(ImEditarDisab);
                fila.appendChild(Diveditar);
                final Image ImEliminar = new Image("media/cancel.png");
                ImEliminar.setStyle("cursor:pointer");
                ImEliminar.addEventListener("onClick",
                                new org.zkoss.zk.ui.event.EventListener() {
                                    @Override
                                    public void onEvent(final Event e)
                                        throws UiException
                                    {
                                        final int msg = Messagebox.show("¿Está seguro de eliminar el Empleado?",
                                                                    empresa.getRazonsocial(),
                                                                    Messagebox.YES | Messagebox.NO,
                                                                    Messagebox.QUESTION);
                                        if (msg == Messagebox.YES) {
                                            elimina((DTO_Empleado) ((Row) e
                                                            .getTarget().getParent()
                                                            .getParent())
                                                            .getAttribute("empleado"));
                                        }

                                    }
                                });
                final Image ImDisable = new Image("media/fileclose.png");
                ImDisable.setVisible(false);

                final Div Diveliminar = new Div();
                Diveliminar.appendChild(ImEliminar);
                Diveliminar.appendChild(ImDisable);
                fila.appendChild(Diveliminar);
                grdEmp.getRows().appendChild(fila);
            }
        } else {

        }
    }

    private DTO_Usuario getUsuario(final Integer codigo)
    {
        final DTO_Usuario usuario = new DTO_Usuario();
        usuario.setCodigo(codigo);
        usuario.setEmpresa(empresa.getCodigo());
        final DTO_Input input = new DTO_Input(usuario);
        input.setVerbo(Constantes.V_LIST);
        final DTO_Output output = usuarioService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            return (DTO_Usuario) output.getLista().get(0);
        } else {
            return null;
        }
    }

    private DTO_Perfil getPerfil(final Integer codigo)
    {
        final DTO_Perfil perfil = new DTO_Perfil();
        perfil.setCodigo(codigo);
        perfil.setEmpresa(empresa.getCodigo());
        final DTO_Input input = new DTO_Input(perfil);
        input.setVerbo(Constantes.V_GET);
        final DTO_Output output = perfilService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            return (DTO_Perfil) output.getObject();
        } else {
            return null;
        }
    }

    public void cargarPerfiles()
    {
        final DTO_Perfil per = new DTO_Perfil();
        per.setEmpresa(empresa.getCodigo());
        final DTO_Input input = new DTO_Input(per);
        input.setVerbo(Constantes.V_LIST);
        final DTO_Output output = perfilService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Perfil> listado = output.getLista();
            for (final DTO_Perfil perfil : listado) {
                final Comboitem item = new Comboitem(perfil.getFuncion());
                item.setAttribute("perfil", perfil);
                cmbPerfil.appendChild(item);
            }
        } else {
            alertaError("Error en la carga de perfiles",
                            "error al cargar los perfiles", null);
        }
    }

    public void alertaInfo(final String txt,
                           final String txt2,
                           final Throwable t)
    {
        if (txt.length() > 0) {
            Messagebox.show(txt, empresa.getRazonsocial(), 1, Messagebox.INFORMATION);
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
            Messagebox.show(txt, empresa.getRazonsocial(), 1, Messagebox.EXCLAMATION);
        }
        if (t != null) {
            logger.error(txt2, t);
        } else {
            logger.error(txt2);
        }
    }

    @Override
    String[] requiredResources()
    {
        return new String[]{Constantes.MODULO_ADM_EMPLEADO };
    }
}
