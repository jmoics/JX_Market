package pe.com.jx_market.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zhtml.Li;
import org.zkoss.zhtml.Ul;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import pe.com.jx_market.domain.DTO_Category;
import pe.com.jx_market.domain.DTO_Client;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_Index
    extends Div
{
    static Log logger = LogFactory.getLog(PO_Index.class);
    private final NumberFormat formateador = NumberFormat.getNumberInstance(Locale.ENGLISH);
    private BusinessService productService, categoryService;
    private Ul ulCategories;
    private Label lbUser, lbTotal, lbItems, lbSalir;

    public void onCreate()
    {
        categoryService = ContextLoader.getService(this, "categoryService");
        ulCategories = (Ul) getFellow("ulCategories");
        lbUser = (Label) getFellow("lbUser");
        lbTotal = (Label) getFellow("lbTotal");
        lbItems = (Label) getFellow("lbItems");
        lbSalir = (Label) getFellow("lbSalir");
        ulCategories.getSclass();
        ulCategories.setSclass("left_menu");
        // incluir("cEProductos.zul");
        listarCategories();

        if (getDesktop().getSession().getAttribute("actualizar") == null) {
            incluir("cEProductos.zul");
        }

        if (getDesktop().getSession().getAttribute("sendPage") != null) {
            getDesktop().getSession().removeAttribute("sendPage");
            Executions.sendRedirect(null);
        }

        if (getDesktop().getSession().getAttribute("client") != null) {
            final DTO_Client client = (DTO_Client) getDesktop().getSession().getAttribute("client");
            lbUser.setValue(client.getClientLastName() + " " + client.getClientName());
            lbSalir.setVisible(true);

            actualizarCarrito();
        }

        getPage().addEventListener(Events.ON_BOOKMARK_CHANGE,
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(final Event arg0)
                                throws Exception
                            {
                                try {
                                    incluir(getDesktop().getBookmark());
                                } catch (final org.zkoss.zk.ui.ComponentNotFoundException ex) {
                                    incluir("cEProductos.zul");
                                }
                            }
                        });
    }

    public void listarCategories()
    {
        final DTO_Category cat = new DTO_Category();
        final ServiceInput input = new ServiceInput(cat);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput output = categoryService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("", "Exito al cargar categories", null);
            final List<DTO_Category> lstCat = output.getLista();
            int cont = 1;
            for (final DTO_Category categ : lstCat) {
                final Li item = new Li();
                if (cont % 2 == 0) {
                    item.setSclass("even");
                } else {
                    item.setSclass("odd");
                }
                item.appendChild((new Label(categ.getCategoryName())));
                item.setAttribute("category", categ);
                item.addEventListener("onClick",
                                new org.zkoss.zk.ui.event.EventListener() {
                                    @Override
                                    public void onEvent(final Event e)
                                        throws UiException
                    {
                        getDesktop().getSession().setAttribute("category", e.getTarget().getAttribute("category"));
                        saltarPagina("cEProductos.zul", false);
                    }
                                });
                ulCategories.appendChild(item);
                cont++;
            }
        } else {
            alertaError("Error inesperado, por favor contacte al administrador", "Error cargando categories", null);
        }
    }

    @SuppressWarnings("unchecked")
    private void actualizarCarrito()
    {
        final Map<String, Object> map2 = (Map<String, Object>) getDesktop().getSession().getAttribute("totales");
        final Integer cantTot = (Integer) map2.get("cantidad");
        final BigDecimal precTotal = (BigDecimal) map2.get("total");
        lbItems.setValue("" + cantTot);
        lbTotal.setValue(formateador.format(precTotal));

    }

    public void incluir(final String txt)
    {
        // getDesktop().getSession().setAttribute("paginaActual", txt);
        getDesktop().getSession().setAttribute("actualizar", "actualizar");
        ContextLoader.saltar(this, txt);
    }

    public void saltarPagina(final String txt,
                             final boolean anotherPage)
    {
        if (getDesktop().getBookmark().contains(txt)) {
            Executions.sendRedirect(null);
        } else if (anotherPage) {
            Executions.sendRedirect(txt);
        } else {
            incluir(txt);
        }
    }

    public void cerrarSesion()
    {
        getDesktop().getSession().removeAttribute("client");
        getDesktop().getSession().removeAttribute("carrito");
        getDesktop().getSession().removeAttribute("actualizar");

        Executions.sendRedirect(null);
    }

    public void alertaInfo(final String txt,
                           final String txt2,
                           final Throwable t)
    {
        if (txt.length() > 0) {
            Messagebox.show(txt, "JX_Market", 1, Messagebox.INFORMATION);
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
            Messagebox.show(txt, "JX_Market", 1, Messagebox.EXCLAMATION);
        }
        if (t != null) {
            logger.error(txt2, t);
        } else {
            logger.error(txt2);
        }

    }

}
