package pe.com.jx_market.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelList;
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
    @Wire
    private Window wEARM;
    @WireVariable
    private BusinessService<DTO_Area> areaService;
    @WireVariable
    private BusinessService<DTO_Role> roleService;
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
        loadAreas(cmbArea);
        // CargarTabla();
    }

    @Listen("onClick = #btnSearch")
    public void searchProducts()
    {
        lstRoleModule.getListhead().getChildren().clear();
        lstRoleModule.getItems().clear();
        Listheader lstHeader = new Listheader();
        lstHeader.setWidth("30px");
        lstHeader.setLabel(Labels.getLabel("pe.com.jx_market.eAAdministrateRoleModule.Number.Label"));
        lstRoleModule.getListhead().appendChild(lstHeader);
        lstHeader = new Listheader();
        lstHeader.setSort("auto");
        lstHeader.setHflex("2");
        lstHeader.setLabel(Labels.getLabel("pe.com.jx_market.eAAdministrateRoleModule.Resource.Label"));
        lstRoleModule.getListhead().appendChild(lstHeader);
        if (cmbArea.getSelectedItem() != null) {
            final ServiceInputConnection<DTO_RoleModule, DTO_Role, DTO_Module> input =
                            new ServiceInputConnection<DTO_RoleModule, DTO_Role, DTO_Module>();
            input.setAction(Constantes.V_LIST);
            final DTO_Module moduSe = new DTO_Module();
            moduSe.setCompanyId(company.getId());
            input.setObjectTo(moduSe);
            final Integer areaId = ((DTO_Area) cmbArea.getSelectedItem().getAttribute(Constantes.ATTRIBUTE_AREA))
                            .getId();
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

            final ServiceOutputConnection<DTO_RoleModule, DTO_Role, DTO_Module> output = roleModuleService
                            .execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                final List<DTO_Module> modules = output.getResultListTo();
                final List<DTO_Role> roles = output.getResultListFrom();
                desktop.getSession().setAttribute(Constantes.ATTRIBUTE_ROLEMODULE, roles);
                desktop.getSession().setAttribute(Constantes.ATTRIBUTE_MODULE, modules);
                for (int i = 0; i < roles.size(); i++) {
                    lstHeader = new Listheader();
                    lstHeader.setLabel(roles.get(i).getRoleName());
                    lstHeader.setHflex("2");
                    lstRoleModule.getListhead().appendChild(lstHeader);
                }
                int columnNumber = 1;
                for (final DTO_Module modu : modules) {
                    final Listitem item = new Listitem();
                    Listcell cell = new Listcell("" + columnNumber);
                    item.appendChild(cell);
                    cell = new Listcell(modu.getModuleDescription());
                    item.appendChild(cell);
                    for (final DTO_Role role : roles) {
                        final Set<Integer> setModules = new HashSet<Integer>();
                        for (final DTO_Module mo : role.getModules()) {
                            setModules.add(mo.getId());
                        }
                        if (setModules.contains(modu.getId())) {
                            cell = new Listcell(
                                            Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleModule.searchProducts.Active"));
                        } else {
                            cell = new Listcell(
                                            Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleModule.searchProducts.Inactive"));
                        }
                        item.appendChild(cell);
                    }

                    lstRoleModule.appendChild(item);
                    columnNumber++;
                }
            }
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleModule.searchProducts.Info.Label"),
                        Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleModule.searchProducts.Info.Label"), null);
        }
    }

    private void loadAreas(final Combobox combo)
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
            combo.addEventListener(Events.ON_SELECT, new EventListener<Event>()
            {
                @Override
                public void onEvent(final Event e)
                    throws UiException
                {
                    final DTO_Area area = (DTO_Area) ((Combobox) e.getTarget()).getSelectedItem()
                                        .getAttribute(Constantes.ATTRIBUTE_AREA);
                    loadRoles(area.getId());
                }
            });
        } else {
            alertaError(logger, "Error en la carga de areas",
                            "error al cargar los areas", null);
        }
    }

    private void loadRoles(final Integer _areaId)
    {
        final DTO_Role rolSe = new DTO_Role();
        rolSe.setCompanyId(company.getId());
        rolSe.setAreaId(_areaId);
        final ServiceInput<DTO_Role> input = new ServiceInput<DTO_Role>(rolSe);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput<DTO_Role> output = roleService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Role> lstRoles = output.getLista();
            final ListModelList<DTO_Role> modelRole = new ListModelList<DTO_Role>(lstRoles);
            chbRole.setModel(modelRole);
        }
    }

    @Listen("onClick = #btnClean")
    public void cleanSearch()
    {
        chbRole.clearSelection();
        cmbArea.setSelectedItem(null);
        cmbArea.setValue(null);
    }

    @Listen("onClick = #btnEdit")
    public void runWindowEdit(final MouseEvent event) {
        if (desktop.getSession().getAttribute(Constantes.ATTRIBUTE_ROLEMODULE) != null
                        && desktop.getSession().getAttribute(Constantes.ATTRIBUTE_MODULE) != null) {
            final Map<String, Object> dataArgs = new HashMap<String, Object>();
            dataArgs.put(Constantes.ATTRIBUTE_PARENTFORM, this);
            final Window w = (Window) Executions.createComponents(Constantes.Form.ROLEMODULE_EDIT_FORM.getForm(),
                            null, dataArgs);
            w.setPage(wEARM.getPage());
            //w.setParent(wEAT);
            w.doModal();
            //w.doHighlighted();
            //w.doEmbedded();
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleModule.runWindowEdit.Info.Label"),
                                "No se selecciono un registro a editar", null);
        }
    }

    @Override
    String[] requiredResources()
    {
        return new String[] { Constantes.MODULE_ADM_ROLEMODULE };
    }
}
