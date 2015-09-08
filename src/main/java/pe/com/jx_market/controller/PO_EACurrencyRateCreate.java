package pe.com.jx_market.controller;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
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

import pe.com.jx_market.domain.Currency;
import pe.com.jx_market.domain.CurrencyRate;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;


public class PO_EACurrencyRateCreate
    extends SecuredComposer<Window>
{
    private final Log logger = LogFactory.getLog(PO_EACurrencyRateCreate.class);
    @Wire
    private Datebox datFromDate;
    @Wire
    private Datebox datToDate;
    @Wire
    private Decimalbox decRate;
    @Wire
    private Window wEACRC;
    @WireVariable
    private BusinessService<CurrencyRate> currencyRateService;
    @WireVariable
    private Desktop desktop;
    private DTO_Company company;
    private Currency currency;
    private PO_EACurrencyRate currencyRateParentUI;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        this.company = (DTO_Company) _comp.getDesktop().getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        this.currency = (Currency) _comp.getDesktop().getSession().getAttribute(Constantes.ATTRIBUTE_CURRENCY);
        _comp.getDesktop().getSession().removeAttribute(Constantes.ATTRIBUTE_CURRENCY);
        // Obtenemos el controlador de la pantalla principal de currencyRates.
        final Map<?, ?> mapArg = this.desktop.getExecution().getArg();
        this.currencyRateParentUI = (PO_EACurrencyRate) mapArg.get(Constantes.ATTRIBUTE_PARENTFORM);
    }

    /**
     *
     */
    @Listen("onClick = #btnSave")
    public void createCurrencyRate()
    {
        if (this.datFromDate != null && this.datToDate != null && this.decRate != null) {
            final CurrencyRate newCurRat = new CurrencyRate();
            newCurRat.setCurrencyId(this.currency.getId());
            newCurRat.setCompanyId(this.company.getId());
            newCurRat.setFromDate(new DateTime(this.datFromDate.getValue()));
            newCurRat.setToDate(new DateTime(this.datToDate.getValue()));
            newCurRat.setRateAmount(this.decRate.getValue());
            final ServiceInput<CurrencyRate> input = new ServiceInput<CurrencyRate>(newCurRat);
            input.setAction(Constantes.V_REGISTER);
            final ServiceOutput<CurrencyRate> output = this.currencyRateService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                this.currency.getCurrencyRates().add(newCurRat);
                final int resp = alertaInfo(this.logger,
                            Labels.getLabel("pe.com.jx_market.PO_EACurrencyRateCreate.createCurrencyRate.Info.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EACurrencyRateCreate.createCurrencyRate.Info.Label"),
                            null);
                if (resp == Messagebox.OK) {
                    this.currencyRateParentUI.searchCurrenciesRates();
                    // desktop.setAttribute(Constantes.ATTRIBUTE_RELOAD, true);
                    // ContextLoader.recargar(desktop,
                    // Constantes.Form.TRADEMARK_FORM.getForm());
                    this.wEACRC.detach();
                }
            } else {
                alertaError(this.logger,
                            Labels.getLabel("pe.com.jx_market.PO_EACurrencyRateCreate.createCurrencyRate.Error.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EACurrencyRateCreate.createCurrencyRate.Error.Label"),
                            null);
            }
        } else {
            alertaInfo(this.logger,
                            Labels.getLabel("pe.com.jx_market.PO_EACurrencyRateCreate.createCurrencyRate.Info2.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EACurrencyRateCreate.createCurrencyRate.Info2.Label"),
                            null);
        }
    }

    /**
     * @param _event
     */
    @Listen("onClick = #btnClose")
    public void accionCerrar(final Event _event)
    {
        this.wEACRC.detach();
    }

    @Override
    String[] requiredResources()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
