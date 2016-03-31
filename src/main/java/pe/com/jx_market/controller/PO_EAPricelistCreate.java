package pe.com.jx_market.controller;

import java.util.List;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.AbstractPricelist;
import pe.com.jx_market.domain.Currency;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Product;
import pe.com.jx_market.domain.PricelistCost;
import pe.com.jx_market.domain.PricelistRetail;
import pe.com.jx_market.service.IBusinessService;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.JXMarketException;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;


/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public class PO_EAPricelistCreate
    extends SecuredComposer<Window>
{
    private final Log logger = LogFactory.getLog(PO_EAPricelistCreate.class);
    @Wire
    private Datebox datValidFrom;
    @Wire
    private Datebox datValidTo;
    @Wire
    private Decimalbox decPrice;
    @Wire
    private Combobox cmbCurrency;
    @Wire
    private Window wEAPLC;
    @WireVariable
    private IBusinessService<AbstractPricelist> pricelistService;
    @WireVariable
    private BusinessService<Currency> currencyService;
    @WireVariable
    private Desktop desktop;
    private DTO_Product product;
    private PO_EAPricelist pricelistParentUI;
    private Integer pricelistType;
    private DTO_Company company;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);
        this.company = (DTO_Company) _comp.getDesktop().getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        this.product = (DTO_Product) _comp.getDesktop().getSession().getAttribute(Constantes.ATTRIBUTE_PRODUCT);
        _comp.getDesktop().getSession().removeAttribute(Constantes.ATTRIBUTE_CURRENCY);
        // Obtenemos el controlador de la pantalla principal de currencyRates.
        final Map<?, ?> mapArg = this.desktop.getExecution().getArg();
        this.pricelistParentUI = (PO_EAPricelist) mapArg.get(Constantes.ATTRIBUTE_PARENTFORM);
        this.pricelistType = (Integer) mapArg.get(Constantes.V_REGISTERPRICE);

        buildCurrencyCombo();
    }

    /**
     * Build combo of trademarks.
     */
    private void buildCurrencyCombo()
    {
        final Currency curSearch = new Currency();
        curSearch.setCompanyId(this.company.getId());
        final ServiceInput<Currency> input = new ServiceInput<Currency>(curSearch);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput<Currency> output = this.currencyService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo(this.logger, "", "Exito al cargar monedas", null);
            final List<Currency> lstCur = output.getLista();
            for (final Currency cur : lstCur) {
                final Comboitem item = new Comboitem();
                item.setLabel(cur.getCurrencyName());
                item.setValue(cur);
                this.cmbCurrency.appendChild(item);
            }
        } else {
            alertaError(this.logger, Labels.getLabel("pe.com.jx_market.Error.Label"), "Error cargando monedas", null);
        }
    }

    @Listen("onClick = #btnSave")
    public void createPricelist()
        throws JXMarketException
    {
        if (this.datValidFrom != null && this.datValidTo != null
                        && this.decPrice != null && this.cmbCurrency.getSelectedItem() != null) {
            AbstractPricelist newPriceL = null;
            if (Constantes.PRICELIST_RETAIL.equals(this.pricelistType)) {
                newPriceL = new PricelistRetail();
            } else if (Constantes.PRICELIST_COST.equals(this.pricelistType)) {
                newPriceL = new PricelistCost();
            }
            newPriceL.setCurrency((Currency) this.cmbCurrency.getSelectedItem().getValue());
            newPriceL.setProductId(this.product.getId());
            newPriceL.setValidFrom(new DateTime(this.datValidFrom.getValue()));
            newPriceL.setValidTo(new DateTime(this.datValidTo.getValue()));
            newPriceL.setPrice(this.decPrice.getValue());
            final ServiceInput<AbstractPricelist> input = new ServiceInput<AbstractPricelist>(newPriceL);
            input.setAction(Constantes.V_REGISTER);
            final ServiceOutput<AbstractPricelist> output = this.pricelistService.insert(input);
            if (output.getErrorCode() == Constantes.OK) {
                // this.product.getPricelist().add(newPriceL);
                final int resp = alertaInfo(
                            this.logger,
                            Labels.getLabel("pe.com.jx_market.PO_EAPricelistCreate.createPricelist.Info.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EAPricelistCreate.createPricelist.Info.Label"),
                            null);
                if (resp == Messagebox.OK) {
                    this.pricelistParentUI.searchPricelist();
                    // desktop.setAttribute(Constantes.ATTRIBUTE_RELOAD, true);
                    // ContextLoader.recargar(desktop,
                    // Constantes.Form.TRADEMARK_FORM.getForm());
                    this.wEAPLC.detach();
                }
            } else {
                alertaError(this.logger,
                            Labels.getLabel("pe.com.jx_market.PO_EAPricelistCreate.createCurrencyRate.Error.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EAPricelistCreate.createCurrencyRate.Error.Label"),
                            null);
            }
        } else {
            alertaInfo(this.logger,
                            Labels.getLabel("pe.com.jx_market.PO_EAPricelistCreate.createCurrencyRate.Info2.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EAPricelistCreate.createCurrencyRate.Info2.Label"),
                            null);
        }
    }

    /**
     * @param _event
     */
    @Listen("onClick = #btnClose")
    public void accionCerrar(final Event _event)
    {
        this.wEAPLC.detach();
    }

    /* (non-Javadoc)
     * @see pe.com.jx_market.controller.SecuredComposer#requiredResources()
     */
    @Override
    String[] requiredResources()
    {
        // TODO Auto-generated method stub
                return null;
    }
}
