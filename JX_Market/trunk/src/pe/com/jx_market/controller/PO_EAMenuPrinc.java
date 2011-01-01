/**
 *
 */
package pe.com.jx_market.controller;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
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
public class PO_EAMenuPrinc extends Borderlayout
{
    private Image imaLogo;
    private BusinessService autorizacionService;

    public void onCreate()
    {
        imaLogo = (Image) getFellow("imaLogo");
        autorizacionService = Utility.getService(this, "autorizacionService");
        incluir("eAFondo.zul");
        final DTO_Empleado empleado = (DTO_Empleado) getDesktop().getSession().getAttribute("empleado");
        final DTO_Empresa empresa = (DTO_Empresa) getDesktop().getSession().getAttribute("empresa");
        imaLogo.setSrc("Service/"+empresa.getDominio().toUpperCase()+"/"+empresa.getDominio()+".png");
        if (empleado == null) {
            throw new RuntimeException("La sesion se perdio, vuelva a ingresar por favor");
        }

        if(getDesktop().getSession().getAttribute("actualizar") == null){
            incluir("eAFondo.zul");
        }

        getPage().addEventListener(Events.ON_BOOKMARK_CHANGE,
                new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(final Event arg0) throws Exception {
                        try {
                            incluir(getDesktop().getBookmark());
                        } catch (final org.zkoss.zk.ui.ComponentNotFoundException ex) {
                            incluir("eAFondo.zul");
                        }
                    }
              });

        setVisibilityByResource("id_option_productos", Constantes.MODULO_PRODUCTOS, empleado);
        setVisibilityByResource("id_option_prod_ingreso", Constantes.MODULO_PROD_INGRESO, empleado);
        setVisibilityByResource("id_option_prod_consulta", Constantes.MODULO_PROD_CONSULTA, empleado);
        setVisibilityByResource("id_option_prod_inventario", Constantes.MODULO_PROD_INVENTARIO, empleado);
        setVisibilityByResource("id_option_administracion", Constantes.MODULO_ADMINISTRACION, empleado);
        setVisibilityByResource("id_option_adm_areas", Constantes.MODULO_ADM_AREA, empleado);
        setVisibilityByResource("id_option_adm_emp", Constantes.MODULO_ADM_EMPLEADO, empleado);
        setVisibilityByResource("id_option_adm_mod", Constantes.MODULO_ADM_MODULO, empleado);
        setVisibilityByResource("id_option_adm_perf", Constantes.MODULO_ADM_PERFIL, empleado);
        setVisibilityByResource("id_option_adm_perfi_modulo", Constantes.MODULO_ADM_PERFILMODULO, empleado);
        setVisibilityByResource("id_option_chgpass", Constantes.MODULO_CHANGE_PASS, empleado);
    }

    private void setVisibilityByResource(final String widget,
                                         final String modulo,
                                         final DTO_Empleado empleado)
    {

        final DTO_Input input = new DTO_Input();
        input.addMapPair("empleado", empleado);
        input.addMapPair("modulo", modulo);
        final DTO_Output output = autorizacionService.execute(input);
        if (output.getErrorCode() != Constantes.OK) {
            getFellow(widget).setVisible(false);
        }

    }

    public void incluir(final String txt)
    {
        // getDesktop().getSession().setAttribute("paginaActual", txt);
        getDesktop().getSession().setAttribute("actualizar", "actualizar");
        Utility.saltar(this, txt);
    }

    public void salir()
    {
        getDesktop().getSession().removeAttribute("login");
        getDesktop().getSession().removeAttribute("actualizar");
        getDesktop().getSession().removeAttribute("empresa");
        getDesktop().getSession().removeAttribute("empleado");
        // getDesktop().getSession().removeAttribute("paginaActual");
        Executions.sendRedirect("eALogin.zul");
    }

}
