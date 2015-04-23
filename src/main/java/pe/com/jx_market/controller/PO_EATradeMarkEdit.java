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
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_TradeMark;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;


public class PO_EATradeMarkEdit
    extends SecuredComposer<Window>
{
    static Log logger = LogFactory.getLog(PO_EAProductsCreate.class);
    @Wire
    private Combobox cmbActive;
    @Wire
    private Textbox txtTradeMarkName;
    @Wire
    private Window wEATE;
    @WireVariable
    private BusinessService<DTO_TradeMark> tradeMarkService;
    @WireVariable
    private Desktop desktop;
    private DTO_TradeMark tradeMark;
    private PO_EATradeMark tradeMarkParentUI;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        tradeMark = (DTO_TradeMark) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_TRADEMARK);
        if (tradeMark == null) {
            alertaInfo(logger, "", "No se encontro producto, retornando a busqueda", null);
        } else {
            desktop.getSession().removeAttribute(Constantes.ATTRIBUTE_TRADEMARK);
            // Obtenemos el controlador de la pantalla principal de marcas.
            final Map<?, ?> mapArg = desktop.getExecution().getArg();
            tradeMarkParentUI = (PO_EATradeMark) mapArg.get("parent");
            loadData();
        }
    }

    @Override
    public void buildActiveCombo(final Combobox _combo)
    {
        super.buildActiveCombo(cmbActive);
        for(final Comboitem item : cmbActive.getItems()) {
            if (tradeMark.isActive().equals(item.getValue())){
                cmbActive.setSelectedItem(item);
            }
        }
    }

    private void loadData()
    {
        buildActiveCombo(cmbActive);
        txtTradeMarkName.setValue(tradeMark.getTradeMarkName());
    }

    @Listen("onClick = #btnSave")
    public void editTradeMark()
    {
        if (!txtTradeMarkName.getValue().equals("") && cmbActive.getSelectedItem() != null) {
            tradeMark.setActive((Boolean) cmbActive.getSelectedItem().getValue());
            tradeMark.setTradeMarkName(txtTradeMarkName.getValue());
            final ServiceInput<DTO_TradeMark> input = new ServiceInput<DTO_TradeMark>(tradeMark);
            input.setAccion(Constantes.V_REGISTER);
            final ServiceOutput<DTO_TradeMark> output = tradeMarkService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                final int resp = alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAPTradeMarkEdit.editTradeMark.Info.Label",
                                        new Object[] {tradeMark.getTradeMarkName()}),
                                Labels.getLabel("pe.com.jx_market.PO_EATradeMarkCreate.editTradeMark.Info.Label"), null);
                if (resp == Messagebox.OK) {
                    tradeMarkParentUI.searchTradeMarks();
                    //desktop.setAttribute(Constantes.ATTRIBUTE_RELOAD, true);
                    //ContextLoader.recargar(desktop, Constantes.Form.TRADEMARK_FORM.getForm());
                }
            } else {
                alertaError(logger, Labels.getLabel("pe.com.jx_market.PO_EAPTradeMarkEdit.editTradeMark.Error.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EAPTradeMarkEdit.editTradeMark.Error.Label"), null);
            }
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAPTradeMarkEdit.editTradeMark.Info2.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EAPTradeMarkEdit.editTradeMark.Info2.Label"), null);
        }
    }

    @Listen("onClick = #btnClose")
    public void accionCerrar(final Event _event) {
        wEATE.detach();
    }

    @Override
    String[] requiredResources()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
