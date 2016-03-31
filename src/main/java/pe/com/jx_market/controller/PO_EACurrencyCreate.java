package pe.com.jx_market.controller;

import java.util.ArrayList;
import java.util.List;
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
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.Currency;
import pe.com.jx_market.domain.CurrencyRate;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;


/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public class PO_EACurrencyCreate
    extends SecuredComposer<Window>
{
    /**
     *
     */
    private static final long serialVersionUID = 3593429143693471232L;
    /**
     *
     */
    private final Log logger = LogFactory.getLog(PO_EACurrencyCreate.class);
    @Wire
    private Combobox cmbCurBase;
    @Wire
    private Textbox txtCurrencyName;
    @Wire
    private Window wEACC;
    @Wire
    private Include incCurRate;
    @WireVariable
    private BusinessService<Currency> currencyService;
    @WireVariable
    private Desktop desktop;
    private DTO_Company company;
    private Currency currency;
    private PO_EACurrency currencyParentUI;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        this.currency = null;
        this.company = (DTO_Company) _comp.getDesktop().getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        buildActiveCombo(this.cmbCurBase);
        this.cmbCurBase.setSelectedIndex(1);
        // Obtenemos el controlador de la pantalla principal de marcas.
        final Map<?, ?> mapArg = this.desktop.getExecution().getArg();
        this.currencyParentUI = (PO_EACurrency) mapArg.get(Constantes.ATTRIBUTE_PARENTFORM);
        this.desktop.getSession().setAttribute(Constantes.ATTRIBUTE_PARENT_INCLUDEFORM, this);
        this.incCurRate.setSrc(Constantes.Form.CURRENCYRATE_FORM.getForm());
    }

    /**
     *
     */
    @Listen("onClick = #btnSave")
    public void createCurrency()
    {
        if (!this.txtCurrencyName.getValue().equals("")) {
            final Currency newCur = new Currency();
            newCur.setCurBase((Boolean) this.cmbCurBase.getSelectedItem().getValue());
            newCur.setCurrencyName(this.txtCurrencyName.getValue());
            newCur.setCompanyId(this.company.getId());
            final ServiceInput<Currency> input = new ServiceInput<Currency>(newCur);
            input.setAction(Constantes.V_REGISTER);
            final ServiceOutput<Currency> output = this.currencyService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                this.currency = newCur;
                // asignamos la moneda como variable de sesion para obtenerla al crear tipos de cambio
                this.desktop.getSession().setAttribute(Constantes.ATTRIBUTE_CURRENCY, this.currency);
                this.currency.setCurrencyRates(new ArrayList<CurrencyRate>());
                if ((Boolean) this.cmbCurBase.getSelectedItem().getValue()) {
                    updateCurrencyBase(newCur.getId());
                }
                final int resp = alertaInfo(this.logger,
                            Labels.getLabel("pe.com.jx_market.PO_EACurrencyCreate.createCurrency.Info.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EACurrencyCreate.createCurrency.Info.Label"), null);
                if (resp == Messagebox.OK) {
                    this.currencyParentUI.searchCurrencies();
                    // desktop.setAttribute(Constantes.ATTRIBUTE_RELOAD, true);
                    // ContextLoader.recargar(desktop,
                    // Constantes.Form.TRADEMARK_FORM.getForm());
                    // wEATC.detach();
                }
            } else {
                alertaError(this.logger,
                                Labels.getLabel("pe.com.jx_market.PO_EACurrencyCreate.createCurrency.Error.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EACurrencyCreate.createCurrency.Error.Label"),
                                null);
            }
        } else {
            alertaInfo(this.logger,
                            Labels.getLabel("pe.com.jx_market.PO_EACurrencyCreate.createCurrency.Info2.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EACurrencyCreate.createCurrency.Info2.Label"), null);
        }
    }

    /**
     * @param _curBaseId
     */
    private void updateCurrencyBase(final Integer _curBaseId) {
        final Currency curSearch = new Currency();
        curSearch.setCompanyId(this.company.getId());
        final ServiceInput<Currency> input = new ServiceInput<Currency>();
        input.setObject(curSearch);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput<Currency> output = this.currencyService.execute(input);
        if (Constantes.OK == output.getErrorCode()) {
            final List<Currency> lstCur = output.getLista();
            for (final Currency cur : lstCur) {
                if (!_curBaseId.equals(cur.getId())) {
                    final ServiceInput<Currency> input2 = new ServiceInput<Currency>();
                    cur.setCurBase(false);
                    input2.setObject(cur);
                    input2.setAction(Constantes.V_REGISTER);
                    final ServiceOutput<Currency> output2 = this.currencyService.execute(input2);
                    if (Constantes.OK == output2.getErrorCode()) {
                        alertaInfo(this.logger, Constantes.EMPTY_STRING,
                                Labels.getLabel("pe.com.jx_market.PO_EAPCurrencyCreate.createCurrency.Info3.Label"),
                                null);
                    } else {
                        alertaError(this.logger,
                                Labels.getLabel("pe.com.jx_market.PO_EACurrencyCreate.createCurrency.Error2.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EACurrencyCreate.createCurrency.Error2.Label"),
                                null);
                    }
                }
            }
        }
    }

    /**
     * @param _event
     */
    @Listen("onClick = #btnClose")
    public void accionCerrar(final Event _event)
    {
        this.desktop.getSession().removeAttribute(Constantes.ATTRIBUTE_CURRENCY);
        this.wEACC.detach();
    }

    @Override
    String[] requiredResources()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
