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

import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Employee;
import pe.com.jx_market.domain.DTO_Role;
import pe.com.jx_market.domain.DTO_User;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_EAAdministrateEmployee
    extends SecuredWindow
{

    static Log logger = LogFactory.getLog(PO_EAAdministrateEmployee.class);
    private Grid grdEmp;
    private Textbox txtUser, txtPass, txtNombre, txtApellidos, txtDNI, txtTelefono, txtCelular, txtMail,
                    txtDireccion, txtCiudad, txtRegion;
    private Label lblUser, lblNombre, lblApellidos, lblDNI, lblRole, lblTelefono, lblMail, lblCiudad, lblEstado;
    private Datebox datNac;
    private Button btnInfo, btnCancel, btnCrear;
    private Caption capNombre, capInfo;
    private Groupbox grpEmployees;
    private Popup popDetails;
    private Combobox cmbRole, cmbEstado;
    private DTO_Company company;
    private BusinessService employeeService, roleService, userService;

    @Override
    public void realOnCreate()
    {
        grdEmp = (Grid) getFellow("grdEmp");
        txtUser = (Textbox) getFellow("txtUser");
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
        lblUser = (Label) getFellow("lblUser");
        lblNombre = (Label) getFellow("lblNombre");
        lblApellidos = (Label) getFellow("lblApellidos");
        lblRole = (Label) getFellow("lblRole");
        lblDNI = (Label) getFellow("lblDNI");
        lblTelefono = (Label) getFellow("lblTelefono");
        lblMail = (Label) getFellow("lblMail");
        lblCiudad = (Label) getFellow("lblCiudad");
        lblEstado = (Label) getFellow("lblEstado");
        cmbRole = (Combobox) getFellow("cmbRole");
        cmbEstado = (Combobox) getFellow("cmbEstado");
        btnInfo = (Button) getFellow("btnInfo");
        btnCancel = (Button) getFellow("btnCancel");
        btnCrear = (Button) getFellow("btnCrear");
        grpEmployees = (Groupbox) getFellow("grpEmployees");
        capNombre = (Caption) getFellow("capNombre");
        capInfo = (Caption) getFellow("capInfo");
        popDetails = (Popup) getFellow("popDetails");
        employeeService = ContextLoader.getService(this, "employeeService");
        roleService = ContextLoader.getService(this, "roleService");
        userService = ContextLoader.getService(this, "userService");

        company = (DTO_Company) getDesktop().getSession().getAttribute("company");
        cargarRoles();
        CargarTabla();
    }

    public void elimina(final DTO_Employee employee)
    {

        final ServiceInput input = new ServiceInput(employee);
        input.setAccion("delete");
        final ServiceOutput output = employeeService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            logger.info("El contacto se elimino correctamente");
        } else {
            logger.error("Error al eliminar contacto");

        }
        CargarTabla();

    }

    public void editar(final DTO_Employee emp,
                       final String username,
                       final String pass,
                       final String nombre,
                       final String apellido,
                       final String dni,
                       final String telefono,
                       final String mail,
                       final Boolean estado,
                       final Integer role,
                       final String celular,
                       final String ciudad,
                       final String direccion,
                       final String region)
    {
        final DTO_Employee employee = new DTO_Employee(); // nuevo user
        employee.setEmployeeName(nombre);
        employee.setEmployeeLastName(apellido);
        employee.setDocumentNumber(dni);
        employee.setPhone(telefono);
        employee.setEmail(mail);
        employee.setActive(estado);
        employee.setRole(role);
        employee.setCompanyId(company.getId());
        employee.setCellPhone(celular);
        employee.setCity(ciudad);
        employee.setAddress(direccion);
        employee.setUbigeo(region);

        final ServiceInput input = new ServiceInput();
        final Map<String, Object> map = new HashMap<String,Object>();
        map.put("employee", employee);
        if(emp == null) {
            final DTO_User user = new DTO_User();
            user.setUsername(username);
            user.setPassword(pass);
            user.setCompanyId(company.getId());

            map.put("user", user);
        } else if (emp != null && pass != null && pass.length() != 0) {
            //para cambiar pass
            employee.setId(emp.getId());
            final DTO_User user = new DTO_User();
            user.setId(emp.getUserId());
            user.setUsername(username);
            user.setPassword(pass);
            user.setCompanyId(company.getId());

            map.put("user", user);
        } else {
            employee.setId(emp.getId());
        }
        input.setAccion(Constantes.V_REGISTER);
        input.setMapa(map);
        final ServiceOutput output = employeeService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("El employee se registro correctamente", "El contacto se registro correctamente", null);
        } else {
            alertaError("Error al registrar el contacto", "Error al registrar el contacto", null);
        }
    }

    public void cancelar()
    {
        txtUser.setReadonly(false);
        CargarTabla();
    }

    public void actualizar()
    {
        if (!txtUser.getValue().equals("") && !txtNombre.getValue().equals("") &&
                        !txtApellidos.getValue().equals("") && !txtDNI.getValue().equals("") &&
                        cmbRole.getSelectedItem() != null && cmbEstado.getSelectedItem() != null) {

            editar((DTO_Employee) txtUser.getAttribute("user"), txtUser.getValue(),
                            txtPass.getValue(), txtNombre.getValue(), txtApellidos.getValue(),
                            txtDNI.getValue(), txtTelefono.getValue(), txtMail.getValue(),
                            (Boolean)cmbEstado.getSelectedItem().getValue(),
                            ((DTO_Role) cmbRole.getSelectedItem().getAttribute("role")).getId(),
                            txtCelular.getValue(), txtCiudad.getValue(), txtDireccion.getValue(), txtRegion.getValue());
            CargarTabla();
            limpiarCrear();
            btnCrear.setVisible(true);
            btnInfo.setVisible(false);
            btnCancel.setVisible(false);
            capInfo.setLabel("Nuevo Contacto");
            txtUser.setReadonly(false);
        } else { // validar para cada campo obligatorio
            alertaInfo("Faltan llenar algunos campos", "No se llenaron los campos obligatorios", null);
        }
    }

    public void crear()
    {
        if (!txtPass.getValue().equals("") && !txtUser.getValue().equals("") && !txtNombre.getValue().equals("") &&
                        !txtApellidos.getValue().equals("") && !txtDNI.getValue().equals("") &&
                        cmbRole.getSelectedItem() != null && cmbEstado.getSelectedItem() != null) {
            editar(null, txtUser.getValue(), txtPass.getValue(), txtNombre.getValue(), txtApellidos.getValue(),
                            txtDNI.getValue(), txtTelefono.getValue(), txtMail.getValue(),
                            (Boolean)cmbEstado.getSelectedItem().getValue(),
                            ((DTO_Role) cmbRole.getSelectedItem().getAttribute("role")).getId(),
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
        txtUser.setValue("");
        txtPass.setValue("");
        cmbEstado.setSelectedItem(null);
        cmbRole.setSelectedItem(null);
    }

    @SuppressWarnings("unchecked")
    public void cargarInformacionContacto(final DTO_Employee employee)
    {
        capInfo.setLabel("Información del Contacto");
        btnCrear.setVisible(false);
        txtUser.setAttribute("user", employee);
        txtUser.setReadonly(true);
        txtUser.setValue((getUser(employee.getUserId())).getUsername());
        txtNombre.setValue(employee.getEmployeeName());
        txtApellidos.setValue(employee.getEmployeeLastName());
        txtDNI.setValue(employee.getDocumentNumber());
        txtDireccion.setValue(employee.getAddress() != null ? employee.getAddress() : "");
        txtCelular.setValue(employee.getPhone() != null ? employee.getPhone() : "");
        txtCiudad.setValue(employee.getCity() != null ? employee.getCity() : "");
        txtRegion.setValue(employee.getUbigeo() != null ? employee.getUbigeo() : "");
        txtTelefono.setValue(employee.getCellPhone() != null ? employee.getCellPhone() : "");
        txtMail.setValue(employee.getEmail());
        txtPass.setValue("");

        final List<Comboitem> roles = cmbRole.getItems();
        for(final Comboitem item : roles) {
            final DTO_Role role = (DTO_Role) item.getAttribute("role");
            if(role.getId().equals(employee.getRole())) {
                cmbRole.setSelectedItem(item);
            }
        }
        final List<Comboitem> estados = cmbEstado.getItems();
        for(final Comboitem item : estados) {
            final Integer stat = Integer.parseInt((String) item.getValue());
            if(stat.equals(employee.getActive())) {
                cmbEstado.setSelectedItem(item);
            }
        }
    }

    public void cargarPop(final DTO_Employee employee)
    {
        lblUser.setValue((getUser(employee.getUserId())).getUsername());
        lblNombre.setValue(employee.getEmployeeName());
        lblApellidos.setValue(employee.getEmployeeLastName());
        lblRole.setValue((getRole(employee.getRole()).getRoleName()));
        lblCiudad.setValue(employee.getCity());
        lblDNI.setValue(employee.getDocumentNumber());
        final String estado = Constantes.ST_ACTIVO.equals(employee.getActive()) ? Constantes.STATUS_ACTIVO
                                                                                     : Constantes.STATUS_INACTIVO;
        lblEstado.setValue(estado);
        lblMail.setValue(employee.getEmail());
        lblTelefono.setValue(employee.getPhone());
    }

    public void CargarTabla()
    {
        grdEmp.getRows().getChildren().clear();
        final DTO_Employee emp = new DTO_Employee();
        emp.setCompanyId(company.getId());
        final ServiceInput input = new ServiceInput(emp);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput output = employeeService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Employee> ulist = output.getLista();
            for (final DTO_Employee uOut : ulist) {
                final Row fila = new Row();
                fila.setAttribute("employee", uOut);
                // Fecha Creacion
                /*
                 fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
                 .format(uOut.getFecha_creacion())));
                 */
                // Username
                // fila.appendChild(new Label(uOut.getUser()));
                fila.appendChild(new Label(uOut.getEmployeeName()));
                fila.appendChild(new Label(uOut.getEmployeeLastName()));
                fila.appendChild(new Label(getUser(uOut.getUserId()).getUsername()));
                fila.appendChild(new Label((getRole(uOut.getRole())).getRoleName()));

                final Image ImDetalles = new Image("media/details.png");
                ImDetalles.setStyle("cursor:pointer");
                ImDetalles.setPopup(popDetails);
                ImDetalles.addEventListener("onClick",
                                new org.zkoss.zk.ui.event.EventListener() {
                                    @Override
                                    public void onEvent(final Event e)
                                        throws UiException
                                    {
                                         cargarPop((DTO_Employee) ((Row)
                                         e.getTarget().getParent()).getAttribute("employee"));

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

                                        cargarInformacionContacto((DTO_Employee) ((Row) e
                                        .getTarget().getParent().getParent())
                                        .getAttribute("employee"));
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
                                        final int msg = Messagebox.show("¿Está seguro de eliminar el Employee?",
                                                                    company.getBusinessName(),
                                                                    Messagebox.YES | Messagebox.NO,
                                                                    Messagebox.QUESTION);
                                        if (msg == Messagebox.YES) {
                                            elimina((DTO_Employee) ((Row) e
                                                            .getTarget().getParent()
                                                            .getParent())
                                                            .getAttribute("employee"));
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

    private DTO_User getUser(final Integer codigo)
    {
        final DTO_User user = new DTO_User();
        user.setId(codigo);
        user.setCompanyId(company.getId());
        final ServiceInput input = new ServiceInput(user);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput output = userService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            return (DTO_User) output.getLista().get(0);
        } else {
            return null;
        }
    }

    private DTO_Role getRole(final Integer codigo)
    {
        final DTO_Role role = new DTO_Role();
        role.setId(codigo);
        role.setCompanyId(company.getId());
        final ServiceInput input = new ServiceInput(role);
        input.setAccion(Constantes.V_GET);
        final ServiceOutput output = roleService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            return (DTO_Role) output.getObject();
        } else {
            return null;
        }
    }

    public void cargarRoles()
    {
        final DTO_Role per = new DTO_Role();
        per.setCompanyId(company.getId());
        final ServiceInput input = new ServiceInput(per);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput output = roleService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Role> listado = output.getLista();
            for (final DTO_Role role : listado) {
                final Comboitem item = new Comboitem(role.getRoleName());
                item.setAttribute("role", role);
                cmbRole.appendChild(item);
            }
        } else {
            alertaError("Error en la carga de roles",
                            "error al cargar los roles", null);
        }
    }

    public void alertaInfo(final String txt,
                           final String txt2,
                           final Throwable t)
    {
        if (txt.length() > 0) {
            Messagebox.show(txt, company.getBusinessName(), 1, Messagebox.INFORMATION);
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
            Messagebox.show(txt, company.getBusinessName(), 1, Messagebox.EXCLAMATION);
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
        return new String[]{Constantes.MODULE_ADM_EMPLOYEE };
    }
}
