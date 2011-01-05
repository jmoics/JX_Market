package pe.com.jx_market.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Articulo;
import pe.com.jx_market.utilities.BusinessService;

public class PO_CECarritoCliente extends Window
{
    static Log logger = LogFactory.getLog(PO_CECarritoCliente.class);
    private NumberFormat formateador = NumberFormat.getNumberInstance(Locale.ENGLISH);
    private BusinessService articuloService, categoriaService;
    private Grid grdProds;

    public void onCreate() {
        this.getParent();
        grdProds = (Grid) getFellow("grdProds");
        articuloService = Utility.getService(this, "articuloService");
        categoriaService = Utility.getService(this, "categoriaService");

        if (getDesktop().getSession().getAttribute("carrito") != null) {
            listaProductos();
        }
    }

    @SuppressWarnings("unchecked")
    public void listaProductos()
    {
        grdProds.getRows().getChildren().clear();
        final Map<Integer, Map<DTO_Articulo, Integer>> map = (Map<Integer, Map<DTO_Articulo, Integer>>) getDesktop()
                                                                                .getSession().getAttribute("carrito");
        Integer cantTot = new Integer(0);
        BigDecimal precTotal = BigDecimal.ZERO;

        for (final Entry<Integer, Map<DTO_Articulo, Integer>> entry : map.entrySet()) {
            for (final Entry<DTO_Articulo, Integer> entry2 : entry.getValue().entrySet()) {
                final DTO_Articulo producto = entry2.getKey();

                final Row row = new Row();
                row.setAttribute("producto", producto);
                final Intbox cant = new Intbox();
                cant.setWidth("50px");
                cant.setValue(entry2.getValue());
                cant.setParent(row);

                cantTot = cantTot + entry2.getValue();

                final Label desc = new Label(producto.getNombre() + " " + producto.getDescripcion());
                desc.setParent(row);

                final Label prec = new Label();
                prec.setValue(formateador.format(producto.getPrecio()));
                prec.setParent(row);

                final Label tot = new Label();
                tot.setValue(formateador.format(producto.getPrecio().multiply(new BigDecimal(entry2.getValue()))));
                tot.setParent(row);

                precTotal = precTotal.add(producto.getPrecio().multiply(new BigDecimal(entry2.getValue())));

                final Image imgGuardar = new Image("media/add.png");
                imgGuardar.setStyle("cursor:pointer");
                imgGuardar.addEventListener(Events.ON_CLICK,
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(final Event e)
                                throws UiException
                            {
                                actualizarCarrito((DTO_Articulo)e.getTarget()
                                                .getParent().getParent().getAttribute("producto"),
                                            ((Intbox)((Row) e.getTarget()
                                                .getParent().getParent()).getChildren().get(0)).getValue());
                            }
                });
                final Div divEditar = new Div();
                divEditar.setAlign("center");
                divEditar.appendChild(imgGuardar);
                divEditar.setParent(row);

                final Image imgQuitar = new Image("media/remove.png");
                imgQuitar.setStyle("cursor:pointer");
                imgQuitar.addEventListener(Events.ON_CLICK,
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(final Event e)
                                throws UiException
                            {
                                quitarCarrito((DTO_Articulo)e.getTarget()
                                                .getParent().getParent().getAttribute("producto"));
                            }
                });
                final Div divQuitar = new Div();
                divQuitar.setAlign("center");
                divQuitar.appendChild(imgQuitar);
                divQuitar.setParent(row);

                grdProds.getRows().appendChild(row);
            }
        }
        final Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("cantidad", cantTot);
        map2.put("total", precTotal);
        getDesktop().getSession().setAttribute("totales", map2);
    }

    @SuppressWarnings("unchecked")
    private void actualizarCarrito(final DTO_Articulo producto, final Integer cantNew) {
        final Map<Integer, Map<DTO_Articulo, Integer>> map = (Map<Integer, Map<DTO_Articulo, Integer>>) getDesktop()
                                                                                .getSession().getAttribute("carrito");
        map.get(producto.getCodigo()).put(producto, cantNew);
        listaProductos();
        //Executions.sendRedirect("index.zul");
        final Map<String, Object> map2 = (Map<String, Object>) getDesktop().getSession().getAttribute("totales");
        final Integer cantTot = (Integer) map2.get("cantidad");
        final BigDecimal precTotal = (BigDecimal) map2.get("total");
        ((Label) getDesktop().getPage("menup").getFellow("lbItems")).setValue(""+cantTot);
        ((Label) getDesktop().getPage("menup").getFellow("lbTotal")).setValue(formateador.format(precTotal));
    }

    @SuppressWarnings("unchecked")
    private void quitarCarrito(final DTO_Articulo producto) {
        final Map<Integer, Map<DTO_Articulo, Integer>> map = (Map<Integer, Map<DTO_Articulo, Integer>>) getDesktop()
                                                                                .getSession().getAttribute("carrito");
        map.remove(producto.getCodigo());
        listaProductos();
        //Executions.sendRedirect("index.zul");
        final Map<String, Object> map2 = (Map<String, Object>) getDesktop().getSession().getAttribute("totales");
        final Integer cantTot = (Integer) map2.get("cantidad");
        final BigDecimal precTotal = (BigDecimal) map2.get("total");
        ((Label) getDesktop().getPage("menup").getFellow("lbItems")).setValue(""+cantTot);
        ((Label) getDesktop().getPage("menup").getFellow("lbTotal")).setValue(formateador.format(precTotal));
    }

    public void incluir(final String txt)
    {
        // getDesktop().getSession().setAttribute("paginaActual", txt);
        getDesktop().getSession().setAttribute("actualizar", "actualizar");
        Utility.saltar(this, txt);
    }

    public void alertaInfo(final String txt,
                           final String txt2,
                           final Throwable t)
    {
        try {
            if (txt.length() > 0)
                Messagebox.show(txt, "JX_Market", 1, Messagebox.INFORMATION);
            if (t != null) {
                logger.info(txt2, t);
            } else {
                logger.info(txt2);
            }
        } catch (final InterruptedException ex) {
        }
    }

    public void alertaError(final String txt,
                            final String txt2,
                            final Throwable t)
    {
        try {
            if (txt.length() > 0)
                Messagebox.show(txt, "JX_Market", 1, Messagebox.EXCLAMATION);
            if (t != null) {
                logger.error(txt2, t);
            } else {
                logger.error(txt2);
            }
        } catch (final InterruptedException ex) {
        }

    }
}
