package pe.com.jx_market.controller;

import java.util.List;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.AbstractPricelist;
import pe.com.jx_market.domain.Currency;
import pe.com.jx_market.domain.DTO_Company;
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
public class PO_EAPricelistEdit
    extends SecuredComposer<Window>
{
    private final Log logger = LogFactory.getLog(PO_EAPricelistEdit.class);
    @Wire
    private Datebox datValidFrom;
    @Wire
    private Datebox datValidTo;
    @Wire
    private Decimalbox decPrice;
    @Wire
    private Combobox cmbCurrency;
    @Wire
    private Window wEAPLE;
    @WireVariable
    private IBusinessService<AbstractPricelist> pricelistService;
    @WireVariable
    private BusinessService<Currency> currencyService;
    @WireVariable
    private Desktop desktop;
    private PO_EAPricelist pricelistParentUI;
    private Integer pricelistType;
    private DTO_Company company;
    private AbstractPricelist pricelist;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);
        this.company = (DTO_Company) _comp.getDesktop().getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        this.pricelist = (AbstractPricelist) this.desktop.getSession().getAttribute(Constantes.ATTRIBUTE_PRICELIST);
        if (this.pricelist == null) {
            alertaInfo(this.logger, "", "No se encontro lista de precios, retornando a busqueda", null);
        } else {
            this.desktop.getSession().removeAttribute(Constantes.ATTRIBUTE_PRICELIST);
            // Obtenemos el controlador de la pantalla principal de marcas.
            final Map<?, ?> mapArg = this.desktop.getExecution().getArg();
            this.pricelistParentUI = (PO_EAPricelist) mapArg.get(Constantes.ATTRIBUTE_PARENTFORM);
            loadData();
        }
    }

    /**
    *
    */
    private void loadData()
    {
        this.datValidFrom.setValue(this.pricelist.getValidFrom().toDate());
        this.datValidTo.setValue(this.pricelist.getValidTo().toDate());
        this.decPrice.setValue(this.pricelist.getPrice());
        buildCurrencyCombo();
    }

    /**
     *
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
                if (cur.getId().equals(this.pricelist.getCurrency().getId())) {
                    this.cmbCurrency.setSelectedItem(item);
                }
            }
        } else {
            alertaError(this.logger, Labels.getLabel("pe.com.jx_market.Error.Label"), "Error cargando monedas", null);
        }
    }

    @Listen("onClick = #btnSave")
    public void editPricelist()
        throws JXMarketException
    {
        if (this.datValidFrom != null && this.datValidTo != null
                        && this.decPrice != null && this.cmbCurrency.getSelectedItem() != null) {
            final AbstractPricelist prlEdit = this.pricelist;
            prlEdit.setValidFrom(new DateTime(this.datValidFrom.getValue()));
            prlEdit.setValidTo(new DateTime(this.datValidTo.getValue()));
            prlEdit.setPrice(this.decPrice.getValue());
            prlEdit.setCurrency((Currency) this.cmbCurrency.getSelectedItem().getValue());
            final ServiceInput<AbstractPricelist> input = new ServiceInput<AbstractPricelist>(prlEdit);
            input.setAction(Constantes.V_REGISTER);
            final ServiceOutput<AbstractPricelist> output = this.pricelistService.update(input);
            if (output.getErrorCode() == Constantes.OK) {
                final int resp = alertaInfo(
                                this.logger,
                                Labels.getLabel("pe.com.jx_market.PO_EAPricelistEdit.editPricelist.Info.Label",
                                        new Object[] { this.pricelist.getValidFrom().toString(
                                                    DateTimeFormat.shortDate()) + " - "
                                                    + this.pricelist.getValidTo().toString(
                                                    DateTimeFormat.shortDate()) }),
                                Labels.getLabel("pe.com.jx_market.PO_EAPricelistEdit.editPricelist.Info.Label",
                                        new Object[] { this.pricelist.getValidFrom().toString(
                                                    DateTimeFormat.shortDate()) + " - "
                                                    + this.pricelist.getValidTo().toString(
                                                    DateTimeFormat.shortDate()) }),
                                null);
                if (resp == Messagebox.OK) {
                    this.pricelistParentUI.searchPricelist();
                    // desktop.setAttribute(Constantes.ATTRIBUTE_RELOAD, true);
                    // ContextLoader.recargar(desktop,
                    // Constantes.Form.TRADEMARK_FORM.getForm());
                    this.wEAPLE.detach();
                }
            } else {
                alertaError(this.logger,
                                Labels.getLabel("pe.com.jx_market.PO_EAPricelistEdit.editPricelist.Error.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EAPricelistEdit.editPricelist.Error.Label"),
                                null);
            }
        } else {
            alertaInfo(this.logger, Labels.getLabel("pe.com.jx_market.PO_EAPricelistEdit.editPricelist.Info2.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EAPricelistEdit.editPricelist.Info2.Label"),
                            null);
        }
    }

    /**
     * @param _event
     */
    @Listen("onClick = #btnClose")
    public void close(final Event _event)
    {
        this.wEAPLE.detach();
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
