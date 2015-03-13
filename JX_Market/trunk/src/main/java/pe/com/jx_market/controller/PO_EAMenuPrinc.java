/**
 *
 */
package pe.com.jx_market.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Image;

import pe.com.jx_market.domain.DTO_Empleado;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

/**
 * @author George
 *
 */
public class PO_EAMenuPrinc extends SelectorComposer<Borderlayout>
{
    /**
     * Mapeo de paginas a redirigir de acuerdo al ID del link en el menu.
     */
    public static final Map<String, String> MAPA_MENU = new HashMap<String, String>();
    {
        PO_EAMenuPrinc.MAPA_MENU.put("id_option_categ", "eAConsultaCategoria.zul");
        PO_EAMenuPrinc.MAPA_MENU.put("id_option_prod_ingreso", "eAIngresaProducto.zul");
        PO_EAMenuPrinc.MAPA_MENU.put("id_option_prod_consulta", "eAConsultaProducto.zul");
        PO_EAMenuPrinc.MAPA_MENU.put("id_option_prod_inventario","");
        PO_EAMenuPrinc.MAPA_MENU.put("id_option_adm_areas", "eAAdministraArea.zul");
        PO_EAMenuPrinc.MAPA_MENU.put("id_option_adm_emp", "eAAdministraEmpleado.zul");
        PO_EAMenuPrinc.MAPA_MENU.put("id_option_adm_perf", "eAAdministraPerfil.zul");
        PO_EAMenuPrinc.MAPA_MENU.put("id_option_adm_mod", "eAAdministraModulo.zul");
        PO_EAMenuPrinc.MAPA_MENU.put("id_option_adm_perfi_modulo", "eAAdministraPerfilModulo.zul");
        PO_EAMenuPrinc.MAPA_MENU.put("id_option_chgpass", "eACambiarContrasenia.zul");
    }
    
    static Log logger = LogFactory.getLog(PO_EAMenuPrinc.class);

    @WireVariable
    private Desktop desktop;
    private BusinessService autorizacionService;

    @Override
    public void doBeforeComposeChildren(Borderlayout comp)
        throws Exception
    {
        super.doBeforeComposeChildren(comp);
    }
    
    @Override
    public void doAfterCompose(final Borderlayout comp)
        throws Exception
    {
        super.doAfterCompose(comp);
        autorizacionService = Utility.getService(comp, "autorizacionService");
        incluir("eAFondo.zul");
        
        final DTO_Empleado empleado = (DTO_Empleado) comp.getDesktop().getSession().getAttribute("empleado");

        if (empleado == null) {
            throw new RuntimeException("La sesion se perdio, vuelva a ingresar por favor");
        }

        if(comp.getDesktop().getSession().getAttribute("actualizar") == null){
            incluir("eAFondo.zul");
        }
        
        getPage().addEventListener(Events.ON_BOOKMARK_CHANGE,
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(final Event arg0) throws Exception {
                                try {
                                    incluir(comp.getDesktop().getBookmark());
                                } catch (final org.zkoss.zk.ui.ComponentNotFoundException ex) {
                                    incluir("eAFondo.zul");
                                }
                            }
                      });
        
        setVisibilityByResource(comp, "id_option_productos", Constantes.MODULO_PRODUCTOS, empleado);
        setVisibilityByResource(comp, "id_option_prod_ingreso", Constantes.MODULO_PROD_INGRESO, empleado);
        setVisibilityByResource(comp, "id_option_prod_consulta", Constantes.MODULO_PROD_CONSULTA, empleado);
        setVisibilityByResource(comp, "id_option_prod_inventario", Constantes.MODULO_PROD_INVENTARIO, empleado);
        setVisibilityByResource(comp, "id_option_administracion", Constantes.MODULO_ADMINISTRACION, empleado);
        setVisibilityByResource(comp, "id_option_adm_areas", Constantes.MODULO_ADM_AREA, empleado);
        setVisibilityByResource(comp, "id_option_adm_emp", Constantes.MODULO_ADM_EMPLEADO, empleado);
        setVisibilityByResource(comp, "id_option_adm_mod", Constantes.MODULO_ADM_MODULO, empleado);
        setVisibilityByResource(comp, "id_option_adm_perf", Constantes.MODULO_ADM_PERFIL, empleado);
        setVisibilityByResource(comp, "id_option_adm_perfi_modulo", Constantes.MODULO_ADM_PERFILMODULO, empleado);
        setVisibilityByResource(comp, "id_option_chgpass", Constantes.MODULO_CHANGE_PASS, empleado);
    }
    
    public void onCreate()
    {
        /*imaLogo = (Image) getFellow("imaLogo");
        autorizacionService = Utility.getService(this, "autorizacionService");
        incluir("eAFondo.zul");
        final DTO_Empleado empleado = (DTO_Empleado) getDesktop().getSession().getAttribute("empleado");
        empresa = (DTO_Empresa) getDesktop().getSession().getAttribute("empresa");

        imaLogo.setSrc("Service/"+empresa.getDominio().toUpperCase()+"/"+empresa.getDominio()+".png");
        loadPhoto(empresa.getDominio());
        setGraficoFoto();
        if (empleado == null) {
            throw new RuntimeException("La sesion se perdio, vuelva a ingresar por favor");
        }

        if(getDesktop().getSession().getAttribute("actualizar") == null){
            incluir("eAFondo.zul");
        }*/

        /*getPage().addEventListener(Events.ON_BOOKMARK_CHANGE,
                new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(final Event arg0) throws Exception {
                        try {
                            incluir(getDesktop().getBookmark());
                        } catch (final org.zkoss.zk.ui.ComponentNotFoundException ex) {
                            incluir("eAFondo.zul");
                        }
                    }
              });*/

        /*setVisibilityByResource("id_option_productos", Constantes.MODULO_PRODUCTOS, empleado);
        setVisibilityByResource("id_option_prod_ingreso", Constantes.MODULO_PROD_INGRESO, empleado);
        setVisibilityByResource("id_option_prod_consulta", Constantes.MODULO_PROD_CONSULTA, empleado);
        setVisibilityByResource("id_option_prod_inventario", Constantes.MODULO_PROD_INVENTARIO, empleado);
        setVisibilityByResource("id_option_administracion", Constantes.MODULO_ADMINISTRACION, empleado);
        setVisibilityByResource("id_option_adm_areas", Constantes.MODULO_ADM_AREA, empleado);
        setVisibilityByResource("id_option_adm_emp", Constantes.MODULO_ADM_EMPLEADO, empleado);
        setVisibilityByResource("id_option_adm_mod", Constantes.MODULO_ADM_MODULO, empleado);
        setVisibilityByResource("id_option_adm_perf", Constantes.MODULO_ADM_PERFIL, empleado);
        setVisibilityByResource("id_option_adm_perfi_modulo", Constantes.MODULO_ADM_PERFILMODULO, empleado);
        setVisibilityByResource("id_option_chgpass", Constantes.MODULO_CHANGE_PASS, empleado);*/
    }

    private void setVisibilityByResource(final Borderlayout comp,
                                         final String widget,
                                         final String modulo,
                                         final DTO_Empleado empleado)
    {

        final DTO_Input input = new DTO_Input();
        input.addMapPair("empleado", empleado);
        input.addMapPair("modulo", modulo);
        final DTO_Output output = autorizacionService.execute(input);
        if (output.getErrorCode() != Constantes.OK) {
            comp.getFellow(widget).setVisible(false);
        }

    }

    @Listen("onClick = #id_option_prod_ingreso, #id_option_prod_consulta, #id_option_prod_inventario, "
                    + "#id_option_adm_areas, #id_option_adm_emp, #id_option_adm_perf, "
                    + "#id_option_adm_mod, #id_option_adm_perfi_modulo, #id_option_chgpass, "
                    + "#id_option_categ")
    public void incluir(MouseEvent event)
    {
        // getDesktop().getSession().setAttribute("paginaActual", txt);
        desktop.getSession().setAttribute("actualizar", "actualizar");
        Utility.saltar(desktop, PO_EAMenuPrinc.MAPA_MENU.get(event.getTarget().getId()));
    }
    
    public void incluir(String _txt)
    {
        // getDesktop().getSession().setAttribute("paginaActual", txt);
        desktop.getSession().setAttribute("actualizar", "actualizar");
        Utility.saltar(desktop, _txt);
    }

    @Listen("onClick=#salir")
    public void salir()
    {
        desktop.getSession().removeAttribute("login");
        desktop.getSession().removeAttribute("actualizar");
        desktop.getSession().removeAttribute("empresa");
        desktop.getSession().removeAttribute("empleado");
        // getDesktop().getSession().removeAttribute("paginaActual");
        Executions.sendRedirect("eALogin.zul");
    }

}
