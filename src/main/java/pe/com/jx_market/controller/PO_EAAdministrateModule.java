package pe.com.jx_market.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Module;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_EAAdministrateModule
    extends SecuredWindow
{

    static Log logger = LogFactory.getLog(PO_EAAdministrateModule.class);
    private Textbox txtRecurso, txtDescripcion;
    private Grid grdModule;
    private Groupbox grpModule;
    private BusinessService moduleService;
    private DTO_Company company;

    @Override
    public void realOnCreate()
    {
        moduleService = ContextLoader.getService(this, "moduleService");
        txtRecurso = (Textbox) getFellow("txtRecurso");
        txtDescripcion = (Textbox) getFellow("txtDescripcion");
        grdModule = (Grid) getFellow("grdModule");
        grpModule = (Groupbox) getFellow("grpModule");
        company = (DTO_Company) getDesktop().getSession().getAttribute("company");
        mostrarModules();
    }

    public void onLimpiar()
    {
        grdModule.getRows().getChildren().clear();
        txtRecurso.setValue("");
        txtDescripcion.setValue("");
    }

    public void crearNuevoModule()
    {
        // row1.setVisible(true);
        final DTO_Module unew = new DTO_Module();
        unew.setResource(txtRecurso.getValue());
        unew.setDescription(txtDescripcion.getValue());
        unew.setCompanyId(company.getId());

        if (!txtRecurso.getValue().isEmpty() && !txtDescripcion.getValue().isEmpty()) {
            final ServiceInput input = new ServiceInput(unew);
            input.setAccion(Constantes.V_REGISTER);

            final ServiceOutput output = moduleService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                alertaInfo("", "Module creado correctamente", null);
                onLimpiar();
                mostrarModules();
            } else {
                alertaError("Error al crear Module", "error al crear Module", null);
            }
        } else {
            alertaInfo("Todos los campos deben ser llenados", "No se ingresaron datos para codigo y descripcion", null);
        }
    }

    @SuppressWarnings("unchecked")
    public void mostrarModules()
    {
        final DTO_Module mod = new DTO_Module();
        mod.setCompanyId(company.getId());
        final ServiceInput input = new ServiceInput(mod);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput output = moduleService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Module> ulist = output.getLista();
            for (final DTO_Module sOut : ulist) {
                agregarFila(sOut);
            }
        } else {
            alertaInfo("Error al cargar los modules", "Error al cargar modules", null);
        }
    }

    public void agregarFila(final DTO_Module mod)
    {
        final Row fila = new Row();
        fila.setAttribute("module", mod);

        Textbox txt = new Textbox(mod.getResource());
        txt.setWidth("190px");
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
                                grpModule.setOpen(true);
                                onLimpiar();
                                mostrarModules();
                            }
                        });
        fila.appendChild(txt);

        txt = new Textbox(mod.getDescription());
        txt.setWidth("285px");
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
                                grpModule.setOpen(true);
                                onLimpiar();
                                mostrarModules();
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
                                for (int i = 0; i < grdModule.getRows().getChildren().size(); i++) {
                                    if (!grdModule.getRows().getChildren().get(i)
                                                    .equals((e.getTarget().getParent().getParent()))) {
                                        ((Image) ((Div) (((Row) (((Rows) e.getTarget().getParent().getParent().getParent())
                                                        .getChildren().get(i))).getChildren().get(2))).getChildren().get(0))
                                                        .setVisible(false);
                                        ((Image) ((Div) (((Row) (((Rows) e.getTarget().getParent().getParent().getParent())
                                                        .getChildren().get(i))).getChildren().get(2))).getChildren().get(2))
                                                        .setVisible(true);
                                        ((Image) ((Div) (((Row) (((Rows) e.getTarget().getParent().getParent().getParent())
                                                        .getChildren().get(i))).getChildren().get(3))).getChildren().get(0))
                                                        .setVisible(false);
                                        ((Image) ((Div) (((Row) (((Rows) e.getTarget().getParent().getParent().getParent())
                                                        .getChildren().get(i))).getChildren().get(3))).getChildren().get(1))
                                                        .setVisible(true);
                                    }
                                }
                                ((Textbox) ((Row) e.getTarget().getParent().getParent()).getChildren().get(0)).setReadonly(false);
                                ((Textbox) ((Row) e.getTarget().getParent().getParent()).getChildren().get(0)).setFocus(true);
                                ((Textbox) ((Row) e.getTarget().getParent().getParent()).getChildren().get(1)).setReadonly(false);
                                ((Image) (((Div) ((Row) e.getTarget().getParent().getParent()).getChildren().get(3)))
                                                .getChildren().get(0)).setVisible(false);
                                ((Image) (((Div) ((Row) e.getTarget().getParent().getParent()).getChildren().get(3)))
                                                .getChildren().get(1)).setVisible(true);
                                grpModule.setOpen(false);
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
                                ((Image) ((Div) e.getTarget().getParent()).getChildren().get(0)).setVisible(true);
                                ((Textbox) ((Row) e.getTarget().getParent().getParent()).getChildren().get(0)).setReadonly(true);
                                ((Textbox) ((Row) e.getTarget().getParent().getParent()).getChildren().get(1)).setReadonly(true);
                                ((Image) (((Div) ((Row) e.getTarget().getParent().getParent()).getChildren().get(3)))
                                                .getChildren().get(0)).setVisible(true);
                                ((Image) (((Div) ((Row) e.getTarget().getParent().getParent()).getChildren().get(3)))
                                                .getChildren().get(1)).setVisible(false);
                                grpModule.setOpen(true);
                                for (int i = 0; i < grdModule.getRows().getChildren().size(); i++) {
                                    if (!grdModule.getRows().getChildren().get(i)
                                                    .equals((e.getTarget().getParent().getParent()))) {
                                        ((Image) ((Div) (((Row) (((Rows) e.getTarget().getParent().getParent().getParent())
                                                        .getChildren().get(i))).getChildren().get(2))).getChildren().get(0))
                                                        .setVisible(true);
                                        ((Image) ((Div) (((Row) (((Rows) e.getTarget().getParent().getParent().getParent())
                                                        .getChildren().get(i))).getChildren().get(2))).getChildren().get(2))
                                                        .setVisible(false);
                                        ((Image) ((Div) (((Row) (((Rows) e.getTarget().getParent().getParent().getParent())
                                                        .getChildren().get(i))).getChildren().get(3))).getChildren().get(0))
                                                        .setVisible(true);
                                        ((Image) ((Div) (((Row) (((Rows) e.getTarget().getParent().getParent().getParent())
                                                        .getChildren().get(i))).getChildren().get(3))).getChildren().get(1))
                                                        .setVisible(false);
                                    }
                                }
                                mod.setResource(((Textbox) ((Row) e.getTarget().getParent().getParent()).getChildren().get(0)).getValue());
                                mod.setDescription(((Textbox) ((Row) e.getTarget().getParent().getParent()).getChildren().get(1))
                                                .getValue());
                                actualizaModule(mod);
                            }
                        });

        final Image imgEditarDisab = new Image("media/editdelete.png");
        imgEditarDisab.setVisible(false);

        imgGuardar.setVisible(false);
        final Div divEditar = new Div();
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
                                final int msg = Messagebox.show("¿Está seguro de eliminar el Module?",
                                                company.getBusinessName(), Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION);
                                if (msg == Messagebox.YES) {
                                    eliminaFila(mod);
                                }
                            }
                        });
        final Image imgEliminarDisab = new Image("media/fileclose.png");
        imgEliminarDisab.setVisible(false);

        final Div divEliminar = new Div();
        divEliminar.appendChild(imgEliminar);
        divEliminar.appendChild(imgEliminarDisab);
        fila.appendChild(divEliminar);

        grdModule.getRows().appendChild(fila);
    }

    public void actualizaModule(final DTO_Module rec)
    {
        final ServiceInput input = new ServiceInput(rec);
        input.setAccion(Constantes.V_REGISTER);

        final ServiceOutput output = moduleService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("", "El Module se actualizo correctamente", null);
        } else {
            alertaError("Error al actualizar el Module", "Error al actualizar el Module", null);
        }

        // onLimpiar();
        // mostrarTCampos();
    }

    public void eliminaFila(final DTO_Module rec)
        throws UiException
    {
        final ServiceInput input = new ServiceInput(rec);
        input.setAccion(Constantes.V_DELETE);
        final ServiceOutput output = moduleService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("", "El Module se elimino correctamente", null);
            onLimpiar();
            mostrarModules();
        } else {
            alertaError("Error al eliminar el Module", "Error al eliminar el Module", null);
        }
    }

    public void alertaInfo(final String txt,
                           final String txt2,
                           final Throwable t)
    {
        if (txt.length() > 0) {
            Messagebox.show(txt, company.getBusinessName(), 1, Messagebox.EXCLAMATION);
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
        return new String[] { Constantes.MODULE_ADM_MODULE };
    }
}
