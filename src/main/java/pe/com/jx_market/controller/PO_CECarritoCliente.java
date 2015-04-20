package pe.com.jx_market.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Executions;
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

import pe.com.jx_market.domain.DTO_Cliente;
import pe.com.jx_market.domain.DTO_DetallePedido;
import pe.com.jx_market.domain.DTO_Pedido;
import pe.com.jx_market.domain.DTO_Product;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_CECarritoCliente extends Window
{
    static Log logger = LogFactory.getLog(PO_CECarritoCliente.class);
    private final NumberFormat formateador = NumberFormat.getNumberInstance(Locale.ENGLISH);
    private BusinessService productService, categoryService, pedidosService;
    private Grid grdProds;

    public void onCreate() {
        this.getParent();
        grdProds = (Grid) getFellow("grdProds");
        productService = ContextLoader.getService(this, "productService");
        categoryService = ContextLoader.getService(this, "categoryService");
        pedidosService = ContextLoader.getService(this, "pedidosService");

        if (getDesktop().getSession().getAttribute("carrito") != null) {
            listaProductos();
        }
    }

    @SuppressWarnings("unchecked")
    public void listaProductos()
    {
        grdProds.getRows().getChildren().clear();
        final Map<Integer, Map<DTO_Product, Integer>> map = (Map<Integer, Map<DTO_Product, Integer>>) getDesktop()
                                                                                .getSession().getAttribute("carrito");
        Integer cantTot = new Integer(0);
        final BigDecimal precTotal = BigDecimal.ZERO;

        for (final Entry<Integer, Map<DTO_Product, Integer>> entry : map.entrySet()) {
            for (final Entry<DTO_Product, Integer> entry2 : entry.getValue().entrySet()) {
                final DTO_Product producto = entry2.getKey();

                final Row row = new Row();
                row.setAttribute("producto", producto);
                final Intbox cant = new Intbox();
                cant.setWidth("50px");
                cant.setValue(entry2.getValue());
                cant.setParent(row);

                cantTot = cantTot + entry2.getValue();

                final Label desc = new Label(producto.getProductName() + " " + producto.getProductDescription());
                desc.setParent(row);

                final Label prec = new Label();
                //prec.setValue(formateador.format(producto.getPrecio()));
                prec.setParent(row);

                final Label tot = new Label();
                //tot.setValue(formateador.format(producto.getPrecio().multiply(new BigDecimal(entry2.getValue()))));
                tot.setParent(row);

                //precTotal = precTotal.add(producto.getPrecio().multiply(new BigDecimal(entry2.getValue())));

                final Image imgGuardar = new Image("media/add.png");
                imgGuardar.setStyle("cursor:pointer");
                imgGuardar.addEventListener(Events.ON_CLICK,
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(final Event e)
                                throws UiException
                            {
                                actualizarCarrito((DTO_Product)e.getTarget()
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
                                quitarCarrito((DTO_Product)e.getTarget()
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
    private void actualizarCarrito(final DTO_Product producto, final Integer cantNew) {
        final Map<Integer, Map<DTO_Product, Integer>> map = (Map<Integer, Map<DTO_Product, Integer>>) getDesktop()
                                                                                .getSession().getAttribute("carrito");
        map.get(producto.getId()).put(producto, cantNew);
        listaProductos();
        //Executions.sendRedirect("index.zul");
        final Map<String, Object> map2 = (Map<String, Object>) getDesktop().getSession().getAttribute("totales");
        final Integer cantTot = (Integer) map2.get("cantidad");
        final BigDecimal precTotal = (BigDecimal) map2.get("total");
        ((Label) getDesktop().getPage("menup").getFellow("lbItems")).setValue(""+cantTot);
        ((Label) getDesktop().getPage("menup").getFellow("lbTotal")).setValue(formateador.format(precTotal));
    }

    @SuppressWarnings("unchecked")
    private void quitarCarrito(final DTO_Product producto) {
        final Map<Integer, Map<DTO_Product, Integer>> map = (Map<Integer, Map<DTO_Product, Integer>>) getDesktop()
                                                                                .getSession().getAttribute("carrito");
        map.remove(producto.getId());
        listaProductos();
        //Executions.sendRedirect("index.zul");
        final Map<String, Object> map2 = (Map<String, Object>) getDesktop().getSession().getAttribute("totales");
        final Integer cantTot = (Integer) map2.get("cantidad");
        final BigDecimal precTotal = (BigDecimal) map2.get("total");
        ((Label) getDesktop().getPage("menup").getFellow("lbItems")).setValue(""+cantTot);
        ((Label) getDesktop().getPage("menup").getFellow("lbTotal")).setValue(formateador.format(precTotal));
    }

    @SuppressWarnings({ "unchecked" })
    public void generarPedido() {
        final Map<Integer, Map<DTO_Product, Integer>> map = (Map<Integer, Map<DTO_Product, Integer>>) getDesktop()
                                                                                .getSession().getAttribute("carrito");
        final Map<Integer, Map<DTO_Pedido, List<DTO_DetallePedido>>> mapPed
                                                = new HashMap<Integer, Map<DTO_Pedido, List<DTO_DetallePedido>>>();

        for (final Entry<Integer, Map<DTO_Product, Integer>> entry : map.entrySet()) {
            for (final Entry<DTO_Product, Integer> entry2 : entry.getValue().entrySet()) {
                final DTO_Product prod = entry2.getKey();
                if (mapPed.containsKey(prod.getCompanyId())) {
                    final Map<DTO_Pedido, List<DTO_DetallePedido>> mapAux = mapPed.get(prod.getCompanyId());
                    for (final Entry<DTO_Pedido, List<DTO_DetallePedido>> entry3 : mapAux.entrySet()) {
                        final DTO_Pedido pedAux = entry3.getKey();
                        final BigDecimal total = pedAux.getTotal();
                        //total = total.add(prod.getPrecio().multiply(new BigDecimal(entry2.getValue())));

                        final List<DTO_DetallePedido> lstDet = entry3.getValue();
                        final DTO_DetallePedido det = new DTO_DetallePedido();
                        det.setProduct(prod.getId());
                        det.setCantidad(entry2.getValue());
                        lstDet.add(det);

                        pedAux.setTotal(total);
                        mapAux.remove(entry3.getKey());
                        mapAux.put(pedAux, lstDet);

                        mapPed.put(prod.getCompanyId(), mapAux);
                    }

                } else {
                    final DTO_Pedido pedAux = construirPedido();
                    pedAux.setCompany(prod.getCompanyId());
                    //pedAux.setTotal(prod.getPrecio().multiply(new BigDecimal(entry2.getValue())));

                    final List<DTO_DetallePedido> lstDet = new ArrayList<DTO_DetallePedido>();
                    final DTO_DetallePedido det = new DTO_DetallePedido();
                    det.setProduct(prod.getId());
                    det.setCantidad(entry2.getValue());
                    lstDet.add(det);

                    final Map<DTO_Pedido, List<DTO_DetallePedido>> mapAux = new HashMap<DTO_Pedido, List<DTO_DetallePedido>>();
                    mapAux.put(pedAux, lstDet);

                    mapPed.put(prod.getCompanyId(), mapAux);
                }
            }
        }

        final ServiceInput input = new ServiceInput();
        input.setAccion(Constantes.V_REGISTER);
        input.setMapa(mapPed);
        input.setObject(((Map) getDesktop().getSession().getAttribute("totales")).get("total"));
        final ServiceOutput output = pedidosService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            resetearAtributos();
            final int rpta = Messagebox.show("Su pedido fue realizado correctamente, " +
                                            "revise su cuenta en unos momentos para revisar la venta", "JX_Market",
                                            Messagebox.OK, Messagebox.INFORMATION);
            if (rpta == Messagebox.OK) {
                Executions.sendRedirect(null);
            }
        }

    }

    private DTO_Pedido construirPedido() {
        final DTO_Cliente cli = (DTO_Cliente) getDesktop().getSession().getAttribute("cliente");
        final DTO_Pedido ped = new DTO_Pedido();
        ped.setCliente(cli.getCodigo());
        ped.setFecha(new Date());
        ped.setIgv(BigDecimal.ZERO);
        ped.setTipo(1);

        return ped;
    }

    private void resetearAtributos() {
        final Map<Integer, Map<DTO_Product, Integer>> map = new HashMap<Integer, Map<DTO_Product, Integer>>();
        getDesktop().getSession().setAttribute("carrito", map);
        final Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("total", BigDecimal.ZERO);
        map2.put("cantidad", 0);
        getDesktop().getSession().setAttribute("totales", map2);
    }

    public void incluir(final String txt)
    {
        // getDesktop().getSession().setAttribute("paginaActual", txt);
        getDesktop().getSession().setAttribute("actualizar", "actualizar");
        ContextLoader.saltar(this, txt);
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
