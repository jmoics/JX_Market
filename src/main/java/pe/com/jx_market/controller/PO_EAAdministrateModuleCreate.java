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

import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Module;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_EAAdministrateModuleCreate
    extends SecuredComposerModal<Window>
{

    static Log logger = LogFactory.getLog(PO_EAAdministrateModuleCreate.class);
    @Wire
    private Textbox txtResource, txtDescription;
    @Wire
    private Window wEAAMC;
    @WireVariable
    private BusinessService<DTO_Module> moduleService;
    @WireVariable
    private Desktop desktop;
    private DTO_Company company;
    private PO_EAAdministrateModule moduleParentUI;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        company = (DTO_Company) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);

        // Obtenemos el controlador de la pantalla principal de roles.
        final Map<?, ?> mapArg = desktop.getExecution().getArg();
        moduleParentUI = (PO_EAAdministrateModule) mapArg.get(Constantes.ATTRIBUTE_PARENTFORM);
    }

    @Listen("onClick = #btnSave")
    public void createModule()
    {
        if (!txtDescription.getValue().isEmpty() && !txtResource.getValue().isEmpty()) {
            final DTO_Module modNew = new DTO_Module();
            modNew.setModuleResource(txtResource.getValue());
            modNew.setModuleDescription(txtDescription.getValue());
            modNew.setCompanyId(company.getId());

            final ServiceInput<DTO_Module> input = new ServiceInput<DTO_Module>(modNew);
            input.setAction(Constantes.V_REGISTER);

            final ServiceOutput<DTO_Module> output = moduleService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                final int resp = alertaInfo(logger,
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateModuleCreate.createModule.Info.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateModuleCreate.createModule.Info.Label"),
                                null);
                if (resp == Messagebox.OK) {
                    moduleParentUI.getModules();
                }
            } else {
                alertaError(logger,
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateModuleCreate.createModule.Error.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateModuleCreate.createModule.Error.Label"),
                                null);
            }

        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateModuleCreate.createModule.Info2.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EAAdministrateModuleCreate.createModule.Info2.Label"),
                            null);
        }
    }

    @Listen("onClick = #btnClose")
    public void close(final Event _event)
    {
        wEAAMC.detach();
    }

    @Override
    String[] requiredResources()
    {
        return new String[] { Constantes.MODULE_ADM_MODULE };
    }
}
