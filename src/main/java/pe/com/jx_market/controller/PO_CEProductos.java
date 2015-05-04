package pe.com.jx_market.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Product;
import pe.com.jx_market.domain.DTO_Category;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_CEProductos extends Window
{
    static Log logger = LogFactory.getLog(PO_CEProductos.class);
    private final NumberFormat formateador = NumberFormat.getNumberInstance(Locale.ENGLISH);
    private BusinessService productService, categoryService;
    private Listbox lstProds;

    public void onCreate() {
        this.getParent();
        lstProds = (Listbox) getFellow("lstProds");
        productService = ContextLoader.getService(this, "productService");
        categoryService = ContextLoader.getService(this, "categoryService");

        listaProductos();
    }

    public void listaProductos()
    {
        lstProds.getItems().clear();
        DTO_Category cat = null;
        if (getDesktop().getSession().hasAttribute("category")) {
            cat = (DTO_Category) getDesktop().getSession().getAttribute("category");
            getDesktop().getSession().removeAttribute("category");
        }

        final DTO_Product product = new DTO_Product();
        if (cat != null) {
            //product.setCategory(cat.getCodigo());
        }
        final ServiceInput input = new ServiceInput(product);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput output = productService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Product> lst = output.getLista();
            for (final DTO_Product art : lst) {
                final Listitem item = new Listitem();
                item.setZclass("null");
                item.setSclass("prod_box");
                item.setAttribute("producto", art);

                final Listcell divProdBox = new Listcell();

                //top box
                final Div divTopProdBox = new Div();
                divTopProdBox.setSclass("top_prod_box");
                divTopProdBox.setParent(divProdBox);

                //center box
                final Div divCenterProdBox = new Div();
                divCenterProdBox.setSclass("center_prod_box");

                final Div divProdTitle = new Div();
                divProdTitle.setSclass("product_title");
                final Label nomProd = new Label(art.getProductName());
                nomProd.addEventListener("onClick",
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(final Event e)
                                throws UiException
                            {
                                getDesktop().getSession().setAttribute("producto", e.getTarget().getParent().getParent().getAttribute("producto"));
                                //incluir("cEDetalleProducto.zul");
                            }
                        });
                nomProd.setParent(divProdTitle);
                divProdTitle.setParent(divCenterProdBox);

                final Div divProdImg = new Div();
                divProdImg.setSclass("product_img");
                final Image image = new Image();
                image.setWidth("100px");
                image.setHeight("110px");
                setGraficoFoto(art, image);
                image.addEventListener("onClick",
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(final Event e)
                                throws UiException
                            {
                                getDesktop().getSession().setAttribute("producto", e.getTarget().getParent().getParent().getAttribute("producto"));
                                //incluir("cEDetalleProducto.zul");
                            }
                });
                image.setParent(divProdImg);

                divProdImg.setParent(divCenterProdBox);

                final Div divProdPrice = new Div();
                divProdPrice.setSclass("prod_price");
                final Label lbPrecio = new Label(formateador.format(BigDecimal.ZERO));
                lbPrecio.setSclass("price");
                divProdPrice.appendChild(lbPrecio);
                divProdPrice.setParent(divCenterProdBox);

                divCenterProdBox.setParent(divProdBox);

                //bottom box
                final Div divBottomProdBox = new Div();
                divBottomProdBox.setSclass("bottom_prod_box");
                divBottomProdBox.setParent(divProdBox);

                //detail box
                final Div divDetailBox = new Div();
                divDetailBox.setSclass("prod_details_tab");

                Image imgAdd = new Image();
                imgAdd.setSclass("left_bt");
                imgAdd.setSrc("media/images/cart.gif");
                imgAdd.addEventListener("onClick",
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(final Event e)
                                throws UiException
                            {
                                final DTO_Product prod = (DTO_Product) e.getTarget().getParent().getParent().getParent().getAttribute("producto");
                                agregarCarrito(prod);
                            }
                });
                imgAdd.setParent(divDetailBox);

                imgAdd = new Image();
                imgAdd.setSclass("left_bt");
                imgAdd.setSrc("media/images/favs.gif");
                imgAdd.setParent(divDetailBox);

                imgAdd = new Image();
                imgAdd.setSclass("left_bt");
                imgAdd.setSrc("media/images/favorites.gif");
                imgAdd.setParent(divDetailBox);

                final Label detLab = new Label("Detalles");
                detLab.setSclass("prod_details");
                detLab.addEventListener("onClick",
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(final Event e)
                                throws UiException
                            {
                                getDesktop().getSession().setAttribute("producto", e.getTarget().getParent().getParent().getAttribute("producto"));
                                //incluir("cEDetalleProducto.zul");
                            }
                });
                detLab.setParent(divDetailBox);

                divDetailBox.setParent(divProdBox);

                divProdBox.setParent(item);
                item.setParent(lstProds);

            }
        } else {

        }
    }

    @SuppressWarnings("unchecked")
    private void agregarCarrito(final DTO_Product prod) {
        if (getDesktop().getSession().getAttribute("client") != null
                        && getDesktop().getSession().getAttribute("carrito") != null) {
            final Map<Integer, Map<DTO_Product, Integer>> map = (Map<Integer, Map<DTO_Product, Integer>>) getDesktop()
                                                                                    .getSession().getAttribute("carrito");

            if (!map.containsKey(prod.getId())) {
                final Map<DTO_Product, Integer> map2 = new HashMap<DTO_Product, Integer>();
                map2.put(prod, 1);
                map.put(prod.getId(), map2);
            }
            incluir("cECarritoClient.zul");
        } else {
            Executions.sendRedirect("cELoginClient.zul");
        }
    }

    private void setGraficoFoto(final DTO_Product product, final Image imgFoto)
    {
        final ServiceInput input = new ServiceInput(product);
        input.setAction(Constantes.V_GETIMG);
        final ServiceOutput output = productService.execute(input);
        if (output.getErrorCode() != Constantes.OK) {
            alertaInfo("", "El product" + product.getProductName() + "no posee imagen", null);
        }
        /*if (product.getImagen() != null) {
            try {
                imgFoto.setContent(new AImage("foto", product.getImagen()));
                return;
            } catch (final IOException ex) {
                throw new RuntimeException(ex);
            }
        }*/
        imgFoto.setSrc("/media/imagProd.gif");
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
