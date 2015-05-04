package pe.com.jx_market.controller;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Module;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_EAAdministrateModuleEdit
    extends SecuredComposerModal<Window>
{

    static Log logger = LogFactory.getLog(PO_EAAdministrateModuleEdit.class);
    @Wire
    private Textbox txtResource, txtDescription;
    @Wire
    private Window wEAAME;
    @WireVariable
    private BusinessService<DTO_Module> moduleService;
    @WireVariable
    private Desktop desktop;
    private DTO_Module module;
    private PO_EAAdministrateModule moduleParentUI;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        module = (DTO_Module) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_MODULE);
        if (module == null) {
            alertaInfo(logger, "", "No se encontro module, retornando a busqueda", null);
        } else {
            // Obtenemos el controlador de la pantalla principal de marcas.
            final Map<?, ?> mapArg = desktop.getExecution().getArg();
            moduleParentUI = (PO_EAAdministrateModule) mapArg.get(Constantes.ATTRIBUTE_PARENTFORM);
            loadData();
        }
    }

    private void loadData()
    {
        txtResource.setValue(module.getModuleResource());
        txtDescription.setValue(module.getModuleDescription());
    }

    @Listen("onClick = #btnSave")
    public void editModule()
    {
        // row1.setVisible(true);
        if (!txtDescription.getValue().isEmpty() && !txtResource.getValue().isEmpty()) {
            module.setModuleDescription(txtDescription.getValue());
            module.setModuleResource(txtResource.getValue());

            final ServiceInput<DTO_Module> input = new ServiceInput<DTO_Module>(module);
            input.setAction(Constantes.V_REGISTER);
            final ServiceOutput<DTO_Module> output = moduleService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                final int resp = alertaInfo(logger,
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateModuleEdit.editModule.Info.Label",
                                                new Object[] { module.getModuleResource() }),
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateModuleEdit.editModule.Info.Label",
                                                new Object[] { module.getModuleResource() }), null);
                if (resp == Messagebox.OK) {
                    moduleParentUI.getModules();
                }
            } else {
                alertaError(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateModuleEdit.editModule.Error.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateModuleEdit.editModule.Error.Label"),
                                null);
            }
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateModuleEdit.editModule.Info2.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EAAdministrateModuleEdit.editModule.Info2.Label"), null);
        }
    }

    @Listen("onClick = #btnClose")
    public void close(final Event _event)
    {
        wEAAME.detach();
    }

    @Override
    String[] requiredResources()
    {
        return new String[] { Constantes.MODULE_ADM_MODULE };
    }
}
