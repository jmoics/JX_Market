package pe.com.jx_market.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Area;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Role;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

@VariableResolver(DelegatingVariableResolver.class)
public class PO_EAAdministrateRoleCreate
    extends SecuredComposer<Window>
{

    static Log logger = LogFactory.getLog(PO_EAAdministrateRoleCreate.class);
    @Wire
    private Textbox txtName, txtDescription;
    @Wire
    private Combobox cmbArea;
    @Wire
    private Window wEAARC;
    @WireVariable
    private BusinessService<DTO_Area> areaService;
    @WireVariable
    private BusinessService<DTO_Role> roleService;
    @WireVariable
    private Desktop desktop;
    private DTO_Company company;
    private PO_EAAdministrateRole roleParentUI;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        company = (DTO_Company) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        loadAreas(cmbArea);

        // Obtenemos el controlador de la pantalla principal de roles.
        final Map<?, ?> mapArg = desktop.getExecution().getArg();
        roleParentUI = (PO_EAAdministrateRole) mapArg.get(Constantes.ATTRIBUTE_PARENTFORM);
    }

    @Listen("onClick = #btnSave")
    public void createRole()
    {
        if (!txtDescription.getValue().isEmpty() && !txtName.getValue().isEmpty()
                        && cmbArea.getSelectedItem() != null) {
            final DTO_Role rolNew = new DTO_Role();

            rolNew.setRoleDescription(txtDescription.getValue());
            rolNew.setRoleName(txtName.getValue());
            rolNew.setAreaId(((DTO_Area) cmbArea.getSelectedItem().getAttribute(Constantes.ATTRIBUTE_AREA)).getId());
            rolNew.setCompanyId(company.getId());

            final ServiceInput<DTO_Role> input = new ServiceInput<DTO_Role>(rolNew);
            input.setAction(Constantes.V_REGISTER);
            final ServiceOutput<DTO_Role> output = roleService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                final int resp = alertaInfo(logger,
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleCreate.createRole.Info.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleCreate.createRole.Info.Label"),
                                null);
                if (resp == Messagebox.OK) {
                    roleParentUI.getRoles();
                }
            } else {
                alertaError(logger,
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleCreate.createRole.Error.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleCreate.createRole.Error.Label"),
                                null);
            }
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleCreate.createRole.Info2.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleCreate.createRole.Info2.Label"),
                            null);
        }
    }

    @Listen("onClick = #btnClose")
    public void close(final Event _event)
    {
        wEAARC.detach();
    }

    private void loadAreas(final Combobox combo)
    {
        final DTO_Area areaSe = new DTO_Area();
        areaSe.setCompanyId(company.getId());
        final ServiceInput<DTO_Area> input = new ServiceInput<DTO_Area>(areaSe);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput<DTO_Area> output = areaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Area> listado = output.getLista();
            for (final DTO_Area area : listado) {
                final Comboitem item = new Comboitem(area.getAreaName());
                item.setAttribute(Constantes.ATTRIBUTE_AREA, area);
                combo.appendChild(item);
            }
        } else {
            alertaError(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleCreate.loadAreas.Error.Label"),// "Error en la carga de areas",
                            "Error al cargar las areas", null);
        }
    }

    @Override
    String[] requiredResources()
    {
        return new String[] { Constantes.MODULE_ADM_ROLE };
    }
}
