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

public class PO_EAAdministrateRoleEdit
    extends SecuredComposer<Window>
{

    static Log logger = LogFactory.getLog(PO_EAAdministrateRoleEdit.class);
    @Wire
    private Textbox txtName, txtDescription;
    @Wire
    private Combobox cmbArea;
    @Wire
    private Window wEAARE;
    @WireVariable
    private BusinessService<DTO_Area> areaService;
    @WireVariable
    private BusinessService<DTO_Role> roleService;
    @WireVariable
    private Desktop desktop;
    private DTO_Company company;
    private DTO_Role role;
    private PO_EAAdministrateRole roleParentUI;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        company = (DTO_Company) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        role = (DTO_Role) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_ROLE);
        if (role == null) {
            alertaInfo(logger, "", "No se encontro role, retornando a busqueda", null);
        } else {
            // Obtenemos el controlador de la pantalla principal de marcas.
            final Map<?, ?> mapArg = desktop.getExecution().getArg();
            roleParentUI = (PO_EAAdministrateRole) mapArg.get(Constantes.ATTRIBUTE_PARENTFORM);
            loadData();
        }
    }

    private void loadData()
    {
        txtName.setValue(role.getRoleName());
        txtDescription.setValue(role.getRoleDescription());
        loadAreas();
    }

    private void loadAreas()
    {
        final DTO_Area areaSe = new DTO_Area();
        areaSe.setCompanyId(company.getId());
        final ServiceInput<DTO_Area> input = new ServiceInput<DTO_Area>(areaSe);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput<DTO_Area> output = areaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Area> listado = output.getLista();
            for (final DTO_Area area : listado) {
                final Comboitem item = new Comboitem(area.getAreaName());
                item.setAttribute(Constantes.ATTRIBUTE_AREA, area);
                cmbArea.appendChild(item);
                if (role.getArea().getId().equals(area.getId())) {
                    cmbArea.setSelectedItem(item);
                }
            }
        } else {
            alertaError(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleEdit.loadAreas.Error.Label"),// "Error en la carga de areas",
                            "Error al cargar las areas", null);
        }
    }

    @Listen("onClick = #btnSave")
    public void editRole()
    {
        // row1.setVisible(true);
        if (!txtDescription.getValue().isEmpty() && !txtName.getValue().isEmpty()
                        && cmbArea.getSelectedItem() != null) {
            role.setRoleDescription(txtDescription.getValue());
            role.setRoleName(txtName.getValue());
            role.setAreaId(((DTO_Area) cmbArea.getSelectedItem().getAttribute(Constantes.ATTRIBUTE_AREA)).getId());

            final ServiceInput<DTO_Role> input = new ServiceInput<DTO_Role>(role);
            input.setAccion(Constantes.V_REGISTER);
            final ServiceOutput<DTO_Role> output = roleService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                final int resp = alertaInfo(logger,
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleEdit.editRole.Info.Label",
                                                new Object[] { role.getRoleName() }),
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleEdit.editRole.Info.Label",
                                                new Object[] { role.getRoleName() }), null);
                if (resp == Messagebox.OK) {
                    roleParentUI.getRoles();
                }
            } else {
                alertaError(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleEdit.editRole.Error.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleEdit.editRole.Error.Label"),
                                null);
            }
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleEdit.editRole.Info2.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleEdit.editRole.Info2.Label"), null);
        }
    }

    @Listen("onClick = #btnClose")
    public void close(final Event _event)
    {
        wEAARE.detach();
    }

    @Override
    String[] requiredResources()
    {
        return new String[] { Constantes.MODULE_ADM_ROLE };
    }
}
