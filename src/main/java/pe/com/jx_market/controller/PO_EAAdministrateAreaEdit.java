package pe.com.jx_market.controller;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Area;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;


public class PO_EAAdministrateAreaEdit
    extends SecuredComposer<Window>
{
    static Log logger = LogFactory.getLog(PO_EAProductsCreate.class);
    @Wire
    private Textbox txtAreaName;
    @Wire
    private Window wEAAE;
    @WireVariable
    private BusinessService<DTO_Area> areaService;
    @WireVariable
    private Desktop desktop;
    private DTO_Area area;
    private PO_EAAdministrateArea areaParentUI;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        area = (DTO_Area) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_AREA);
        if (area == null) {
            alertaInfo(logger, "", "No se encontro área, retornando a busqueda", null);
        } else {
            desktop.getSession().removeAttribute(Constantes.ATTRIBUTE_AREA);
            // Obtenemos el controlador de la pantalla principal de marcas.
            final Map<?, ?> mapArg = desktop.getExecution().getArg();
            areaParentUI = (PO_EAAdministrateArea) mapArg.get(Constantes.ATTRIBUTE_PARENTFORM);
            loadData();
        }
    }

    private void loadData()
    {
        txtAreaName.setValue(area.getAreaName());
    }

    @Listen("onClick = #btnSave")
    public void editArea()
    {
        if (!txtAreaName.getValue().equals("")) {
            area.setAreaName(txtAreaName.getValue());
            final ServiceInput<DTO_Area> input = new ServiceInput<DTO_Area>(area);
            input.setAccion(Constantes.V_REGISTER);
            final ServiceOutput<DTO_Area> output = areaService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                final int resp = alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateAreaEdit.editArea.Info.Label",
                                        new Object[] {area.getAreaName()}),
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateAreaEdit.editArea.Info.Label",
                                        new Object[] {area.getAreaName()}), null);
                if (resp == Messagebox.OK) {
                    areaParentUI.getAreas();
                    //desktop.setAttribute(Constantes.ATTRIBUTE_RELOAD, true);
                    //ContextLoader.recargar(desktop, Constantes.Form.TRADEMARK_FORM.getForm());
                }
            } else {
                alertaError(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateAreaEdit.editArea.Error.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateAreaEdit.editArea.Error.Label"), null);
            }
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateAreaEdit.editArea.Info2.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EAAdministrateAreaEdit.editArea.Info2.Label"), null);
        }
    }

    @Listen("onClick = #btnClose")
    public void close(final Event _event) {
        wEAAE.detach();
    }

    @Override
    String[] requiredResources()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
