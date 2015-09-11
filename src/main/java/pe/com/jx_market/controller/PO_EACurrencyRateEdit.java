package pe.com.jx_market.controller;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.CurrencyRate;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.JXMarketException;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;


public class PO_EACurrencyRateEdit
    extends SecuredComposer<Window>
{
    private final static Log logger = LogFactory.getLog(PO_EACurrencyRateEdit.class);
    @Wire
    private Datebox datFromDate;
    @Wire
    private Datebox datToDate;
    @Wire
    private Decimalbox decRate;
    @Wire
    private Window wEACRE;
    @WireVariable
    private BusinessService<CurrencyRate> currencyRateService;
    @WireVariable
    private Desktop desktop;
    private CurrencyRate currencyRate;
    private PO_EACurrencyRate currencyRateParentUI;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        this.currencyRate = (CurrencyRate) this.desktop.getSession().getAttribute(Constantes.ATTRIBUTE_CURRENCYRATE);
        if (this.currencyRate == null) {
            alertaInfo(logger, "", "No se encontro producto, retornando a busqueda", null);
        } else {
            this.desktop.getSession().removeAttribute(Constantes.ATTRIBUTE_CURRENCYRATE);
            // Obtenemos el controlador de la pantalla principal de marcas.
            final Map<?, ?> mapArg = this.desktop.getExecution().getArg();
            this.currencyRateParentUI = (PO_EACurrencyRate) mapArg.get(Constantes.ATTRIBUTE_PARENTFORM);
            loadData();
        }
    }

    /**
     *
     */
    private void loadData()
    {
        this.datFromDate.setValue(this.currencyRate.getFromDate().toDate());
        this.datToDate.setValue(this.currencyRate.getToDate().toDate());
        this.decRate.setValue(this.currencyRate.getRate());
    }

    @Listen("onClick = #btnSave")
    public void editCurrencyRate()
        throws JXMarketException
    {
        if (this.datFromDate != null && this.datToDate != null && this.decRate != null) {
            final CurrencyRate curRateEdit = this.currencyRate;
            curRateEdit.setFromDate(new DateTime(this.datFromDate.getValue()));
            curRateEdit.setToDate(new DateTime(this.datToDate.getValue()));
            curRateEdit.setRateAmount(this.decRate.getValue());
            final ServiceInput<CurrencyRate> input = new ServiceInput<CurrencyRate>(curRateEdit);
            input.setAction(Constantes.V_REGISTER);
            final ServiceOutput<CurrencyRate> output = this.currencyRateService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                final int resp = alertaInfo(
                                logger,
                                Labels.getLabel("pe.com.jx_market.PO_EACurrencyRateEdit.editCurrencyRate.Info.Label",
                                                new Object[] { this.currencyRate.getFromDate().toString(
                                                                DateTimeFormat.shortDate())
                                                                + " - "
                                                                + this.currencyRate.getToDate().toString(
                                                                DateTimeFormat.shortDate()) }),
                                Labels.getLabel("pe.com.jx_market.PO_EACurrencyRateEdit.editCurrencyRate.Info.Label",
                                                new Object[] { this.currencyRate.getFromDate().toString(
                                                                DateTimeFormat.shortDate())
                                                                + " - "
                                                                + this.currencyRate.getToDate().toString(
                                                                DateTimeFormat.shortDate()) }),
                                null);
                if (resp == Messagebox.OK) {
                    this.currencyRateParentUI.searchCurrencyRates();
                    // desktop.setAttribute(Constantes.ATTRIBUTE_RELOAD, true);
                    // ContextLoader.recargar(desktop,
                    // Constantes.Form.TRADEMARK_FORM.getForm());
                    this.wEACRE.detach();
                }
            } else {
                alertaError(logger,
                                Labels.getLabel("pe.com.jx_market.PO_EACurrencyRateEdit.editCurrencyRate.Error.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EACurrencyRateEdit.editCurrencyRate.Error.Label"),
                                null);
            }
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EACurrencyRateEdit.editCurrencyRate.Info2.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EACurrencyRateEdit.editCurrencyRate.Info2.Label"),
                            null);
        }
    }

    /**
     * @param _event
     */
    @Listen("onClick = #btnClose")
    public void close(final Event _event)
    {
        this.wEACRE.detach();
    }

    @Override
    String[] requiredResources()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
