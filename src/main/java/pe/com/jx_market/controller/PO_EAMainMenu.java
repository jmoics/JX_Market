/**
 *
 */
package pe.com.jx_market.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Borderlayout;

import pe.com.jx_market.domain.DTO_User;
import pe.com.jx_market.utilities.Constantes;

/**
 * @author George
 *
 */
public class PO_EAMainMenu
    extends SecuredComposer<Borderlayout>
{

    private static final long serialVersionUID = -3447722983065558278L;

    /**
     * Mapeo de paginas a redirigir de acuerdo al ID del link en el menu.
     */
    public static final Map<String, String> MENU_MAP = new HashMap<String, String>();
    {
        PO_EAMainMenu.MENU_MAP.put("id_option_prod_categ", "eACategory.zul");
        PO_EAMainMenu.MENU_MAP.put("id_option_prod_products", "eAProducts.zul");
        PO_EAMainMenu.MENU_MAP.put("id_option_prod_tradeMark", "eATradeMark.zul");
        PO_EAMainMenu.MENU_MAP.put("id_option_prod_amount", "");
        PO_EAMainMenu.MENU_MAP.put("id_option_prod_inventory", "");
        PO_EAMainMenu.MENU_MAP.put("id_option_adm_areas", "eAAdministrateArea.zul");
        PO_EAMainMenu.MENU_MAP.put("id_option_adm_emp", "eAAdministrateEmployee.zul");
        PO_EAMainMenu.MENU_MAP.put("id_option_adm_perf", "eAAdministrateRole.zul");
        PO_EAMainMenu.MENU_MAP.put("id_option_adm_mod", "eAAdministrateModule.zul");
        PO_EAMainMenu.MENU_MAP.put("id_option_adm_perfi_module", "eAAdministrateRoleModule.zul");
        PO_EAMainMenu.MENU_MAP.put("id_option_chgpass", "eACambiarContrasenia.zul");
    }

    static Log logger = LogFactory.getLog(PO_EAMainMenu.class);

    @WireVariable
    private Desktop desktop;

    @Override
    public void doBeforeComposeChildren(final Borderlayout comp)
        throws Exception
    {
        super.doBeforeComposeChildren(comp);
    }

    @Override
    public void doAfterCompose(final Borderlayout comp)
        throws Exception
    {
        super.doAfterCompose(comp);
        incluir(Constantes.Form.EMPTY_FORM.getForm());

        final DTO_User user = (DTO_User) comp.getDesktop().getSession().getAttribute(Constantes.ATTRIBUTE_USER);

        if (user == null) {
            throw new RuntimeException("La sesion se perdio, vuelva a ingresar por favor");
        }

        if (comp.getDesktop().getSession().getAttribute("actualizar") == null) {
            incluir(Constantes.Form.EMPTY_FORM.getForm());
        }

        getPage().addEventListener(Events.ON_BOOKMARK_CHANGE, new EventListener<Event>()
        {
            @Override
            public void onEvent(final Event arg0)
                throws Exception
            {
                try {
                    incluir(comp.getDesktop().getBookmark());
                } catch (final org.zkoss.zk.ui.ComponentNotFoundException ex) {
                    incluir(Constantes.Form.EMPTY_FORM.getForm());
                }
            }
        });

        setVisibilityByResource(comp, "id_option_prod_products", Constantes.MODULE_PROD_PRODUCT, user);
        setVisibilityByResource(comp, "id_option_prod_categ", Constantes.MODULE_PROD_CATEGORY, user);
        setVisibilityByResource(comp, "id_option_prod_tradeMark", Constantes.MODULE_PROD_TRADEMARK, user);
        setVisibilityByResource(comp, "id_option_prod_amount", Constantes.MODULE_PROD_AMOUNT, user);
        setVisibilityByResource(comp, "id_option_prod_inventory", Constantes.MODULE_PROD_INVENTORY, user);
        setVisibilityByResource(comp, "id_option_administracion", Constantes.MODULE_ADMINISTRACION, user);
        setVisibilityByResource(comp, "id_option_adm_areas", Constantes.MODULE_ADM_AREA, user);
        setVisibilityByResource(comp, "id_option_adm_emp", Constantes.MODULE_ADM_EMPLOYEE, user);
        setVisibilityByResource(comp, "id_option_adm_mod", Constantes.MODULE_ADM_MODULE, user);
        setVisibilityByResource(comp, "id_option_adm_perf", Constantes.MODULE_ADM_ROLE, user);
        setVisibilityByResource(comp, "id_option_adm_perfi_module", Constantes.MODULE_ADM_ROLEMODULE, user);
        setVisibilityByResource(comp, "id_option_chgpass", Constantes.MODULE_CHANGE_PASS, user);
    }

    @Listen("onClick = #id_option_prod_products, #id_option_prod_categ, #id_option_prod_amount, "
                    + "#id_option_prod_inventory, #id_option_prod_tradeMark, "
                    + "#id_option_adm_areas, #id_option_adm_emp, #id_option_adm_perf, "
                    + "#id_option_adm_mod, #id_option_adm_perfi_module, #id_option_chgpass, "
                    + "#id_option_categ")
    public void incluir(final MouseEvent event)
    {
        // getDesktop().getSession().setAttribute("paginaActual", txt);
        desktop.getSession().setAttribute("actualizar", "actualizar");
        ContextLoader.saltar(desktop, PO_EAMainMenu.MENU_MAP.get(event.getTarget().getId()));
    }

    public void incluir(final String _txt)
    {
        // getDesktop().getSession().setAttribute("paginaActual", txt);
        desktop.getSession().setAttribute("actualizar", "actualizar");
        ContextLoader.saltar(desktop, _txt);
    }

    @Override
    String[] requiredResources()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
