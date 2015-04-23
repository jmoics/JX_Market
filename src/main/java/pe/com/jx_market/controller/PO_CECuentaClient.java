package pe.com.jx_market.controller;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Client;
import pe.com.jx_market.domain.DTO_DetallePedido;
import pe.com.jx_market.domain.DTO_Pedido;
import pe.com.jx_market.domain.DTO_Product;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_CECuentaClient extends Window
{
    static Log logger = LogFactory.getLog(PO_CECuentaClient.class);
    private final NumberFormat formateador = NumberFormat.getNumberInstance(Locale.ENGLISH);
    private BusinessService pedidosService, productService;
    private Listbox lstPed, lstDet;
    private Button btnReturn;

    public void onCreate() {
        this.getParent();
        lstPed = (Listbox) getFellow("lstPed");
        lstDet = (Listbox) getFellow("lstDet");
        btnReturn = (Button) getFellow("btnReturn");
        pedidosService = ContextLoader.getService(this, "pedidosService");
        productService = ContextLoader.getService(this, "productService");

        if (getDesktop().getSession().getAttribute("client") != null) {
            listarPedidos();
        }
    }

    @SuppressWarnings("unchecked")
    public void listarPedidos()
    {
        lstDet.setVisible(false);
        lstPed.setVisible(true);
        final DTO_Client client = (DTO_Client) getDesktop().getSession().getAttribute("client");
        final DTO_Pedido pedAux = new DTO_Pedido();
        pedAux.setClient(client.getId());
        pedAux.setCompany(Constantes.INSTITUCION_JX_MARKET);

        final ServiceInput input = new ServiceInput();
        input.setAccion(Constantes.V_LIST);
        input.setObject(pedAux);

        final ServiceOutput output = pedidosService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final Map<DTO_Pedido, List<DTO_DetallePedido>> mapPed = output.getMapa();
            for (final Entry<DTO_Pedido, List<DTO_DetallePedido>> entry : mapPed.entrySet()) {
                final Listitem item = new Listitem();

                Listcell cell = new Listcell();
                cell.setLabel(client.getClientLastName() + " " + client.getClientName());
                cell.setParent(item);

                cell = new Listcell();
                cell.setLabel(new SimpleDateFormat("dd/MM/yyyy").format(entry.getKey().getFecha()));
                cell.setParent(item);

                cell = new Listcell();
                cell.setLabel(entry.getKey().getTipo() == 1 ? Constantes.TIPO_PEDIDO_ENTREGA
                                                            : Constantes.TIPO_PEDIDO_RECOGE);
                cell.setParent(item);

                cell = new Listcell();
                cell.setLabel(formateador.format(entry.getKey().getIgv()));
                cell.setParent(item);

                cell = new Listcell();
                cell.setLabel(formateador.format(entry.getKey().getTotal()));
                cell.setParent(item);

                item.setAttribute("detalles", entry.getValue());
                item.addEventListener(Events.ON_CLICK,
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(final Event e)
                                throws UiException
                            {
                                listarDetalles((List<DTO_DetallePedido>)e.getTarget().getAttribute("detalles"));
                            }
                });

                lstPed.appendChild(item);
            }
        }
    }

    public void listarDetalles(final List<DTO_DetallePedido> lstDetPed) {
        lstDet.getItems().clear();
        lstDet.setVisible(true);
        lstPed.setVisible(false);
        btnReturn.setVisible(true);

        for (final DTO_DetallePedido det : lstDetPed) {
            final Listitem item = new Listitem();

            Listcell cell = new Listcell();
            cell.setLabel(det.getCantidad().toString());
            cell.setParent(item);

            final DTO_Product art = obtenerProduct(det.getProduct());

            cell = new Listcell();
            cell.setLabel(art.getProductName());
            cell.setParent(item);

            cell = new Listcell();
            cell.setLabel(art.getProductDescription());
            cell.setParent(item);

            cell = new Listcell();
            //cell.setLabel(formateador.format(art.getPrecio()));
            cell.setParent(item);

            cell = new Listcell();
            //cell.setLabel(formateador.format(art.getPrecio().multiply(new BigDecimal(det.getCantidad()))));
            cell.setParent(item);

            lstDet.appendChild(item);
        }
    }

    private DTO_Product obtenerProduct(final Integer codArt) {
        DTO_Product prod = new DTO_Product();
        prod.setId(codArt);

        final ServiceInput input = new ServiceInput();
        input.setAccion(Constantes.V_GET);
        input.setMapa(new HashMap<String, String>());
        input.setObject(prod);
        final ServiceOutput output = productService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            prod = (DTO_Product) output.getObject();
        }
        return prod;
    }

    public void retornar() {
        lstDet.setVisible(false);
        lstPed.setVisible(true);
        btnReturn.setVisible(false);
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
