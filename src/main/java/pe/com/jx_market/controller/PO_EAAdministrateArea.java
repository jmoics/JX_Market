package pe.com.jx_market.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Area;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

@VariableResolver(DelegatingVariableResolver.class)
public class PO_EAAdministrateArea
    extends SecuredComposer<Window>
{
    static Log logger = LogFactory.getLog(PO_EAAdministrateArea.class);
    @Wire
    private Listbox lstArea;
    @Wire
    private Popup popEmployees;
    @Wire
    private Window wEAAA;
    @WireVariable
    private BusinessService<DTO_Area> areaService;
    @WireVariable
    private Desktop desktop;
    private DTO_Company company;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        company = (DTO_Company) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        getAreas();
    }

    public void getAreas()
    {
        lstArea.getItems().clear();
        final DTO_Area areSe = new DTO_Area();
        areSe.setCompanyId(company.getId());
        final ServiceInput<DTO_Area> input = new ServiceInput<DTO_Area>(areSe);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput<DTO_Area> output = areaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Area> areaLst = output.getLista();
            int columnNumber = 1;
            for (final DTO_Area area : areaLst) {
                final Listitem item = new Listitem();
                Listcell cell = new Listcell("" + columnNumber);
                item.appendChild(cell);
                cell = new Listcell(area.getAreaName());
                item.appendChild(cell);
                cell = new Listcell();
                final Image ImDetalles = new Image("media/details.png");
                ImDetalles.setStyle("cursor:pointer");
                ImDetalles.setPopup(popEmployees);
                ImDetalles.addEventListener(Events.ON_CLICK,
                new org.zkoss.zk.ui.event.EventListener<Event>() {
                    @Override
                    public void onEvent(final Event e)
                        throws UiException
                    {
                        /*
                         * cargarPop((DTO_Employee) ((Row)
                         * e.getTarget().
                         * getParent()).getAttribute("employee"));
                         */
                    }
                });
                cell.appendChild(ImDetalles);
                item.appendChild(cell);
                item.setAttribute(Constantes.ATTRIBUTE_AREA, area);
                item.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>()
                {
                    @Override
                    public void onEvent(final Event e)
                        throws UiException
                    {
                        runWindowEdit((MouseEvent) e);
                    }
                });
                lstArea.appendChild(item);
                columnNumber++;
            }
        } else {
            alertaError(logger, "Error al cargar areas", "Error al cargar areas", null);
        }
    }

    @Listen("onClick = #btnEdit")
    public void runWindowEdit(final MouseEvent event) {
        if (lstArea.getSelectedItem() != null) {
            desktop.getSession().setAttribute(Constantes.ATTRIBUTE_AREA,
                            lstArea.getSelectedItem().getAttribute(Constantes.ATTRIBUTE_AREA));
            final Map<String, Object> dataArgs = new HashMap<String, Object>();
            dataArgs.put(Constantes.ATTRIBUTE_PARENTFORM, this);
            final Window w = (Window) Executions.createComponents(Constantes.Form.AREA_EDIT_FORM.getForm(),
                            null, dataArgs);
            w.setPage(wEAAA.getPage());
            //w.setParent(wEAT);
            w.doOverlapped();
            //w.doHighlighted();
            //w.doEmbedded();
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateArea.runWindowEdit.Info.Label"),
                                "No se selecciono un registro a editar", null);
        }
    }

    @Listen("onClick = #btnCreate")
    public void runWindowCreate(final MouseEvent event) {
        final Map<String, Object> dataArgs = new HashMap<String, Object>();
        dataArgs.put(Constantes.ATTRIBUTE_PARENTFORM, this);
        final Window w = (Window) Executions.createComponents(Constantes.Form.AREA_CREATE_FORM.getForm(),
                            null, dataArgs);
        w.setPage(wEAAA.getPage());
        //w.setParent(wEAT);
        //w.doOverlapped();
        w.doModal();
        //w.doEmbedded();
    }

    @Listen("onClick = #btnDelete")
    public void deleteArea(final MouseEvent event)
        throws UiException
    {
        if (lstArea.getSelectedItem() != null) {
            final int verifyDelete = Messagebox.show(
                            Labels.getLabel("pe.com.jx_market.PO_EAAdministrateArea.deleteArea.Info3.Label"),
                            company.getBusinessName(), Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
            if (Messagebox.OK == verifyDelete) {
                final DTO_Area area = (DTO_Area) lstArea.getSelectedItem().getAttribute(Constantes.ATTRIBUTE_AREA);
                final ServiceInput<DTO_Area> input = new ServiceInput<DTO_Area>(area);
                input.setAction(Constantes.V_DELETE);
                final ServiceOutput<DTO_Area> output = areaService.execute(input);
                if (output.getErrorCode() == Constantes.OK) {
                    alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateArea.deleteArea.Info.Label"),
                                    "El area se elimino correctamente", null);
                    getAreas();
                } else {
                    alertaError(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateArea.deleteArea.Error.Label"),
                                    "Error al eliminar el area", null);
                }
            }
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateArea.deleteArea.Info2.Label"),
                            "No se selecciono un registro a eliminar", null);
        }
    }

    @Override
    String[] requiredResources()
    {
        return new String[] { Constantes.MODULE_ADM_AREA };
    }
}
