package pe.com.jx_market.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.format.DateTimeFormat;
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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.Currency;
import pe.com.jx_market.domain.CurrencyRate;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.FormatUtilities;

@VariableResolver(DelegatingVariableResolver.class)
public class PO_EACurrencyRate
    extends SecuredComposer<Window>
{

    /**
     *
     */
    private static final long serialVersionUID = 6164946527963639382L;

    private final Log logger = LogFactory.getLog(PO_EACurrencyRate.class);
    @Wire
    private Listbox lstCurrencyRate;
    @Wire
    private Window wEACR;
    @WireVariable
    private BusinessService<Currency> currencyService;
    @WireVariable
    private Desktop desktop;
    private DTO_Company company;
    private Currency currency;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        this.company = (DTO_Company) _comp.getDesktop().getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        if (this.desktop.getAttribute(Constantes.ATTRIBUTE_RELOAD) != null
                        && (Boolean) this.desktop.getAttribute(Constantes.ATTRIBUTE_RELOAD)) {
            this.desktop.setAttribute(Constantes.ATTRIBUTE_RELOAD, false);
            searchCurrenciesRates();
        }
    }

    @Listen("onClick = #btnSearch")
    public void searchCurrenciesRates()
    {
        this.lstCurrencyRate.getItems().clear();
        final List<CurrencyRate> lstCurRat = this.currency.getCurrencyRates();
        int columnNumber = 1;
        for (final CurrencyRate curRat : lstCurRat) {
            final Listitem item = new Listitem();
            Listcell cell = new Listcell("" + columnNumber);
            item.appendChild(cell);
            cell = new Listcell(curRat.getFromDate().toString(DateTimeFormat.shortDate()));
            item.appendChild(cell);
            cell = new Listcell(curRat.getToDate().toString(DateTimeFormat.shortDate()));
            item.appendChild(cell);
            cell = new Listcell(FormatUtilities.getFormatter(this.desktop, 2, 4).format(curRat.getRate()));
            item.setAttribute(Constantes.ATTRIBUTE_CURRENCYRATE, curRat);
            item.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>()
            {
                @Override
                public void onEvent(final Event _e)
                    throws UiException
                {
                    runWindowEdit((MouseEvent) _e);
                }
            });
            this.lstCurrencyRate.appendChild(item);
            columnNumber++;
        }
    }

    @Listen("onClick = #btnEdit")
    public void runWindowEdit(final MouseEvent _event)
    {
        if (this.lstCurrencyRate.getSelectedItem() != null) {
            this.desktop.getSession().setAttribute(Constantes.ATTRIBUTE_CURRENCYRATE,
                            this.lstCurrencyRate.getSelectedItem().getAttribute(Constantes.ATTRIBUTE_CURRENCYRATE));
            final Map<String, Object> dataArgs = new HashMap<String, Object>();
            dataArgs.put(Constantes.ATTRIBUTE_PARENTFORM, this);
            final Window w = (Window) Executions.createComponents(Constantes.Form.CURRENCYRATE_EDIT_FORM.getForm(),
                            null, dataArgs);
            w.setPage(this.wEACR.getPage());
            // w.setParent(wEAT);
            w.doModal();
            // w.doHighlighted();
            // w.doEmbedded();
        } else {
            alertaInfo(this.logger, Labels.getLabel("pe.com.jx_market.PO_EACurrencyRate.runWindowEdit.Info.Label"),
                            "No se selecciono un registro a editar", null);
        }
    }

    @Listen("onClick = #btnCreate")
    public void runWindowCreate(final MouseEvent _event)
    {
        if (this.currency == null) {
            this.currency = (Currency) this.desktop.getSession().getAttribute(Constantes.ATTRIBUTE_CURRENCY);
        }
        if (this.currency != null) {
            this.desktop.getSession().removeAttribute(Constantes.ATTRIBUTE_CURRENCY);
            this.desktop.getSession().setAttribute(Constantes.ATTRIBUTE_CURRENCY, this.currency);
            final Map<String, Object> dataArgs = new HashMap<String, Object>();
            dataArgs.put(Constantes.ATTRIBUTE_PARENTFORM, this);
            final Window w = (Window) Executions.createComponents(Constantes.Form.CURRENCYRATE_CREATE_FORM.getForm(),
                            null, dataArgs);
            w.setPage(this.wEACR.getPage());
            // w.setParent(wEAT);
            // w.doOverlapped();
            w.doModal();
            // w.doEmbedded();
        } else {
            alertaInfo(this.logger, Labels.getLabel("pe.com.jx_market.PO_EACurrencyRate.runWindowCreate.Info1.Label"),
                            "Ninguna moneda a sido creada previamente", null);
        }
    }

    /**
     * @param _event
     */
    @Listen("onClick = #btnClose")
    public void accionCerrar(final Event _event)
    {
        this.wEACR.detach();
    }

    @Override
    String[] requiredResources()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
