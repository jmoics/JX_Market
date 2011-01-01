package pe.com.jx_market.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.image.AImage;
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

import pe.com.jx_market.domain.DTO_Articulo;
import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

public class PO_CEProductos extends Window
{
    static Log logger = LogFactory.getLog(PO_CEProductos.class);
    private NumberFormat formateador = NumberFormat.getNumberInstance(Locale.ENGLISH);
    private BusinessService articuloService, categoriaService;
    private Listbox lstProds;

    public void onCreate() {
        this.getParent();
        lstProds = (Listbox) getFellow("lstProds");
        articuloService = Utility.getService(this, "articuloService");
        categoriaService = Utility.getService(this, "categoriaService");

        listaProductos();
    }

    public void listaProductos()
    {
        final DTO_Articulo articulo = new DTO_Articulo();

        final DTO_Input input = new DTO_Input(articulo);
        input.setVerbo(Constantes.V_LIST);
        final DTO_Output output = articuloService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Articulo> lst = output.getLista();
            for (final DTO_Articulo art : lst) {
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
                final Label nomProd = new Label(art.getNombre());
                nomProd.setAttribute("producto", art);
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
                final Label lbPrecio = new Label(formateador.format(art.getPrecio()));
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
                                //getDesktop().getSession().setAttribute("producto", e.getTarget().getParent().getParent().getAttribute("producto"));
                                //incluir("cEDetalleProducto.zul");
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

    private void setGraficoFoto(final DTO_Articulo articulo, final Image imgFoto)
    {
        final DTO_Input input = new DTO_Input(articulo);
        input.setVerbo(Constantes.V_GETIMG);
        final DTO_Output output = articuloService.execute(input);
        if (output.getErrorCode() != Constantes.OK) {
            alertaInfo("", "El articulo" + articulo.getNombre() + "no posee imagen", null);
        }
        if (articulo.getImagen() != null) {
            try {
                imgFoto.setContent(new AImage("foto", articulo.getImagen()));
                return;
            } catch (final IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        imgFoto.setSrc("/media/imagProd.gif");
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
