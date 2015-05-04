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
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Module;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_EAAdministrateModule
    extends SecuredComposerModal<Window>
{

    static Log logger = LogFactory.getLog(PO_EAAdministrateModule.class);
    @Wire
    private Listbox lstModule;
    @Wire
    private Window wEAAM;
    @WireVariable
    private BusinessService<DTO_Module> moduleService;
    @WireVariable
    private Desktop desktop;
    private DTO_Company company;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        company = (DTO_Company) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        getModules();
    }

    public void getModules()
    {
        lstModule.getItems().clear();
        final DTO_Module modSe = new DTO_Module();
        modSe.setCompanyId(company.getId());
        final ServiceInput<DTO_Module> input = new ServiceInput<DTO_Module>(modSe);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput<DTO_Module> output = moduleService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Module> modLst = output.getLista();
            int columnNumber = 1;
            for (final DTO_Module modu : modLst) {
                final Listitem item = new Listitem();
                Listcell cell = new Listcell("" + columnNumber);
                item.appendChild(cell);
                cell = new Listcell(modu.getModuleResource());
                item.appendChild(cell);
                cell = new Listcell(modu.getModuleDescription());
                item.appendChild(cell);
                item.setAttribute(Constantes.ATTRIBUTE_MODULE, modu);
                item.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>()
                {
                    @Override
                    public void onEvent(final Event e)
                        throws UiException
                    {
                        runWindowEdit((MouseEvent) e);
                    }
                });
                lstModule.appendChild(item);
                columnNumber++;
            }
        } else {
            alertaInfo(logger, "Error al cargar los modulos", "Error al cargar modules", null);
        }
    }

    @Listen("onClick = #btnEdit")
    public void runWindowEdit(final MouseEvent event) {
        if (lstModule.getSelectedItem() != null) {
            desktop.getSession().setAttribute(Constantes.ATTRIBUTE_MODULE,
                            lstModule.getSelectedItem().getAttribute(Constantes.ATTRIBUTE_MODULE));
            final Map<String, Object> dataArgs = new HashMap<String, Object>();
            dataArgs.put(Constantes.ATTRIBUTE_PARENTFORM, this);
            final Window w = (Window) Executions.createComponents(Constantes.Form.MODULE_EDIT_FORM.getForm(),
                            null, dataArgs);
            w.setPage(wEAAM.getPage());
            //w.setParent(wEAT);
            w.doOverlapped();
            //w.doHighlighted();
            //w.doEmbedded();
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateModule.runWindowEdit.Info.Label"),
                                "No se selecciono un registro a editar", null);
        }
    }

    @Listen("onClick = #btnCreate")
    public void runWindowCreate(final MouseEvent event) {
        final Map<String, Object> dataArgs = new HashMap<String, Object>();
        dataArgs.put(Constantes.ATTRIBUTE_PARENTFORM, this);
        final Window w = (Window) Executions.createComponents(Constantes.Form.MODULE_CREATE_FORM.getForm(),
                            null, dataArgs);
        w.setPage(wEAAM.getPage());
        //w.setParent(wEAT);
        //w.doOverlapped();
        w.doModal();
        //w.doEmbedded();
    }

    @Listen("onClick = #btnDelete")
    public void deleteModule(final MouseEvent event)
        throws UiException
    {
        if (lstModule.getSelectedItem() != null) {
            final int verifyDelete = Messagebox.show(
                            Labels.getLabel("pe.com.jx_market.PO_EAAdministrateModule.deleteModule.Info3.Label"),
                            company.getBusinessName(), Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
            if (Messagebox.OK == verifyDelete) {
                final DTO_Module module = (DTO_Module) lstModule.getSelectedItem().getAttribute(
                                Constantes.ATTRIBUTE_MODULE);
                final ServiceInput<DTO_Module> input = new ServiceInput<DTO_Module>(module);
                input.setAction(Constantes.V_DELETE);
                final ServiceOutput<DTO_Module> output = moduleService.execute(input);
                if (output.getErrorCode() == Constantes.OK) {
                    alertaInfo(logger,
                                    Labels.getLabel("pe.com.jx_market.PO_EAAdministrateModule.deleteModule.Info.Label"),
                                    "El modulo se elimino correctamente", null);
                    getModules();
                } else {
                    alertaError(logger,
                                    Labels.getLabel("pe.com.jx_market.PO_EAAdministrateModule.deleteModule.Error.Label"),
                                    "Error al eliminar el modulo", null);
                }
            }
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateModule.deleteModule.Info2.Label"),
                            "No se selecciono un registro a eliminar", null);
        }
    }

    @Override
    String[] requiredResources()
    {
        return new String[] { Constantes.MODULE_ADM_MODULE };
    }
}
