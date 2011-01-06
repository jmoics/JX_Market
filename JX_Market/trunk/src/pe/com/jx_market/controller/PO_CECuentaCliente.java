package pe.com.jx_market.controller;

import java.math.BigDecimal;
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

import pe.com.jx_market.domain.DTO_Articulo;
import pe.com.jx_market.domain.DTO_Cliente;
import pe.com.jx_market.domain.DTO_DetallePedido;
import pe.com.jx_market.domain.DTO_Pedido;
import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

public class PO_CECuentaCliente extends Window
{
    static Log logger = LogFactory.getLog(PO_CECuentaCliente.class);
    private NumberFormat formateador = NumberFormat.getNumberInstance(Locale.ENGLISH);
    private BusinessService pedidosService, articuloService;
    private Listbox lstPed, lstDet;
    private Button btnReturn;

    public void onCreate() {
        this.getParent();
        lstPed = (Listbox) getFellow("lstPed");
        lstDet = (Listbox) getFellow("lstDet");
        btnReturn = (Button) getFellow("btnReturn");
        pedidosService = Utility.getService(this, "pedidosService");
        articuloService = Utility.getService(this, "articuloService");

        if (getDesktop().getSession().getAttribute("cliente") != null) {
            listarPedidos();
        }
    }

    @SuppressWarnings("unchecked")
    public void listarPedidos()
    {
        lstDet.setVisible(false);
        lstPed.setVisible(true);
        final DTO_Cliente cliente = (DTO_Cliente) getDesktop().getSession().getAttribute("cliente");
        final DTO_Pedido pedAux = new DTO_Pedido();
        pedAux.setCliente(cliente.getCodigo());
        pedAux.setEmpresa(Constantes.INSTITUCION_JX_MARKET);

        final DTO_Input input = new DTO_Input();
        input.setVerbo(Constantes.V_LIST);
        input.setObject(pedAux);

        final DTO_Output output = pedidosService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final Map<DTO_Pedido, List<DTO_DetallePedido>> mapPed = output.getMapa();
            for (final Entry<DTO_Pedido, List<DTO_DetallePedido>> entry : mapPed.entrySet()) {
                final Listitem item = new Listitem();

                Listcell cell = new Listcell();
                cell.setLabel(cliente.getApellido() + " " + cliente.getNombre());
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

            final DTO_Articulo art = obtenerArticulo(det.getArticulo());

            cell = new Listcell();
            cell.setLabel(art.getNombre());
            cell.setParent(item);

            cell = new Listcell();
            cell.setLabel(art.getDescripcion());
            cell.setParent(item);

            cell = new Listcell();
            cell.setLabel(formateador.format(art.getPrecio()));
            cell.setParent(item);

            cell = new Listcell();
            cell.setLabel(formateador.format(art.getPrecio().multiply(new BigDecimal(det.getCantidad()))));
            cell.setParent(item);

            lstDet.appendChild(item);
        }
    }

    private DTO_Articulo obtenerArticulo(final Integer codArt) {
        DTO_Articulo arti = new DTO_Articulo();
        arti.setCodigo(codArt);

        final DTO_Input input = new DTO_Input();
        input.setVerbo(Constantes.V_GET);
        input.setMapa(new HashMap<String, String>());
        input.setObject(arti);
        final DTO_Output output = articuloService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            arti = (DTO_Articulo) output.getObject();
        }
        return arti;
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
