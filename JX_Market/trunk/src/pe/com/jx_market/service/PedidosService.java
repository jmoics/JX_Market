/**
 *
 */
package pe.com.jx_market.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.jx_market.dao.DetallePedidoDAO;
import pe.com.jx_market.dao.PedidoDAO;
import pe.com.jx_market.domain.DTO_DetallePedido;
import pe.com.jx_market.domain.DTO_Pedido;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

/**
 * @author George
 *
 */
public class PedidosService implements BusinessService
{
    static Log logger = LogFactory.getLog(PedidosService.class);
    private PedidoDAO pedidoDAO;
    private DetallePedidoDAO detalleDAO;

    @SuppressWarnings("unchecked")
    @Override
    public DTO_Output execute(final DTO_Input input)
    {
        final DTO_Output output = new DTO_Output();
        if (Constantes.V_LISTEMP.equals(input.getVerbo())) {
            final List<DTO_Pedido> lst = pedidoDAO.getPedidos((DTO_Pedido) input.getObject());
            for (final DTO_Pedido ped : lst) {

            }
            //output.setLista(dao.getArticulos((DTO_Articulo)input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getVerbo())) {
            Integer pedMainId = null;
            final BigDecimal total = (BigDecimal) input.getObject();
            final Map<Integer, Map<DTO_Pedido, List<DTO_DetallePedido>>> map = input.getMapa();
            for (final Entry<Integer, Map<DTO_Pedido, List<DTO_DetallePedido>>> entry : map.entrySet()) {
                for (final Entry<DTO_Pedido, List<DTO_DetallePedido>> entry2 : entry.getValue().entrySet()) {
                    final DTO_Pedido ped = obtenerPedido(entry2.getKey());
                    if (pedMainId == null) {
                        final DTO_Pedido pedMain = obtenerPedido(ped);
                        pedMain.setEmpresa(Constantes.INSTITUCION_JX_MARKET);
                        pedMain.setTotal(total);
                        pedMainId = pedidoDAO.insertPedido(pedMain);
                    }
                    final Integer idPedido = pedidoDAO.insertPedido(ped);
                    pedidoDAO.connectPedido(pedMainId, idPedido);
                    final List<DTO_DetallePedido> lstDet = entry2.getValue();
                    for (final DTO_DetallePedido detPed : lstDet) {
                        final DTO_DetallePedido detAux = obtenerDetallePedido(detPed);
                        detAux.setPedido(idPedido);
                        detalleDAO.insertDetallePedido(detAux);
                    }
                }
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getVerbo())) {
            //final DTO_Articulo art = dao.getArticuloXCodigo((DTO_Articulo) input.getObject());
            //output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    private DTO_Pedido obtenerPedido (final DTO_Pedido ped) {
        final DTO_Pedido pedAux = new DTO_Pedido();
        pedAux.setCliente(ped.getCliente());
        pedAux.setEmpresa(ped.getEmpresa());
        pedAux.setFecha(ped.getFecha());
        pedAux.setIgv(ped.getIgv());
        pedAux.setTipo(ped.getTipo());
        pedAux.setTotal(ped.getTotal());
        return pedAux;
    }

    private DTO_DetallePedido obtenerDetallePedido (final DTO_DetallePedido det) {
        final DTO_DetallePedido detAux = new DTO_DetallePedido();
        detAux.setArticulo(det.getArticulo());
        detAux.setCantidad(det.getCantidad());
        return detAux;
    }

    public PedidoDAO getPedidoDAO()
    {
        return pedidoDAO;
    }

    public void setPedidoDAO(final PedidoDAO pedidoDAO)
    {
        this.pedidoDAO = pedidoDAO;
    }

    public DetallePedidoDAO getDetalleDAO()
    {
        return detalleDAO;
    }

    public void setDetalleDAO(final DetallePedidoDAO detalleDAO)
    {
        this.detalleDAO = detalleDAO;
    }

}
