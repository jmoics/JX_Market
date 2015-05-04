package pe.com.jx_market.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Area;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Module;
import pe.com.jx_market.domain.DTO_Role;
import pe.com.jx_market.domain.DTO_RoleModule;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.BusinessServiceConnection;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceInputConnection;
import pe.com.jx_market.utilities.ServiceOutput;
import pe.com.jx_market.utilities.ServiceOutputConnection;

public class PO_EAAdministrateRoleModule
    extends SecuredComposer<Window>
{
    static Log logger = LogFactory.getLog(PO_EAAdministrateRoleModule.class);
    @Wire
    private Combobox cmbArea;
    @Wire
    private Listbox lstRoleModule;
    @Wire
    private Chosenbox chbRole;
    @WireVariable
    private BusinessService<DTO_Area> areaService;
    @WireVariable
    private BusinessServiceConnection<DTO_RoleModule, DTO_Role, DTO_Module> roleModuleService;
    @WireVariable
    private Desktop desktop;
    private DTO_Company company;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);
        company = (DTO_Company) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        cargarAreas(cmbArea);
        //CargarTabla();
    }

    @Listen("onClick = #btnSearch")
    public void searchProducts()
    {
        lstRoleModule.getHeads().clear();
        lstRoleModule.getItems().clear();
        if (cmbArea.getSelectedItem() != null) {
            final ServiceInputConnection<DTO_RoleModule, DTO_Role, DTO_Module> input =
                            new ServiceInputConnection<DTO_RoleModule, DTO_Role, DTO_Module>();
            input.setAction(Constantes.V_LIST);
            final DTO_Module moduSe = new DTO_Module();
            moduSe.setCompanyId(company.getId());
            input.setObjectTo(moduSe);
            final Integer areaId = ((DTO_Area) cmbArea.getSelectedItem().getAttribute(Constantes.ATTRIBUTE_AREA)).getId();
            input.addMapPair("companyId", company.getId());
            input.addMapPair("areaId", areaId);
            final List<Integer> lstRolId = new ArrayList<Integer>();
            if (chbRole.getSelectedObjects() != null && !chbRole.getSelectedObjects().isEmpty()) {
                final Set<DTO_Role> setRol = chbRole.getSelectedObjects();
                for (final DTO_Role rol : setRol) {
                    lstRolId.add(rol.getId());
                }
                input.addMapPair("lstRoles", lstRolId);
            }

            final ServiceOutputConnection<DTO_RoleModule, DTO_Role, DTO_Module> output = roleModuleService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                final List<DTO_Module> modules = output.getResultListTo();
                final List<DTO_Role> roles = output.getResultListFrom();
                for (int i=0; i<roles.size(); i++) {
                    final Listheader lstHeader = new Listheader();
                    lstHeader.setLabel(roles.get(i).getRoleName());
                    lstRoleModule.getListhead().appendChild(lstHeader);
                }
                int columnNumber = 1;
                for (final DTO_Module modu : modules) {
                    final Listitem item = new Listitem();
                    Listcell cell = new Listcell("" + columnNumber);
                    item.appendChild(cell);
                    cell = new Listcell(modu.getModuleResource());
                    item.appendChild(cell);
                    for (final DTO_Role role : roles) {
                        final Set<DTO_Module> setModules = new HashSet<DTO_Module>(role.getModules());
                        if (setModules.contains(modu)) {
                            cell = new Listcell("Active");
                        } else {
                            cell = new Listcell("Inactive");
                        }
                        item.appendChild(cell);
                    }

                    lstRoleModule.appendChild(item);
                    columnNumber++;
                }
            }
        }
    }

    private void cargarAreas(final Combobox combo)
    {
        final DTO_Area ar = new DTO_Area();
        ar.setCompanyId(company.getId());
        final ServiceInput<DTO_Area> input = new ServiceInput<DTO_Area>(ar);
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
            alertaError(logger, "Error en la carga de areas",
                            "error al cargar los areas", null);
        }
    }

    @Override
    String[] requiredResources()
    {
        return new String[]{Constantes.MODULE_ADM_ROLEMODULE };
    }
}
