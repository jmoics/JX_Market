package pe.com.jx_market.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import pe.com.jx_market.domain.DTO_Area;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_EAAdministrateArea
    extends SecuredWindow
{
    static Log logger = LogFactory.getLog(PO_EAAdministrateArea.class);
    private Textbox txtNombre;
    private Groupbox grpNuevo;
    private Grid grdArea;
    private DTO_Company company;
    private BusinessService areaService;
    private Popup popEmpleados;

    @Override
    public void realOnCreate()
    {
        txtNombre = (Textbox) getFellow("txtNombre");
        grpNuevo = (Groupbox) getFellow("grpNuevo");
        grdArea = (Grid) getFellow("grdArea");
        areaService = ContextLoader.getService(this, "areaService");
        popEmpleados = (Popup) getFellow("popEmpleados");
        company = (DTO_Company) getDesktop().getSession().getAttribute("company");
        mostrarAreas();
    }

    public void crearNuevaArea()
    {
        // row1.setVisible(true);

        if (!txtNombre.getValue().isEmpty()) {
            final DTO_Area unew = new DTO_Area();

            unew.setAreaName(txtNombre.getValue());
            unew.setCompanyId(company.getId());

            final ServiceInput input = new ServiceInput(unew);
            input.setAccion(Constantes.V_REGISTER);
            final ServiceOutput output = areaService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                alertaInfo("", "area creada correctamente", null);
                onLimpiar();
                mostrarAreas();
            } else {
                alertaError("Error al crear area", "error al crear area", null);
            }
        } else {
            alertaInfo("Todos los campos deben ser llenados", "No se ingresaron datos para codigo y descripcion", null);
        }
    }

    public void mostrarAreas()
    {
        final DTO_Area are = new DTO_Area();
        are.setCompanyId(company.getId());
        final ServiceInput input = new ServiceInput(are);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput output = areaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Area> ulist = output.getLista();

            for (final DTO_Area sOut : ulist) {
                agregarFila(sOut);
            }
        } else {
            alertaError("Error al cargar areas", "Error al cargar areas", null);
        }
    }

    public void agregarFila(final DTO_Area are)
    {
        final Row fila = new Row();
        fila.setAttribute("area", are);

        final Textbox txt = new Textbox(are.getAreaName());
        txt.setWidth("180px");
        txt.setReadonly(true);
        txt.addEventListener(Events.ON_CANCEL,
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(final Event e)
                                throws UiException
                            {
                                ((Image) ((Div) (((Row) e.getTarget().getParent())
                                                .getChildren().get(2))).getChildren().get(0))
                                                .setVisible(true);
                                ((Image) ((Div) (((Row) e.getTarget().getParent())
                                                .getChildren().get(2))).getChildren().get(1))
                                                .setVisible(false);
                                ((Textbox) e.getTarget()).setReadonly(true);
                                grpNuevo.setOpen(true);
                                onLimpiar();
                                mostrarAreas();
                            }
                        });
        fila.appendChild(txt);

        final Image ImDetalles = new Image("media/details.png");
        ImDetalles.setStyle("cursor:pointer");
        ImDetalles.setPopup(popEmpleados);
        ImDetalles.addEventListener("onClick",
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(final Event e)
                                throws UiException
                            {
                                /*
                                 * cargarPop((DTO_Empleado) ((Row)
                                 * e.getTarget().
                                 * getParent()).getAttribute("empleado"));
                                 */

                            }
                        });
        fila.appendChild(ImDetalles);

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
                                for (int i = 0; i < grdArea.getRows().getChildren().size(); i++) {
                                    ((Image) ((Div) (((Row) (((Rows) e.getTarget()
                                                    .getParent().getParent().getParent())
                                                    .getChildren().get(i))).getChildren()
                                                    .get(2))).getChildren().get(0))
                                                    .setVisible(false);
                                    ((Image) ((Div) (((Row) (((Rows) e.getTarget()
                                                    .getParent().getParent().getParent())
                                                    .getChildren().get(i))).getChildren()
                                                    .get(2))).getChildren().get(2))
                                                    .setVisible(true);
                                    ((Image) ((Div) (((Row) (((Rows) e.getTarget()
                                                    .getParent().getParent().getParent())
                                                    .getChildren().get(i))).getChildren()
                                                    .get(3))).getChildren().get(0))
                                                    .setVisible(false);
                                    ((Image) ((Div) (((Row) (((Rows) e.getTarget()
                                                    .getParent().getParent().getParent())
                                                    .getChildren().get(i))).getChildren()
                                                    .get(3))).getChildren().get(1))
                                                    .setVisible(true);
                                }
                                ((Textbox) ((Row) e.getTarget().getParent().getParent())
                                                .getChildren().get(0)).setReadonly(false);
                                ((Textbox) ((Row) e.getTarget().getParent().getParent())
                                                .getChildren().get(0)).setFocus(true);
                                ((Image) (((Div) ((Row) e.getTarget().getParent()
                                                .getParent()).getChildren().get(2)))
                                                .getChildren().get(0)).setVisible(false);
                                ((Image) (((Div) ((Row) e.getTarget().getParent()
                                                .getParent()).getChildren().get(2)))
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
                                ((Textbox) ((Row) e.getTarget().getParent().getParent())
                                                .getChildren().get(0)).setReadonly(true);
                                ((Image) (((Div) ((Row) e.getTarget().getParent()
                                                .getParent()).getChildren().get(2)))
                                                .getChildren().get(0)).setVisible(true);
                                ((Image) (((Div) ((Row) e.getTarget().getParent()
                                                .getParent()).getChildren().get(2)))
                                                .getChildren().get(1)).setVisible(false);
                                grpNuevo.setOpen(true);
                                for (int i = 0; i < grdArea.getRows().getChildren().size(); i++) {
                                    ((Image) ((Div) (((Row) (((Rows) e.getTarget()
                                                    .getParent().getParent().getParent())
                                                    .getChildren().get(i))).getChildren()
                                                    .get(2))).getChildren().get(0))
                                                    .setVisible(true);
                                    ((Image) ((Div) (((Row) (((Rows) e.getTarget()
                                                    .getParent().getParent().getParent())
                                                    .getChildren().get(i))).getChildren()
                                                    .get(2))).getChildren().get(2))
                                                    .setVisible(false);
                                    ((Image) ((Div) (((Row) (((Rows) e.getTarget()
                                                    .getParent().getParent().getParent())
                                                    .getChildren().get(i))).getChildren()
                                                    .get(3))).getChildren().get(0))
                                                    .setVisible(true);
                                    ((Image) ((Div) (((Row) (((Rows) e.getTarget()
                                                    .getParent().getParent().getParent())
                                                    .getChildren().get(i))).getChildren()
                                                    .get(3))).getChildren().get(1))
                                                    .setVisible(false);
                                }
                                are.setAreaName(((Textbox)
                                                ((Row) e.getTarget().getParent().getParent()).getChildren().get(0)).getValue());

                                actualizaArea(are);
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
                                mostrarAreas();
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
                                int msg = 0;
                                msg = Messagebox.show("¿Está seguro de eliminar el area?",
                                                company.getBusinessName(), Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION);
                                if (msg == Messagebox.YES) {
                                    eliminaFila((DTO_Area) ((Row) e.getTarget().getParent().getParent())
                                                                                        .getAttribute("area"));
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
        grdArea.getRows().appendChild(fila);
    }

    public void actualizaArea(final DTO_Area are)
    {
        final ServiceInput input = new ServiceInput(are);
        input.setAccion(Constantes.V_REGISTER);

        final ServiceOutput output = areaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("", "El area se actualizo correctamente", null);
        } else {
            alertaError("Error al actualizar el area", "Error al actualizar el area", null);
        }

        // onLimpiar();
        // mostrarTBloqueos();
    }

    public void eliminaFila(final DTO_Area are)
        throws UiException
    {
        final ServiceInput input = new ServiceInput(are);
        input.setAccion(Constantes.V_DELETE);
        final ServiceOutput output = areaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("", "El area se elimino correctamente", null);
            onLimpiar();
            mostrarAreas();
        } else {
            alertaError("Error al eliminar el area", "Error al eliminar el area", null);
        }
    }

    public void onLimpiar()
    {
        grdArea.getRows().getChildren().clear();
        txtNombre.setValue("");
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
        return new String[] { Constantes.MODULE_ADM_AREA };
    }
}
