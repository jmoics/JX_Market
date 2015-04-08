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
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.domain.DTO_Perfil;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_EAAdministraPerfil
    extends SecuredWindow
{
    static Log logger = LogFactory.getLog(PO_EAAdministraPerfil.class);
    private Textbox txtFuncion, txtDescripcion;
    private Combobox cmbArea;
    private Groupbox grpNuevo;
    private Grid grdPerfil;
    private DTO_Empresa empresa;
    private BusinessService areaService, perfilService;

    @Override
    public void realOnCreate()
    {
        txtFuncion = (Textbox) getFellow("txtFuncion");
        txtDescripcion = (Textbox) getFellow("txtDescripcion");
        cmbArea = (Combobox) getFellow("cmbArea");
        grpNuevo = (Groupbox) getFellow("grpNuevo");
        grdPerfil = (Grid) getFellow("grdPerfil");
        areaService = ContextLoader.getService(this, "areaService");
        perfilService = ContextLoader.getService(this, "perfilService");

        empresa = (DTO_Empresa) getDesktop().getSession().getAttribute("empresa");
        cargarAreas(cmbArea);
        mostrarPerfiles();
    }

    public void crearNuevoPerfil()
    {
        // row1.setVisible(true);

        if (!txtDescripcion.getValue().isEmpty() && !txtFuncion.getValue().isEmpty() && cmbArea.getSelectedItem() != null) {
            final DTO_Perfil unew = new DTO_Perfil();

            unew.setDescripcion(txtDescripcion.getValue());
            unew.setFuncion(txtFuncion.getValue());
            unew.setArea(((DTO_Area) cmbArea.getSelectedItem().getAttribute("area")).getCodigo());
            unew.setEmpresa(empresa.getCodigo());

            final ServiceInput input = new ServiceInput(unew);
            input.setAccion(Constantes.V_REGISTER);
            final ServiceOutput output = perfilService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                alertaInfo("", "perfil creado correctamente", null);
                onLimpiar();
                mostrarPerfiles();
            } else {
                alertaError("Error al crear perfil", "error al crear perfil", null);
            }
        } else {
            alertaInfo("Todos los campos deben ser llenados", "No se ingresaron datos para codigo y descripcion", null);
        }
    }

    public void mostrarPerfiles()
    {
        final DTO_Perfil perf = new DTO_Perfil();
        perf.setEmpresa(empresa.getCodigo());
        final ServiceInput input = new ServiceInput(perf);

        input.setAccion(Constantes.V_LIST);
        final ServiceOutput output = perfilService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Perfil> ulist = output.getLista();

            for (final DTO_Perfil sOut : ulist) {
                agregarFila(sOut);
            }
        } else {
            alertaError("Error al cargar perfiles", "Error al cargar perfiles", null);
        }
    }

    public void agregarFila(final DTO_Perfil perf)
    {
        final Row fila = new Row();
        fila.setAttribute("perfil", perf);

        final Combobox combo = new Combobox();
        combo.setWidth("110px");
        cargarAreas(combo);
        for (final Comboitem item : combo.getItems()) {
            if (perf.getArea().equals(((DTO_Area) item.getAttribute("area")).getCodigo())) {
                combo.setSelectedItem(item);
            }
        }
        combo.setDisabled(true);
        fila.appendChild(combo);

        Textbox txt = new Textbox(perf.getFuncion());
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
                                mostrarPerfiles();
                            }
                        });
        fila.appendChild(txt);

        txt = new Textbox(perf.getDescripcion());
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
                                mostrarPerfiles();
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
                                for (int i = 0; i < grdPerfil.getRows().getChildren().size(); i++) {
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
                                for (int i = 0; i < grdPerfil.getRows().getChildren().size(); i++) {
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
                                ((Row) e.getTarget().getParent().getParent()).getAttribute("perfil");
                                perf.setFuncion(((Textbox)
                                                ((Row) e.getTarget().getParent().getParent()).getChildren().get(1)).getValue());
                                perf.setDescripcion(((Textbox)
                                                ((Row) e.getTarget().getParent().getParent()).getChildren().get(2)).getValue());
                                perf.setArea(((DTO_Area) ((Combobox)
                                                ((Row) e.getTarget().getParent().getParent()).getChildren().get(0))
                                                                .getSelectedItem().getAttribute("area")).getCodigo());
                                actualizaPerfil(perf);
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
                                mostrarPerfiles();
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
                                final int msg = Messagebox.show("¿Está seguro de eliminar el perfil?", empresa.getRazonsocial(),
                                                Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION);
                                if (msg == Messagebox.YES) {
                                    eliminaFila((DTO_Perfil) ((Row) e.getTarget().getParent().getParent()).getAttribute("perfil"));
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
        grdPerfil.getRows().appendChild(fila);
    }

    public void actualizaPerfil(final DTO_Perfil perf)
    {
        final ServiceInput input = new ServiceInput(perf);
        input.setAccion(Constantes.V_REGISTER);

        final ServiceOutput output = perfilService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("", "El perfil se actualizo correctamente", null);
        } else {
            alertaError("Error al actualizar el perfil", "Error al actualizar el perfil", null);
        }

        // onLimpiar();
        // mostrarTBloqueos();
    }

    public void eliminaFila(final DTO_Perfil perf)
        throws UiException
    {
        final ServiceInput input = new ServiceInput(perf.getCodigo());
        input.setAccion("delete");
        final ServiceOutput output = perfilService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("", "El perfil se elimino correctamente", null);
            onLimpiar();
            mostrarPerfiles();
        } else {
            alertaError("Error al eliminar el perfil", "Error al eliminar el perfil", null);
        }
    }

    private void cargarAreas(final Combobox combo)
    {
        final DTO_Area ar = new DTO_Area();
        ar.setEmpresa(empresa.getCodigo());
        final ServiceInput input = new ServiceInput(ar);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput output = areaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Area> listado = output.getLista();
            for (final DTO_Area area : listado) {
                final Comboitem item = new Comboitem(area.getNombre());
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
        area.setCodigo(codigo);
        area.setEmpresa(empresa.getCodigo());
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
        grdPerfil.getRows().getChildren().clear();
        txtDescripcion.setValue("");
        txtFuncion.setValue("");
        cmbArea.setSelectedItem(null);
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
        return new String[] { Constantes.MODULO_ADM_PERFIL };
    }
}
