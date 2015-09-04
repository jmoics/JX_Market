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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.Currency;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

@VariableResolver(DelegatingVariableResolver.class)
public class PO_EACurrency
    extends SecuredComposer<Window>
{

    private static final long serialVersionUID = -5977666142043703079L;

    static Log logger = LogFactory.getLog(PO_EACurrency.class);
    @Wire
    private Listbox lstCurrency;
    @Wire
    private Textbox txtCurrencyName;
    @Wire
    private Window wEAC;
    @WireVariable
    private BusinessService<Currency> currencyService;
    @WireVariable
    private Desktop desktop;
    private DTO_Company company;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        this.company = (DTO_Company) _comp.getDesktop().getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        if (this.desktop.getAttribute(Constantes.ATTRIBUTE_RELOAD) != null
                        && (Boolean) this.desktop.getAttribute(Constantes.ATTRIBUTE_RELOAD)) {
            this.desktop.setAttribute(Constantes.ATTRIBUTE_RELOAD, false);
            searchCurrencies();
        }
    }

    @Listen("onClick = #btnSearch")
    public void searchCurrencies()
    {
        this.lstCurrency.getItems().clear();
        final Currency curSearch = new Currency();
        curSearch.setCompanyId(this.company.getId());
        final ServiceInput<Currency> input = new ServiceInput<Currency>();
        if (this.txtCurrencyName.getValue().length() > 0) {
            curSearch.setCurrencyName(this.txtCurrencyName.getValue());
        }
        input.setObject(curSearch);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput<Currency> output = this.currencyService.execute(input);
        if (Constantes.OK == output.getErrorCode()) {
            final List<Currency> lstTr = output.getLista();
            int columnNumber = 1;
            for (final Currency trade : lstTr) {
                final Listitem item = new Listitem();
                Listcell cell = new Listcell("" + columnNumber);
                item.appendChild(cell);
                cell = new Listcell(trade.getCurrencyName());
                item.appendChild(cell);
                if (trade.isCurBase()) {
                    cell = new Listcell(Labels.getLabel("pe.com.jx_market.Active.TRUE"));
                } else {
                    cell = new Listcell(Labels.getLabel("pe.com.jx_market.Active.FALSE"));
                }
                item.appendChild(cell);
                item.setAttribute(Constantes.ATTRIBUTE_CURRENCY, trade);
                item.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>()
                {
                    @Override
                    public void onEvent(final Event _e)
                        throws UiException
                    {
                        runWindowEdit((MouseEvent) _e);
                    }
                });
                this.lstCurrency.appendChild(item);
                columnNumber++;
            }
        }
    }

    @Listen("onClick = #btnEdit")
    public void runWindowEdit(final MouseEvent _event)
    {
        if (this.lstCurrency.getSelectedItem() != null) {
            this.desktop.getSession().setAttribute(Constantes.ATTRIBUTE_CURRENCY,
                            this.lstCurrency.getSelectedItem().getAttribute(Constantes.ATTRIBUTE_CURRENCY));
            final Map<String, Object> dataArgs = new HashMap<String, Object>();
            dataArgs.put(Constantes.ATTRIBUTE_PARENTFORM, this);
            final Window w = (Window) Executions.createComponents(Constantes.Form.CURRENCY_EDIT_FORM.getForm(),
                            null, dataArgs);
            w.setPage(this.wEAC.getPage());
            // w.setParent(wEAT);
            w.doModal();
            // w.doHighlighted();
            // w.doEmbedded();
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EACurrency.runWindowEdit.Info.Label"),
                            "No se selecciono un registro a editar", null);
        }
    }

    @Listen("onClick = #btnCreate")
    public void runWindowCreate(final MouseEvent _event)
    {
        final Map<String, Object> dataArgs = new HashMap<String, Object>();
        dataArgs.put(Constantes.ATTRIBUTE_PARENTFORM, this);
        final Window w = (Window) Executions.createComponents(Constantes.Form.CURRENCY_CREATE_FORM.getForm(),
                        null, dataArgs);
        w.setPage(this.wEAC.getPage());
        // w.setParent(wEAT);
        // w.doOverlapped();
        w.doModal();
        // w.doEmbedded();
    }

    @Override
    String[] requiredResources()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
