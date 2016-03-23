package pe.com.jx_market.controller;

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
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.Currency;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;


public class PO_EACurrencyEdit
    extends SecuredComposer<Window>
{
    private final Log logger = LogFactory.getLog(PO_EACurrencyEdit.class);
    @Wire
    private Combobox cmbCurBase;
    @Wire
    private Textbox txtCurrencyName;
    @Wire
    private Include incCurRate;
    @Wire
    private Window wEACE;
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

        this.company = (DTO_Company) _comp.getDesktop().getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        this.currency = (Currency) this.desktop.getSession().getAttribute(Constantes.ATTRIBUTE_CURRENCY);
        if (this.currency == null) {
            alertaInfo(this.logger, "", "No se encontro producto, retornando a busqueda", null);
        } else {
            // this.desktop.getSession().removeAttribute(Constantes.ATTRIBUTE_CURRENCY);
            // Obtenemos el controlador de la pantalla principal de monedas.
            final Map<?, ?> mapArg = this.desktop.getExecution().getArg();
            this.currencyParentUI = (PO_EACurrency) mapArg.get(Constantes.ATTRIBUTE_PARENTFORM);
            this.incCurRate.setSrc(Constantes.Form.CURRENCYRATE_FORM.getForm());
            // se agrega el controlador de este controlador a la sesion para su uso en el include de tipo de cambio
            this.desktop.getSession().setAttribute(Constantes.ATTRIBUTE_PARENT_INCLUDEFORM, this);
            loadData();
        }
    }

    @Override
    public void buildActiveCombo(final Combobox _combo)
    {
        super.buildActiveCombo(this.cmbCurBase);
        for (final Comboitem item : this.cmbCurBase.getItems()) {
            if (this.currency.isCurBase().equals(item.getValue())) {
                this.cmbCurBase.setSelectedItem(item);
            }
        }
    }

    /**
     *
     */
    private void loadData()
    {
        buildActiveCombo(this.cmbCurBase);
        this.txtCurrencyName.setValue(this.currency.getCurrencyName());
    }

    /**
     *
     */
    @Listen("onClick = #btnSave")
    public void editCurrency()
    {
        if (!this.txtCurrencyName.getValue().equals("") && this.cmbCurBase.getSelectedItem() != null) {
            this.currency.setCurBase((Boolean) this.cmbCurBase.getSelectedItem().getValue());
            this.currency.setCurrencyName(this.txtCurrencyName.getValue());
            final ServiceInput<Currency> input = new ServiceInput<Currency>(this.currency);
            input.setAction(Constantes.V_REGISTER);
            final ServiceOutput<Currency> output = this.currencyService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                if ((Boolean) this.cmbCurBase.getSelectedItem().getValue()) {
                    updateCurrencyBase(this.currency.getId());
                }
                final int resp = alertaInfo(this.logger,
                                Labels.getLabel("pe.com.jx_market.PO_EACurrencyEdit.editCurrency.Info.Label",
                                                new Object[] { this.currency.getCurrencyName() }),
                                Labels.getLabel("pe.com.jx_market.PO_EACurrencyCreate.editCurrency.Info.Label"), null);
                if (resp == Messagebox.OK) {
                    this.currencyParentUI.searchCurrencies();
                    // desktop.setAttribute(Constantes.ATTRIBUTE_RELOAD, true);
                    // ContextLoader.recargar(desktop,
                    // Constantes.Form.TRADEMARK_FORM.getForm());
                }
            } else {
                alertaError(this.logger,
                                Labels.getLabel("pe.com.jx_market.PO_EACurrencyEdit.editCurrency.Error.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EACurrencyEdit.editCurrency.Error.Label"), null);
            }
        } else {
            alertaInfo(this.logger, Labels.getLabel("pe.com.jx_market.PO_EACurrencyEdit.editCurrency.Info2.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EACurrencyEdit.editCurrency.Info2.Label"), null);
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
                                Labels.getLabel("pe.com.jx_market.PO_EACurrencyEdit.editCurrency.Info3.Label"),
                                null);
                    } else {
                        alertaError(this.logger,
                                Labels.getLabel("pe.com.jx_market.PO_EACurrencyEdit.editCurrency.Error2.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EACurrencyEdit.editCurrency.Error2.Label"),
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
        this.wEACE.detach();
    }

    @Override
    String[] requiredResources()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
