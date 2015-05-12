package pe.com.jx_market.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Foot;
import org.zkoss.zul.Footer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Area;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Module;
import pe.com.jx_market.domain.DTO_Role;
import pe.com.jx_market.domain.DTO_RoleModule;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.BusinessServiceConnection;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInputConnection;
import pe.com.jx_market.utilities.ServiceOutputConnection;

public class PO_EAAdministrateRoleModuleEdit
    extends SecuredComposer<Window>
{
    static Log logger = LogFactory.getLog(PO_EAAdministrateRoleModuleEdit.class);
    @Wire
    private Grid grdRoleModule;
    @Wire
    private Foot f_buttons;
    @Wire
    private Window wEARME;
    @WireVariable
    private BusinessServiceConnection<DTO_RoleModule, DTO_Role, DTO_Module> roleModuleService;
    @WireVariable
    private BusinessService<DTO_Area> areaService;
    @WireVariable
    private Desktop desktop;
    private DTO_Company company;
    List<DTO_Role> roles;
    List<DTO_Module> modules;
    PO_EAAdministrateRoleModule roleModuleParentUI;

    @SuppressWarnings("unchecked")
    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);
        company = (DTO_Company) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);

        roles = (List<DTO_Role>) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_ROLEMODULE);
        modules = (List<DTO_Module>) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_MODULE);

        if (roles == null || modules == null) {
            alertaInfo(logger, "", "No se encontro role, retornando a busqueda", null);
        } else {
            // Obtenemos el controlador de la pantalla principal de marcas.
            final Map<?, ?> mapArg = desktop.getExecution().getArg();
            roleModuleParentUI = (PO_EAAdministrateRoleModule) mapArg.get(Constantes.ATTRIBUTE_PARENTFORM);
            loadData();
        }
    }

    public void printColumn(final DTO_Role role)
    {
        for (int j = 1; j < grdRoleModule.getColumns().getChildren().size(); j++) {
            if (((String) ((Column) grdRoleModule.getColumns().getChildren().get(j))
                            .getAttribute(Constantes.ATTRIBUTE_ROLE)).equals(role)) {
                for (int i = 0; i < grdRoleModule.getRows().getChildren().size(); i++) {
                    ((Checkbox) grdRoleModule.getCell(i, j)).setChecked(true);
                }
            }
        }
    }

    public void loadData()
    {
        if(grdRoleModule.getPageCount() > 1) {
            grdRoleModule.getPagingChild().getTotalSize();
            grdRoleModule.getPagingChild().getPageCount();
            grdRoleModule.getPagingChild().getPageIncrement();
            grdRoleModule.getPagingChild().getPageSize();
            grdRoleModule.getPagingChild().getActivePage();
            grdRoleModule.getPagingChild().getChildren().clear();
        }
        grdRoleModule.setActivePage(0);
        grdRoleModule.getFoot().getChildren().clear();
        grdRoleModule.getRows().getChildren().clear();
        grdRoleModule.getColumns().getChildren().clear();

        Column column = new Column();
        column.setHflex("3");
        column.appendChild(new Label(Labels
                        .getLabel("pe.com.jx_market.PO_EAAdministrateRoleModuleEdit.loadData.Modules.Label")));

        Footer footer = new Footer();
        footer.appendChild(new Label(""));
        f_buttons.appendChild(footer);
        grdRoleModule.getColumns().appendChild(column);

        final List<DTO_Module> listaMod = modules;
        for (final DTO_Module dto : listaMod) {
            final Row fila = new Row();
            fila.setAttribute(Constantes.ATTRIBUTE_MODULE, dto);
            fila.appendChild(new Label(dto.getModuleDescription()));
            grdRoleModule.getRows().appendChild(fila);
            /*
             * columna = new Column(); columna.setAttribute("codigo",
             * dto.getCodigo()); columna.appendChild(new
             * Label(dto.getDescripcion())); columna.setAlign("center");
             * grdRoleModule.getColumns().appendChild(columna);
             */
        }

        final Iterator<DTO_Role> roleIterator = roles.iterator();

        while (roleIterator.hasNext()) {
            final DTO_Role role = roleIterator.next();

            column = new Column();
            column.setHflex("1");
            column.setAttribute(Constantes.ATTRIBUTE_ROLE, role);
            column.appendChild(new Label(role.getRoleName()));
            column.setAlign("center");

            final Set<Integer> setModules = new HashSet<Integer>();
            for (final DTO_Module mo : role.getModules()) {
                setModules.add(mo.getId());
            }
            // verificar para todos los tipos de bloqueo:
            int i = 0;
            for (final DTO_Module modu : listaMod) {
                final Checkbox check = new Checkbox();
                //check.setDisabled(true);
                check.setAttribute(Constantes.ATTRIBUTE_ROLE, role);
                if (setModules.contains(modu.getId())) {
                    check.setChecked(true);
                }
                ((Row) grdRoleModule.getRows().getChildren().get(i)).appendChild(check);
                // columna.appendChild(C);
                i++;
            }
            footer = new Footer();

            final Button selectAll = new Button(Labels
                            .getLabel("pe.com.jx_market.PO_EAAdministrateRoleModuleEdit.loadData.All.Label"));
            selectAll.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                @Override
                public void onEvent(final Event e)
                    throws UiException
                {
                    final DTO_Role codigo = (DTO_Role) e.getTarget().getAttribute(Constantes.ATTRIBUTE_ROLE);
                    printColumn(codigo);
                }
            });
            selectAll.setAttribute(Constantes.ATTRIBUTE_ROLE, role);
            footer.appendChild(selectAll);
            f_buttons.appendChild(footer);
            grdRoleModule.getColumns().appendChild(column);
        }

    }

    @Listen("onClick = #btnSave")
    public void editRoleModule()
    {
        final HashMap<DTO_Role, Set<DTO_RoleModule>> mapa = new HashMap<DTO_Role, Set<DTO_RoleModule>>();

        for (int j = 1; j < grdRoleModule.getColumns().getChildren().size(); j++) {
            final DTO_Role role = ((DTO_Role) ((Column) grdRoleModule.getColumns().getChildren().get(j))
                                                                            .getAttribute(Constantes.ATTRIBUTE_ROLE));
            final Set<DTO_RoleModule> estados = new HashSet<DTO_RoleModule>();
            for (int i = 0; i < grdRoleModule.getRows().getChildren().size(); i++) {
                if (((Checkbox) grdRoleModule.getCell(i, j)).isChecked()) {
                    final DTO_Module module = ((DTO_Module) ((Row) grdRoleModule.getRows().getChildren().get(i))
                                                                            .getAttribute(Constantes.ATTRIBUTE_MODULE));
                    final DTO_RoleModule perfMod = new DTO_RoleModule();
                    perfMod.setModuleId(module.getId());
                    perfMod.setRoleId(role.getId());
                    estados.add(perfMod);
                }
            }
            logger.debug("ESTADOS:" + estados);
            logger.debug("ROLE :" + role);
            mapa.put(role, estados);

        }

        final ServiceInputConnection<DTO_RoleModule, DTO_Role, DTO_Module> input =
                        new ServiceInputConnection<DTO_RoleModule, DTO_Role, DTO_Module>();
        input.setAction(Constantes.V_REGISTER);
        input.setMapa(mapa);
        final ServiceOutputConnection<DTO_RoleModule, DTO_Role, DTO_Module> output = roleModuleService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            //loadData();
            final int resp = alertaInfo(logger,
                        Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleModuleEdit.editRoleModule.Info.Label"),
                        Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleModuleEdit.editRoleModule.Info.Label"),
                        null);
            if (resp == Messagebox.OK) {
                roleModuleParentUI.searchProducts();;
            }
            alertaInfo(logger, "", "Los cambios se guardaron correctamente", null);
        } else {
            alertaError(logger,
                        Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRoleModuleEdit.editRoleModule.Error.Label"),
                        "Error al registrar modulos por roles", null);
        }
    }

    @Listen("onClick = #btnClose")
    public void close(final Event _event)
    {
        wEARME.detach();
    }

        @Override
    String[] requiredResources()
    {
        return new String[]{Constantes.MODULE_ADM_ROLEMODULE };
    }
}
