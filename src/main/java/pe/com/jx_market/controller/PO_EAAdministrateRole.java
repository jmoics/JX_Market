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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Role;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

@VariableResolver(DelegatingVariableResolver.class)
public class PO_EAAdministrateRole
    extends SecuredComposer<Window>
{
    static Log logger = LogFactory.getLog(PO_EAAdministrateRole.class);
    @Wire
    private Listbox lstRole;
    @Wire
    private Window wEAAR;
    @WireVariable
    private BusinessService<DTO_Role> roleService;
    @WireVariable
    private Desktop desktop;
    private DTO_Company company;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        company = (DTO_Company) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);
        getRoles();
    }

    public void getRoles()
    {
        lstRole.getItems().clear();
        final DTO_Role roleSe = new DTO_Role();
        roleSe.setCompanyId(company.getId());
        final ServiceInput<DTO_Role> input = new ServiceInput<DTO_Role>(roleSe);

        input.setAction(Constantes.V_LIST);
        final ServiceOutput<DTO_Role> output = roleService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Role> roleLst = output.getLista();
            int columnNumber = 1;
            for (final DTO_Role role : roleLst) {
                final Listitem item = new Listitem();
                Listcell cell = new Listcell("" + columnNumber);
                item.appendChild(cell);
                cell = new Listcell(role.getArea().getAreaName());
                item.appendChild(cell);
                cell = new Listcell(role.getRoleName());
                item.appendChild(cell);
                cell = new Listcell(role.getRoleDescription());
                item.appendChild(cell);
                item.setAttribute(Constantes.ATTRIBUTE_ROLE, role);
                item.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>()
                {
                    @Override
                    public void onEvent(final Event e)
                        throws UiException
                    {
                        runWindowEdit((MouseEvent) e);
                    }
                });
                lstRole.appendChild(item);
                columnNumber++;
            }
        } else {
            alertaError(logger, "Error al cargar roles", "Error al cargar roles", null);
        }
    }

    @Listen("onClick = #btnEdit, #btnView")
    public void runWindowEdit(final MouseEvent event) {
        if (lstRole.getSelectedItem() != null) {
            desktop.getSession().setAttribute(Constantes.ATTRIBUTE_ROLE,
                            lstRole.getSelectedItem().getAttribute(Constantes.ATTRIBUTE_ROLE));
            final Map<String, Object> dataArgs = new HashMap<String, Object>();
            dataArgs.put(Constantes.ATTRIBUTE_PARENTFORM, this);
            final Window w = (Window) Executions.createComponents(Constantes.Form.ROLE_EDIT_FORM.getForm(),
                            null, dataArgs);
            w.setPage(wEAAR.getPage());
            //w.setParent(wEAT);
            w.doOverlapped();
            //w.doHighlighted();
            //w.doEmbedded();
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRole.runWindowEdit.Info.Label"),
                                "No se selecciono un registro a editar", null);
        }
    }

    @Listen("onClick = #btnCreate")
    public void runWindowCreate(final MouseEvent event) {
        final Map<String, Object> dataArgs = new HashMap<String, Object>();
        dataArgs.put(Constantes.ATTRIBUTE_PARENTFORM, this);
        final Window w = (Window) Executions.createComponents(Constantes.Form.ROLE_CREATE_FORM.getForm(),
                            null, dataArgs);
        w.setPage(wEAAR.getPage());
        //w.setParent(wEAT);
        //w.doOverlapped();
        w.doModal();
        //w.doEmbedded();
    }

    @Listen("onClick = #btnDelete")
    public void deleteRole(final MouseEvent event)
        throws UiException
    {
        if (lstRole.getSelectedItem() != null) {
            final int verifyDelete = Messagebox.show(
                            Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRole.deleteRole.Info3.Label"),
                            company.getBusinessName(), Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
            if (Messagebox.OK == verifyDelete) {
                final DTO_Role role = (DTO_Role) lstRole.getSelectedItem().getAttribute(Constantes.ATTRIBUTE_ROLE);
                final ServiceInput<DTO_Role> input = new ServiceInput<DTO_Role>(role);
                input.setAction(Constantes.V_DELETE);
                final ServiceOutput<DTO_Role> output = roleService.execute(input);
                if (output.getErrorCode() == Constantes.OK) {
                    alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRole.deleteRole.Info.Label"),
                                    "El rol se elimino correctamente", null);
                    getRoles();
                } else {
                    alertaError(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRole.deleteRole.Error.Label"),
                                    "Error al eliminar el rol", null);
                }
            }
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAAdministrateRole.deleteRole.Info2.Label"),
                            "No se selecciono un registro a eliminar", null);
        }
    }

    @Override
    String[] requiredResources()
    {
        return new String[] { Constantes.MODULE_ADM_ROLE };
    }
}
