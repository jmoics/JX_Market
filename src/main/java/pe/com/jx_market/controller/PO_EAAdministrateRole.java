package pe.com.jx_market.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import pe.com.jx_market.domain.DTO_Area;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Role;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_EAAdministrateRole
    extends SecuredWindow
{
    static Log logger = LogFactory.getLog(PO_EAAdministrateRole.class);
    private Textbox txtFuncion, txtDescripcion;
    private Combobox cmbArea;
    private Groupbox grpNuevo;
    private Grid grdRole;
    private DTO_Company company;
    private BusinessService areaService, roleService;

    @Override
    public void realOnCreate()
    {
        txtFuncion = (Textbox) getFellow("txtFuncion");
        txtDescripcion = (Textbox) getFellow("txtDescripcion");
        cmbArea = (Combobox) getFellow("cmbArea");
        grpNuevo = (Groupbox) getFellow("grpNuevo");
        grdRole = (Grid) getFellow("grdRole");
        areaService = ContextLoader.getService(this, "areaService");
        roleService = ContextLoader.getService(this, "roleService");

        company = (DTO_Company) getDesktop().getSession().getAttribute("company");
        cargarAreas(cmbArea);
        mostrarRoles();
    }

    public void crearNuevoRole()
    {
        // row1.setVisible(true);

        if (!txtDescripcion.getValue().isEmpty() && !txtFuncion.getValue().isEmpty() && cmbArea.getSelectedItem() != null) {
            final DTO_Role unew = new DTO_Role();

            unew.setRoleDescription(txtDescripcion.getValue());
            unew.setRoleName(txtFuncion.getValue());
            unew.setAreaId(((DTO_Area) cmbArea.getSelectedItem().getAttribute("area")).getId());
            unew.setCompanyId(company.getId());

            final ServiceInput input = new ServiceInput(unew);
            input.setAccion(Constantes.V_REGISTER);
            final ServiceOutput output = roleService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                alertaInfo("", "role creado correctamente", null);
                onLimpiar();
                mostrarRoles();
            } else {
                alertaError("Error al crear role", "error al crear role", null);
            }
        } else {
            alertaInfo("Todos los campos deben ser llenados", "No se ingresaron datos para codigo y descripcion", null);
        }
    }

    public void mostrarRoles()
    {
        final DTO_Role perf = new DTO_Role();
        perf.setCompanyId(company.getId());
        final ServiceInput input = new ServiceInput(perf);

        input.setAccion(Constantes.V_LIST);
        final ServiceOutput output = roleService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Role> ulist = output.getLista();

            for (final DTO_Role sOut : ulist) {
                agregarFila(sOut);
            }
        } else {
            alertaError("Error al cargar roles", "Error al cargar roles", null);
        }
    }

    public void agregarFila(final DTO_Role perf)
    {
        final Row fila = new Row();
        fila.setAttribute("role", perf);

        final Combobox combo = new Combobox();
        combo.setWidth("110px");
        cargarAreas(combo);
        for (final Comboitem item : combo.getItems()) {
            if (perf.getAreaId().equals(((DTO_Area) item.getAttribute("area")).getId())) {
                combo.setSelectedItem(item);
            }
        }
        combo.setDisabled(true);
        fila.appendChild(combo);

        Textbox txt = new Textbox(perf.getRoleName());
        txt.setWidth("100px");
        txt.setReadonly(true);
        txt.addEventListener(Events.ON_CANCEL,
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(final Event e)
                                throws UiException
                            {
                                ((Image) ((Div) (((Row) e.getTarget().getParent())
                                                .getChildren().get(3))).getChildren().get(0))
                                                .setVisible(true);
                                ((Image) ((Div) (((Row) e.getTarget().getParent())
                                                .getChildren().get(3))).getChildren().get(1))
                                                .setVisible(false);
                                ((Textbox) e.getTarget()).setReadonly(true);
                                grpNuevo.setOpen(true);
                                onLimpiar();
                                mostrarRoles();
                            }
                        });
        fila.appendChild(txt);

        txt = new Textbox(perf.getRoleDescription());
        txt.setWidth("180px");
        txt.setReadonly(true);
        txt.addEventListener(Events.ON_CANCEL,
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(final Event e)
                                throws UiException
                            {
                                ((Image) ((Div) (((Row) e.getTarget().getParent())
                                                .getChildren().get(3))).getChildren().get(0))
                                                .setVisible(true);
                                ((Image) ((Div) (((Row) e.getTarget().getParent())
                                                .getChildren().get(3))).getChildren().get(1))
                                                .setVisible(false);
                                ((Textbox) e.getTarget()).setReadonly(true);
                                grpNuevo.setOpen(true);
                                onLimpiar();
                                mostrarRoles();
                            }
                        });
        fila.appendChild(txt);

        final Image imgEditar = new Image("media/edit.png");
        imgEditar.setStyle("cursor: pointer");
        imgEditar.addEventListener(Events.ON_CLICK,
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(final Event e)
                                throws UiException
                            {
                                ((Image) e.getTarget()).setVisible(false);
                                ((Image) ((Div) e.getTarget().getParent())
                                                .getChildren().get(1)).setVisible(true);
                                for (int i = 0; i < grdRole.getRows().getChildren().size(); i++) {
                                    ((Image) ((Div) (((Row) (((Rows) e.getTarget()
                                                    .getParent().getParent().getParent())
                                                    .getChildren().get(i))).getChildren()
                                                    .get(3))).getChildren().get(0))
                                                    .setVisible(false);
                                    ((Image) ((Div) (((Row) (((Rows) e.getTarget()
                                                    .getParent().getParent().getParent())
                                                    .getChildren().get(i))).getChildren()
                                                    .get(3))).getChildren().get(2))
                                                    .setVisible(true);
                                    ((Image) ((Div) (((Row) (((Rows) e.getTarget()
                                                    .getParent().getParent().getParent())
                                                    .getChildren().get(i))).getChildren()
                                                    .get(4))).getChildren().get(0))
                                                    .setVisible(false);
                                    ((Image) ((Div) (((Row) (((Rows) e.getTarget()
                                                    .getParent().getParent().getParent())
                                                    .getChildren().get(i))).getChildren()
                                                    .get(4))).getChildren().get(1))
                                                    .setVisible(true);
                                }
                                ((Combobox) ((Row) e.getTarget().getParent().getParent())
                                                .getChildren().get(0)).setDisabled(false);
                                ((Textbox) ((Row) e.getTarget().getParent().getParent())
                                                .getChildren().get(1)).setReadonly(false);
                                ((Textbox) ((Row) e.getTarget().getParent().getParent())
                                                .getChildren().get(1)).setFocus(true);
                                ((Textbox) ((Row) e.getTarget().getParent().getParent())
                                                .getChildren().get(2)).setReadonly(false);
                                ((Image) (((Div) ((Row) e.getTarget().getParent()
                                                .getParent()).getChildren().get(3)))
                                                .getChildren().get(0)).setVisible(false);
                                ((Image) (((Div) ((Row) e.getTarget().getParent()
                                                .getParent()).getChildren().get(3)))
                                                .getChildren().get(1)).setVisible(true);
                                grpNuevo.setOpen(false);
                            }
                        });

        final Image imgGuardar = new Image("media/filesave.png");
        imgGuardar.setStyle("cursor:pointer");
        imgGuardar.addEventListener(Events.ON_CLICK,
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(final Event e)
                                throws UiException
                            {
                                ((Image) e.getTarget()).setVisible(false);
                                ((Image) ((Div) e.getTarget().getParent())
                                                .getChildren().get(0)).setVisible(true);
                                ((Combobox) ((Row) e.getTarget().getParent().getParent())
                                                .getChildren().get(0)).setDisabled(true);
                                ((Textbox) ((Row) e.getTarget().getParent().getParent())
                                                .getChildren().get(1)).setReadonly(true);
                                ((Textbox) ((Row) e.getTarget().getParent().getParent())
                                                .getChildren().get(2)).setReadonly(true);
                                ((Image) (((Div) ((Row) e.getTarget().getParent()
                                                .getParent()).getChildren().get(3)))
                                                .getChildren().get(0)).setVisible(true);
                                ((Image) (((Div) ((Row) e.getTarget().getParent()
                                                .getParent()).getChildren().get(3)))
                                                .getChildren().get(1)).setVisible(false);
                                grpNuevo.setOpen(true);
                                for (int i = 0; i < grdRole.getRows().getChildren().size(); i++) {
                                    ((Image) ((Div) (((Row) (((Rows) e.getTarget()
                                                    .getParent().getParent().getParent())
                                                    .getChildren().get(i))).getChildren()
                                                    .get(3))).getChildren().get(0))
                                                    .setVisible(true);
                                    ((Image) ((Div) (((Row) (((Rows) e.getTarget()
                                                    .getParent().getParent().getParent())
                                                    .getChildren().get(i))).getChildren()
                                                    .get(3))).getChildren().get(2))
                                                    .setVisible(false);
                                    ((Image) ((Div) (((Row) (((Rows) e.getTarget()
                                                    .getParent().getParent().getParent())
                                                    .getChildren().get(i))).getChildren()
                                                    .get(4))).getChildren().get(0))
                                                    .setVisible(true);
                                    ((Image) ((Div) (((Row) (((Rows) e.getTarget()
                                                    .getParent().getParent().getParent())
                                                    .getChildren().get(i))).getChildren()
                                                    .get(4))).getChildren().get(1))
                                                    .setVisible(false);
                                }
                                ((Row) e.getTarget().getParent().getParent()).getAttribute("role");
                                perf.setRoleName(((Textbox)
                                                ((Row) e.getTarget().getParent().getParent()).getChildren().get(1)).getValue());
                                perf.setRoleDescription(((Textbox)
                                                ((Row) e.getTarget().getParent().getParent()).getChildren().get(2)).getValue());
                                perf.setAreaId(((DTO_Area) ((Combobox)
                                                ((Row) e.getTarget().getParent().getParent()).getChildren().get(0))
                                                                .getSelectedItem().getAttribute("area")).getId());
                                actualizaRole(perf);
                            }
                        });

        final Image imgEditarDisab = new Image("media/editdelete.png");
        imgEditarDisab.setStyle("cursor:pointer");
        imgEditarDisab.addEventListener(Events.ON_CLICK,
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(final Event e)
                                throws UiException
                            {
                                ((Image) ((Div) e.getTarget().getParent()).getChildren().get(0)).setVisible(true);
                                ((Image) ((Div) e.getTarget().getParent()).getChildren().get(0)).setVisible(false);
                                grpNuevo.setOpen(true);
                                onLimpiar();
                                mostrarRoles();
                            }
                        });
        imgEditarDisab.setVisible(false);

        imgGuardar.setVisible(false);
        final Div divEditar = new Div();
        divEditar.setAlign("center");
        divEditar.appendChild(imgEditar);
        divEditar.appendChild(imgGuardar);
        divEditar.appendChild(imgEditarDisab);
        fila.appendChild(divEditar);

        final Image imgEliminar = new Image("media/cancel.png");
        imgEliminar.setStyle("cursor:pointer");
        imgEliminar.addEventListener(Events.ON_CLICK,
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(final Event e)
                                throws UiException
                            {
                                final int msg = Messagebox.show("¿Está seguro de eliminar el role?", company.getBusinessName(),
                                                Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION);
                                if (msg == Messagebox.YES) {
                                    eliminaFila((DTO_Role) ((Row) e.getTarget().getParent().getParent()).getAttribute("role"));
                                }
                            }
                        });
        final Image imgEliminarDisab = new Image("media/fileclose.png");
        imgEliminarDisab.setVisible(false);

        final Div divEliminar = new Div();
        divEliminar.setAlign("center");
        divEliminar.appendChild(imgEliminar);
        divEliminar.appendChild(imgEliminarDisab);
        fila.appendChild(divEliminar);
        grdRole.getRows().appendChild(fila);
    }

    public void actualizaRole(final DTO_Role perf)
    {
        final ServiceInput input = new ServiceInput(perf);
        input.setAccion(Constantes.V_REGISTER);

        final ServiceOutput output = roleService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("", "El role se actualizo correctamente", null);
        } else {
            alertaError("Error al actualizar el role", "Error al actualizar el role", null);
        }

        // onLimpiar();
        // mostrarTBloqueos();
    }

    public void eliminaFila(final DTO_Role perf)
        throws UiException
    {
        final ServiceInput input = new ServiceInput(perf.getId());
        input.setAccion("delete");
        final ServiceOutput output = roleService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("", "El role se elimino correctamente", null);
            onLimpiar();
            mostrarRoles();
        } else {
            alertaError("Error al eliminar el role", "Error al eliminar el role", null);
        }
    }

    private void cargarAreas(final Combobox combo)
    {
        final DTO_Area ar = new DTO_Area();
        ar.setCompanyId(company.getId());
        final ServiceInput input = new ServiceInput(ar);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput output = areaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Area> listado = output.getLista();
            for (final DTO_Area area : listado) {
                final Comboitem item = new Comboitem(area.getAreaName());
                item.setAttribute("area", area);
                combo.appendChild(item);
            }
        } else {
            alertaError("Error en la carga de areas",
                            "error al cargar los areas", null);
        }
    }

    private DTO_Area getArea(final Integer codigo)
    {
        final DTO_Area area = new DTO_Area();
        area.setId(codigo);
        area.setCompanyId(company.getId());
        final ServiceInput input = new ServiceInput(area);
        input.setAccion(Constantes.V_GET);
        final ServiceOutput output = areaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            return (DTO_Area) output.getObject();
        } else {
            return null;
        }
    }

    public void onLimpiar()
    {
        grdRole.getRows().getChildren().clear();
        txtDescripcion.setValue("");
        txtFuncion.setValue("");
        cmbArea.setSelectedItem(null);
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
        return new String[] { Constantes.MODULE_ADM_ROLE };
    }
}
