package pe.com.jx_market.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Foot;
import org.zkoss.zul.Footer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import pe.com.jx_market.domain.DTO_Area;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Module;
import pe.com.jx_market.domain.DTO_Role;
import pe.com.jx_market.domain.DTO_RoleModule;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_EAAdministrateRoleModule
    extends SecuredWindow
{
    static Log logger = LogFactory.getLog(PO_EAAdministrateRoleModule.class);
    private Grid gr_recursos;
    private Button b_info, b_cancel, b_edit;
    private Foot f_buttons;
    private Combobox cmbArea;

    private BusinessService roleModuleService, areaService;
    private DTO_Company company;

    @Override
    public void realOnCreate()
    {
        gr_recursos = (Grid) getFellow("gr_recursos");
        b_info = (Button) getFellow("b_info");
        b_edit = (Button) getFellow("b_edit");
        b_cancel = (Button) getFellow("b_cancel");
        f_buttons = (Foot) getFellow("f_buttons");
        cmbArea = (Combobox) getFellow("cmbArea");
        roleModuleService = ContextLoader.getService(this, "roleModuleService");
        areaService = ContextLoader.getService(this, "areaService");
        company = (DTO_Company) getDesktop().getSession().getAttribute("company");
        cargarAreas(cmbArea);
        //CargarTabla();
    }

    public void editar()
    {
        for (int i = 0; i < gr_recursos.getRows().getChildren().size(); i++) {
            for (int j = 1; j < gr_recursos.getColumns().getChildren().size(); j++) {
                ((Checkbox) gr_recursos.getCell(i, j)).setDisabled(false);
                ((Button) ((Footer) gr_recursos.getFoot().getChildren().get(j)).getChildren().get(0)).setDisabled(false);
            }
        }
        b_edit.setVisible(false);
        b_info.setVisible(true);
        b_cancel.setVisible(true);

    }

    public void cancelar()
    {
        b_edit.setVisible(true);
        b_info.setVisible(false);
        b_cancel.setVisible(false);
        CargarTabla();
    }

    public void pintarColumna(final DTO_Role role)
    {
        for (int j = 1; j < gr_recursos.getColumns().getChildren().size(); j++) {
            if (((String) ((Column) gr_recursos.getColumns().getChildren().get(j)).getAttribute("role"))
                                                                                                    .equals(role)) {
                for (int i = 0; i < gr_recursos.getRows().getChildren().size(); i++) {
                    ((Checkbox) gr_recursos.getCell(i, j)).setChecked(true);
                }
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void CargarTabla()
    {
        if(gr_recursos.getPageCount() > 1) {
            gr_recursos.getPagingChild().getTotalSize();
            gr_recursos.getPagingChild().getPageCount();
            gr_recursos.getPagingChild().getPageIncrement();
            gr_recursos.getPagingChild().getPageSize();
            gr_recursos.getPagingChild().getActivePage();
            gr_recursos.getPagingChild().getChildren().clear();
        }
        gr_recursos.setActivePage(0);
        gr_recursos.getFoot().getChildren().clear();
        gr_recursos.getRows().getChildren().clear();
        gr_recursos.getColumns().getChildren().clear();

        Column columna = new Column();
        columna.appendChild(new Label("Modules"));

        Footer F = new Footer();
        F.appendChild(new Label(""));
        f_buttons.appendChild(F);
        gr_recursos.getColumns().appendChild(columna);

        final DTO_Module modL = new DTO_Module();
        modL.setCompanyId(company.getId());
        final DTO_Role perL = new DTO_Role();
        perL.setCompanyId(company.getId());
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("role", perL);
        map.put("module", modL);
        final ServiceInput input = new ServiceInput();
        input.setMapa(map);
        input.setAccion(Constantes.V_LIST);

        final ServiceOutput output = roleModuleService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Module> listaMod = output.getLista();
            for (final DTO_Module dto : listaMod) {
                final Row fila = new Row();
                fila.setAttribute("module", dto);
                fila.appendChild(new Label(dto.getModuleDescription()));
                gr_recursos.getRows().appendChild(fila);
                /*
                 * columna = new Column(); columna.setAttribute("codigo",
                 * dto.getCodigo()); columna.appendChild(new
                 * Label(dto.getDescripcion())); columna.setAlign("center");
                 * gr_recursos.getColumns().appendChild(columna);
                 */
            }

            final Map<DTO_Role, Set<Integer>> mapa = output.getMapa();
            final Iterator roleIterator = mapa.keySet().iterator();

            while (roleIterator.hasNext()) {
                final DTO_Role role = (DTO_Role) roleIterator.next();
                if(role.getAreaId().equals(((DTO_Area)cmbArea.getSelectedItem().getAttribute("area")).getId())) {
                    columna = new Column();
                    columna.setAttribute("role", role);
                    columna.appendChild(new Label(role.getRoleName()));
                    columna.setAlign("center");

                    final Set perfMod = mapa.get(role);
                    // verificar para todos los tipos de bloqueo:
                    int i = 0;
                    for (final DTO_Module dto : listaMod) {
                        final Checkbox C = new Checkbox();
                        C.setDisabled(true);
                        C.setAttribute("role", role);
                        if (perfMod.contains(dto.getId())) {
                            C.setChecked(true);
                        }
                        ((Row) gr_recursos.getRows().getChildren().get(i)).appendChild(C);
                        // columna.appendChild(C);
                        i++;
                    }
                    F = new Footer();

                    final Button todos = new Button("Todos");
                    todos.addEventListener("onClick",
                                    new org.zkoss.zk.ui.event.EventListener() {
                                        @Override
                                        public void onEvent(final Event e)
                                            throws UiException
                                        {
                                            final DTO_Role codigo = (DTO_Role) e.getTarget().getAttribute("role");
                                            pintarColumna(codigo);
                                        }
                                    });
                    todos.setDisabled(true);
                    todos.setAttribute("role", role);
                    F.appendChild(todos);
                    f_buttons.appendChild(F);
                    gr_recursos.getColumns().appendChild(columna);
                }
            }
        } else {
            alertaError("Error al cargar los modules por role", "Error al cargar los modules por role", null);
        }
    }

    public void actualizar()
    {
        final HashMap<DTO_Role, Set<DTO_RoleModule>> mapa = new HashMap<DTO_Role, Set<DTO_RoleModule>>();

        for (int j = 1; j < gr_recursos.getColumns().getChildren().size(); j++) {
            final DTO_Role role = ((DTO_Role) ((Column) gr_recursos.getColumns().getChildren().get(j))
                                                                                            .getAttribute("role"));
            final Set<DTO_RoleModule> estados = new HashSet<DTO_RoleModule>();
            for (int i = 0; i < gr_recursos.getRows().getChildren().size(); i++) {
                if (((Checkbox) gr_recursos.getCell(i, j)).isChecked()) {
                    final DTO_Module module = ((DTO_Module) ((Row) gr_recursos.getRows().getChildren().get(i))
                                                                                            .getAttribute("module"));
                    final DTO_RoleModule perfMod = new DTO_RoleModule();
                    perfMod.setModuleId(module.getId());
                    perfMod.setRole(role.getId());
                    estados.add(perfMod);
                }
            }
            System.out.println("ESTADOS:" + estados);
            System.out.println("ROLE :" + role);
            mapa.put(role, estados);

        }

        final ServiceInput input = new ServiceInput();
        input.setAccion(Constantes.V_REGISTER);
        input.setMapa(mapa);
        final ServiceOutput output = roleModuleService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            CargarTabla();
            b_edit.setVisible(true);
            b_info.setVisible(false);
            b_cancel.setVisible(false);
            logger.info("Recursos registrados correctamente");
            alertaInfo("", "Los cambios se guardaron correctamente", null);
        } else {
            logger.error("Error al registrar bloqueos");
        }
    }

    private void cargarAreas(final Combobox combo)
    {
        final DTO_Area ar = new DTO_Area();
        ar.setCompanyId(company.getId());
        final ServiceInput input = new ServiceInput(ar);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput output = areaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Area> listado = output.getLista();
            for (final DTO_Area area : listado) {
                final Comboitem item = new Comboitem(area.getAreaName());
                item.setAttribute("area", area);
                combo.appendChild(item);
            }
        } else {
            alertaError("Error en la carga de areas",
                            "error al cargar los areas", null);
        }
    }

    public void alertaInfo(final String txt,
                           final String txt2,
                           final Throwable t)
    {
        if (txt.length() > 0) {
            Messagebox.show(txt, company.getBusinessName(), 1, Messagebox.INFORMATION);
        }
        if (t != null) {
            logger.info(txt2, t);
        } else {
            logger.info(txt2);
        }
    }

    public void alertaError(final String txt,
                            final String txt2,
                            final Throwable t)
    {
        if (txt.length() > 0) {
            Messagebox.show(txt, company.getBusinessName(), 1, Messagebox.EXCLAMATION);
        }
        if (t != null) {
            logger.error(txt2, t);
        } else {
            logger.error(txt2);
        }
    }

    @Override
    String[] requiredResources()
    {
        return new String[]{Constantes.MODULE_ADM_ROLEMODULE };
    }
}
