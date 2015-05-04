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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_TradeMark;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

@VariableResolver(DelegatingVariableResolver.class)
public class PO_EATradeMark
    extends SecuredComposer<Window>
{
    private static final long serialVersionUID = -5977666142043703079L;

    static Log logger = LogFactory.getLog(PO_EATradeMark.class);
    @Wire
    private Combobox cmbActive;
    @Wire
    private Listbox lstTradeMarks;
    @Wire
    private Textbox txtTradeMarkName;
    @Wire
    private Window wEAT;
    @WireVariable
    private BusinessService<DTO_TradeMark> tradeMarkService;
    @WireVariable
    private Desktop desktop;
    private DTO_Company company;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        company = (DTO_Company) _comp.getDesktop().getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        buildActiveCombo(cmbActive);
        if (desktop.getAttribute(Constantes.ATTRIBUTE_RELOAD) != null
                        && (Boolean) desktop.getAttribute(Constantes.ATTRIBUTE_RELOAD)) {
            desktop.setAttribute(Constantes.ATTRIBUTE_RELOAD, false);
            searchTradeMarks();
        }
    }

    @Listen("onClick = #btnSearch")
    public void searchTradeMarks() {
        lstTradeMarks.getItems().clear();
        final DTO_TradeMark trMarkSearch = new DTO_TradeMark();
        trMarkSearch.setCompanyId(company.getId());
        final ServiceInput<DTO_TradeMark> input = new ServiceInput<DTO_TradeMark>();
        if (txtTradeMarkName.getValue().length() > 0) {
            trMarkSearch.setTradeMarkName(txtTradeMarkName.getValue());
        }
        if (cmbActive.getSelectedItem() != null) {
            trMarkSearch.setActive((Boolean) cmbActive.getSelectedItem().getValue());
        }
        input.setObject(trMarkSearch);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput<DTO_TradeMark> output = tradeMarkService.execute(input);
        if (Constantes.OK == output.getErrorCode()) {
            final List<DTO_TradeMark> lstTr = output.getLista();
            int columnNumber = 1;
            for (final DTO_TradeMark trade : lstTr) {
                final Listitem item = new Listitem();
                Listcell cell = new Listcell("" + columnNumber);
                item.appendChild(cell);
                cell = new Listcell(trade.getTradeMarkName());
                item.appendChild(cell);
                if (trade.isActive()) {
                    cell = new Listcell(Labels.getLabel("pe.com.jx_market.Active.TRUE"));
                } else {
                    cell = new Listcell(Labels.getLabel("pe.com.jx_market.Active.FALSE"));
                }
                item.appendChild(cell);
                item.setAttribute(Constantes.ATTRIBUTE_TRADEMARK, trade);
                item.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>()
                {
                    @Override
                    public void onEvent(final Event e)
                        throws UiException
                    {
                        runWindowEdit((MouseEvent) e);
                    }
                });
                lstTradeMarks.appendChild(item);
                columnNumber++;
            }
        }
    }

    @Listen("onClick = #btnEdit")
    public void runWindowEdit(final MouseEvent event) {
        if (lstTradeMarks.getSelectedItem() != null) {
            desktop.getSession().setAttribute(Constantes.ATTRIBUTE_TRADEMARK,
                            lstTradeMarks.getSelectedItem().getAttribute(Constantes.ATTRIBUTE_TRADEMARK));
            final Map<String, Object> dataArgs = new HashMap<String, Object>();
            dataArgs.put(Constantes.ATTRIBUTE_PARENTFORM, this);
            final Window w = (Window) Executions.createComponents(Constantes.Form.TRADEMARK_EDIT_FORM.getForm(),
                            null, dataArgs);
            w.setPage(wEAT.getPage());
            //w.setParent(wEAT);
            w.doOverlapped();
            //w.doHighlighted();
            //w.doEmbedded();
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAPTradeMark.runWindowEdit.Info.Label"),
                                "No se selecciono un registro a editar", null);
        }
    }

    @Listen("onClick = #btnCreate")
    public void runWindowCreate(final MouseEvent event) {
        final Map<String, Object> dataArgs = new HashMap<String, Object>();
        dataArgs.put(Constantes.ATTRIBUTE_PARENTFORM, this);
        final Window w = (Window) Executions.createComponents(Constantes.Form.TRADEMARK_CREATE_FORM.getForm(),
                            null, dataArgs);
        w.setPage(wEAT.getPage());
        //w.setParent(wEAT);
        //w.doOverlapped();
        w.doModal();
        //w.doEmbedded();
    }

    @Override
    String[] requiredResources()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
