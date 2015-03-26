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
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Borderlayout;

import pe.com.jx_market.domain.DTO_Empleado;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * @author George
 *
 */
public class PO_EAMainMenu extends SelectorComposer<Borderlayout>
{
    private static final long serialVersionUID = -3447722983065558278L;

    /**
     * Mapeo de paginas a redirigir de acuerdo al ID del link en el menu.
     */
    public static final Map<String, String> MAPA_MENU = new HashMap<String, String>();
    {
        PO_EAMainMenu.MAPA_MENU.put("id_option_prod_categ", "eACategory.zul");
        PO_EAMainMenu.MAPA_MENU.put("id_option_prod_products", "eAIngresaProducto.zul");
        PO_EAMainMenu.MAPA_MENU.put("id_option_prod_amount", "");
        PO_EAMainMenu.MAPA_MENU.put("id_option_prod_inventory","");
        PO_EAMainMenu.MAPA_MENU.put("id_option_adm_areas", "eAAdministraArea.zul");
        PO_EAMainMenu.MAPA_MENU.put("id_option_adm_emp", "eAAdministraEmpleado.zul");
        PO_EAMainMenu.MAPA_MENU.put("id_option_adm_perf", "eAAdministraPerfil.zul");
        PO_EAMainMenu.MAPA_MENU.put("id_option_adm_mod", "eAAdministraModulo.zul");
        PO_EAMainMenu.MAPA_MENU.put("id_option_adm_perfi_modulo", "eAAdministraPerfilModulo.zul");
        PO_EAMainMenu.MAPA_MENU.put("id_option_chgpass", "eACambiarContrasenia.zul");
    }

    static Log logger = LogFactory.getLog(PO_EAMainMenu.class);

    @WireVariable
    private Desktop desktop;
    private BusinessService<DTO_Empleado> autorizacionService;

    @Override
    public void doBeforeComposeChildren(final Borderlayout comp)
        throws Exception
    {
        super.doBeforeComposeChildren(comp);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doAfterCompose(final Borderlayout comp)
        throws Exception
    {
        super.doAfterCompose(comp);
        autorizacionService = (BusinessService<DTO_Empleado>) ContextLoader.getService(comp, "autorizacionService");
        incluir("eAFondo.zul");

        final DTO_Empleado empleado = (DTO_Empleado) comp.getDesktop().getSession().getAttribute("empleado");

        if (empleado == null) {
            throw new RuntimeException("La sesion se perdio, vuelva a ingresar por favor");
        }

        if(comp.getDesktop().getSession().getAttribute("actualizar") == null){
            incluir("eAFondo.zul");
        }

        getPage().addEventListener(Events.ON_BOOKMARK_CHANGE,
                        new EventListener<Event>() {
                            @Override
                            public void onEvent(final Event arg0) throws Exception {
                                try {
                                    incluir(comp.getDesktop().getBookmark());
                                } catch (final org.zkoss.zk.ui.ComponentNotFoundException ex) {
                                    incluir("eAFondo.zul");
                                }
                            }
                      });

        setVisibilityByResource(comp, "id_option_prod_products", Constantes.MODULO_PROD_PRODUCT, empleado);
        setVisibilityByResource(comp, "id_option_prod_categ", Constantes.MODULO_PROD_CATEGORY, empleado);
        setVisibilityByResource(comp, "id_option_prod_amount", Constantes.MODULO_PROD_AMOUNT, empleado);
        setVisibilityByResource(comp, "id_option_prod_inventory", Constantes.MODULO_PROD_INVENTORY, empleado);
        setVisibilityByResource(comp, "id_option_administracion", Constantes.MODULO_ADMINISTRACION, empleado);
        setVisibilityByResource(comp, "id_option_adm_areas", Constantes.MODULO_ADM_AREA, empleado);
        setVisibilityByResource(comp, "id_option_adm_emp", Constantes.MODULO_ADM_EMPLEADO, empleado);
        setVisibilityByResource(comp, "id_option_adm_mod", Constantes.MODULO_ADM_MODULO, empleado);
        setVisibilityByResource(comp, "id_option_adm_perf", Constantes.MODULO_ADM_PERFIL, empleado);
        setVisibilityByResource(comp, "id_option_adm_perfi_modulo", Constantes.MODULO_ADM_PERFILMODULO, empleado);
        setVisibilityByResource(comp, "id_option_chgpass", Constantes.MODULO_CHANGE_PASS, empleado);
    }

    private void setVisibilityByResource(final Borderlayout comp,
                                         final String widget,
                                         final String modulo,
                                         final DTO_Empleado empleado)
    {

        final ServiceInput<DTO_Empleado> input = new ServiceInput<DTO_Empleado>();
        input.addMapPair("empleado", empleado);
        input.addMapPair("modulo", modulo);
        final ServiceOutput<DTO_Empleado> output = autorizacionService.execute(input);
        if (output.getErrorCode() != Constantes.OK) {
            comp.getFellow(widget).setVisible(false);
        }

    }

    @Listen("onClick = #id_option_prod_products, #id_option_prod_categ, #id_option_prod_amount, "
                    + "#id_option_prod_inventory, "
                    + "#id_option_adm_areas, #id_option_adm_emp, #id_option_adm_perf, "
                    + "#id_option_adm_mod, #id_option_adm_perfi_modulo, #id_option_chgpass, "
                    + "#id_option_categ")
    public void incluir(final MouseEvent event)
    {
        // getDesktop().getSession().setAttribute("paginaActual", txt);
        desktop.getSession().setAttribute("actualizar", "actualizar");
        ContextLoader.saltar(desktop, PO_EAMainMenu.MAPA_MENU.get(event.getTarget().getId()));
    }

    public void incluir(final String _txt)
    {
        // getDesktop().getSession().setAttribute("paginaActual", txt);
        desktop.getSession().setAttribute("actualizar", "actualizar");
        ContextLoader.saltar(desktop, _txt);
    }
}
