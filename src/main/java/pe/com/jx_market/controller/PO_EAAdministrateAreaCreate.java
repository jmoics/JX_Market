package pe.com.jx_market.controller;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Area;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

@VariableResolver(DelegatingVariableResolver.class)
public class PO_EAAdministrateAreaCreate
    extends SecuredComposer<Window>
{

    static Log logger = LogFactory.getLog(PO_EAProductsCreate.class);
    @Wire
    private Textbox txtAreaName;
    @Wire
    private Window wEAAC;
    @WireVariable
    private BusinessService<DTO_Area> areaService;
    @WireVariable
    private Desktop desktop;
    private DTO_Company company;
    private PO_EAAdministrateArea areaParentUI;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        company = (DTO_Company) _comp.getDesktop().getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        // Obtenemos el controlador de la pantalla principal de areas.
        final Map<?, ?> mapArg = desktop.getExecution().getArg();
        areaParentUI = (PO_EAAdministrateArea) mapArg.get(Constantes.ATTRIBUTE_PARENTFORM);
    }

    @Listen("onClick = #btnSave")
    public void createArea()
    {
        if (!txtAreaName.getValue().equals("")) {
            final DTO_Area newTrMk = new DTO_Area();
            newTrMk.setAreaName(txtAreaName.getValue());
            newTrMk.setCompanyId(company.getId());
            final ServiceInput<DTO_Area> input = new ServiceInput<DTO_Area>(newTrMk);
            input.setAccion(Constantes.V_REGISTER);
            final ServiceOutput<DTO_Area> output = areaService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                final int resp = alertaInfo(logger,
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateAreaCreate.createArea.Info.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateAreaCreate.createArea.Info.Label"),
                                null);
                if (resp == Messagebox.OK) {
                    areaParentUI.getAreas();
                    // desktop.setAttribute(Constantes.ATTRIBUTE_RELOAD, true);
                    // ContextLoader.recargar(desktop,
                    // Constantes.Form.TRADEMARK_FORM.getForm());
                    // wEATC.detach();
                }
            } else {
                alertaError(logger,
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateAreaCreate.createArea.Error.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateAreaCreate.createArea.Error.Label"),
                                null);
            }
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateAreaCreate.createArea.Info2.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EAAdministrateAreaCreate.createArea.Info2.Label"),
                            null);
        }
    }

    @Listen("onClick = #btnClose")
    public void close(final Event _event)
    {
        wEAAC.detach();
    }

    @Override
    String[] requiredResources()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
