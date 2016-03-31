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
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.AbstractPricelist;
import pe.com.jx_market.domain.DTO_Product;
import pe.com.jx_market.domain.PricelistCost;
import pe.com.jx_market.domain.PricelistRetail;
import pe.com.jx_market.service.IBusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.FormatUtilities;
import pe.com.jx_market.utilities.JXMarketException;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public class PO_EAPricelist
    extends SecuredComposer<Window>
{
    /**
     *
     */
    private static final long serialVersionUID = 5564047296208659464L;
    private final Log logger = LogFactory.getLog(PO_EAPricelist.class);
    @Wire
    private Listbox lstPricelist;
    @Wire
    private Window wEAPL;
    @WireVariable
    private IBusinessService<AbstractPricelist> pricelistService;
    @WireVariable
    private Desktop desktop;
    private DTO_Product product;
    private Integer pricelistType;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);
        this.pricelistType = (Integer) _comp.getParent().getAttribute(Constantes.V_REGISTERPRICE);
        searchPricelist();
    }

    @Listen("onClick = #btnSearch")
    protected void searchPricelist()
        throws JXMarketException
    {
        this.lstPricelist.getItems().clear();
        if (this.product == null) {
            this.product = (DTO_Product) this.desktop.getSession().getAttribute(Constantes.ATTRIBUTE_PRODUCT);
        }
        if (this.product != null) {
            AbstractPricelist pricelistS = null;
            if (this.pricelistType.equals(Constantes.PRICELIST_RETAIL)) {
                pricelistS = new PricelistRetail();
            } else if (this.pricelistType.equals(Constantes.PRICELIST_COST)) {
                pricelistS = new PricelistCost();
            }
            pricelistS.setProductId(this.product.getId());
            final ServiceInput<AbstractPricelist> input = new ServiceInput<AbstractPricelist>();
            input.setObject(pricelistS);
            input.setAction(Constantes.V_LIST);
            final ServiceOutput<AbstractPricelist> output = this.pricelistService.list(input);
            if (Constantes.OK == output.getErrorCode()) {
                if (this.pricelistType.equals(Constantes.PRICELIST_RETAIL)) {
                    buildListResult(output, PricelistRetail.class);
                } else if (this.pricelistType.equals(Constantes.PRICELIST_COST)) {
                    buildListResult(output, PricelistCost.class);
                }
            } else {
                // Create Initial Mode
            }
        }
    }

    private <E> void buildListResult(final ServiceOutput<AbstractPricelist> _output,
                                     final Class<E> _typeKey)
        throws JXMarketException
    {
        final List<E> lstPl = (List<E>) _output.getSecList();
        int columnNumber = 1;
        for (final E curPl : lstPl) {
            final Listitem item = new Listitem();
            Listcell cell = new Listcell("" + columnNumber);
            item.appendChild(cell);
            cell = new Listcell(((AbstractPricelist) curPl).getValidFrom().toString(DateTimeFormat.shortDate()));
            item.appendChild(cell);
            cell = new Listcell(((AbstractPricelist) curPl).getValidTo().toString(DateTimeFormat.shortDate()));
            item.appendChild(cell);
            cell = new Listcell(((AbstractPricelist) curPl).getCurrency().getCurrencyName());
            item.appendChild(cell);
            cell = new Listcell(FormatUtilities.getFormatter(this.desktop, 2, 4).format(
                            ((AbstractPricelist) curPl).getPrice()));
            item.appendChild(cell);
            item.setAttribute(Constantes.ATTRIBUTE_CURRENCYRATE, curPl);
            item.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>()
            {

                @Override
                public void onEvent(final Event _e)
                    throws UiException
                {
                    runWindowEdit((MouseEvent) _e);
                }
            });
            this.lstPricelist.appendChild(item);
            columnNumber++;
        }
    }

    /**
     * @param _event
     */
    @Listen("onClick = #btnEdit")
    public void runWindowEdit(final MouseEvent _event)
    {
        if (this.lstPricelist.getSelectedItem() != null) {
            this.desktop.getSession().setAttribute(Constantes.ATTRIBUTE_PRICELIST,
                            this.lstPricelist.getSelectedItem().getAttribute(Constantes.ATTRIBUTE_PRICELIST));
            final Map<String, Object> dataArgs = new HashMap<String, Object>();
            dataArgs.put(Constantes.ATTRIBUTE_PARENTFORM, this);
            dataArgs.put(Constantes.V_REGISTERPRICE, this.pricelistType);
            final Window w = (Window) Executions.createComponents(Constantes.Form.PRICELIST_EDIT_FORM.getForm(),
                            null, dataArgs);
            w.setPage(this.wEAPL.getPage());
            // w.setParent(wEAT);
            w.doModal();
            // w.doHighlighted();
            // w.doEmbedded();
        } else {
            alertaInfo(this.logger, Labels.getLabel("pe.com.jx_market.PO_EAPricelist.runWindowEdit.Info.Label"),
                            "No se selecciono un registro a editar", null);
        }
    }

    /**
     * @param _event
     */
    @Listen("onClick = #btnCreate")
    public void runWindowCreate(final MouseEvent _event)
    {
        if (this.product == null) {
            this.product = (DTO_Product) this.desktop.getSession().getAttribute(Constantes.ATTRIBUTE_PRODUCT);
        }
        if (this.product != null) {
            final Map<String, Object> dataArgs = new HashMap<String, Object>();
            dataArgs.put(Constantes.ATTRIBUTE_PARENTFORM, this);
            dataArgs.put(Constantes.V_REGISTERPRICE, this.pricelistType);
            final Window w = (Window) Executions.createComponents(Constantes.Form.PRICELIST_CREATE_FORM.getForm(),
                            null, dataArgs);
            w.setPage(this.wEAPL.getPage());
            // w.setParent(wEAT);
            // w.doOverlapped();
            w.doModal();
            // w.doEmbedded();
        } else {
            alertaInfo(this.logger, Labels.getLabel("pe.com.jx_market.PO_EAPricelist.runWindowCreate.Info1.Label"),
                            "Ningún producto ha sido creado previamente", null);
        }
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
