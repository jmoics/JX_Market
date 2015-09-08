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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_TradeMark;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;


public class PO_EATradeMarkCreate
    extends SecuredComposer<Window>
{
    static Log logger = LogFactory.getLog(PO_EATradeMarkCreate.class);
    @Wire
    private Combobox cmbActive;
    @Wire
    private Textbox txtTradeMarkName;
    @Wire
    private Window wEATC;
    @WireVariable
    private BusinessService<DTO_TradeMark> tradeMarkService;
    @WireVariable
    private Desktop desktop;
    private DTO_Company company;
    private PO_EATradeMark tradeMarkParentUI;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        company = (DTO_Company) _comp.getDesktop().getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        buildActiveCombo(cmbActive);
        // Obtenemos el controlador de la pantalla principal de marcas.
        final Map<?, ?> mapArg = desktop.getExecution().getArg();
        tradeMarkParentUI = (PO_EATradeMark) mapArg.get(Constantes.ATTRIBUTE_PARENTFORM);
    }

    @Listen("onClick = #btnSave")
    public void createTradeMark()
    {
        if (!txtTradeMarkName.getValue().equals("") && cmbActive.getSelectedItem() != null) {
            final DTO_TradeMark newTrMk = new DTO_TradeMark();
            newTrMk.setActive((Boolean) cmbActive.getSelectedItem().getValue());
            newTrMk.setTradeMarkName(txtTradeMarkName.getValue());
            newTrMk.setCompanyId(company.getId());
            final ServiceInput<DTO_TradeMark> input = new ServiceInput<DTO_TradeMark>(newTrMk);
            input.setAction(Constantes.V_REGISTER);
            final ServiceOutput<DTO_TradeMark> output = tradeMarkService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                final int resp = alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAPTradeMarkCreate.createTradeMark.Info.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EATradeMarkCreate.createTradeMark.Info.Label"), null);
                if (resp == Messagebox.OK) {
                    tradeMarkParentUI.searchTradeMarks();
                    //desktop.setAttribute(Constantes.ATTRIBUTE_RELOAD, true);
                    //ContextLoader.recargar(desktop, Constantes.Form.TRADEMARK_FORM.getForm());
                    //wEATC.detach();
                }
            } else {
                alertaError(logger, Labels.getLabel("pe.com.jx_market.PO_EAPTradeMarkCreate.createTradeMark.Error.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EAPTradeMarkCreate.createTradeMark.Error.Label"), null);
            }
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAPTradeMarkCreate.createTradeMark.Info2.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EAPTradeMarkCreate.createTradeMark.Info2.Label"), null);
        }
    }

    @Listen("onClick = #btnClose")
    public void accionCerrar(final Event _event) {
        wEATC.detach();
    }

    @Override
    String[] requiredResources()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
