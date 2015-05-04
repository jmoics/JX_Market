/**
 *
 */
package pe.com.jx_market.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_DetallePedido;
import pe.com.jx_market.domain.DTO_Pedido;
import pe.com.jx_market.persistence.DetallePedidoMapper;
import pe.com.jx_market.persistence.PedidoMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * @author George
 *
 */
@Service
public class PedidosService implements BusinessService
{
    static Log logger = LogFactory.getLog(PedidosService.class);
    @Autowired
    private PedidoMapper pedidoMapper;
    @Autowired
    private DetallePedidoMapper detallePedidoMapper;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public ServiceOutput execute(final ServiceInput input)
    {
        final ServiceOutput output = new ServiceOutput();
        if (Constantes.V_LISTEMP.equals(input.getAction())) {
            final List<DTO_Pedido> lst = pedidoMapper.getPedidos((DTO_Pedido) input.getObject());
            for (final DTO_Pedido ped : lst) {

            }
            //output.setLista(dao.getProducts((DTO_Product)input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_LIST.equals(input.getAction())) {
            final Map<DTO_Pedido, List<DTO_DetallePedido>> mapPed = new HashMap<DTO_Pedido, List<DTO_DetallePedido>>();
            //Pedidos de JX_Market para un client
            final List<DTO_Pedido> lstPedCli = pedidoMapper.getPedidos((DTO_Pedido) input.getObject());
            for (final DTO_Pedido ped : lstPedCli) {
                final List<DTO_DetallePedido> lstPedEmp = new ArrayList<DTO_DetallePedido>();
                //Codigos de los pedidos de las company
                final List<Integer> lstPeds = pedidoMapper.getConnected(ped.getCodigo());
                for (final Integer codPed : lstPeds) {
                    final DTO_Pedido pedAux = new DTO_Pedido();
                    pedAux.setCodigo(codPed);
                    //Pedido de la company
                    final DTO_Pedido pedEmp = pedidoMapper.getPedidoXCodigo(pedAux);
                    final DTO_DetallePedido detPedAux = new DTO_DetallePedido();
                    detPedAux.setPedido(pedEmp.getCodigo());
                    final List<DTO_DetallePedido> detPedEmp = detallePedidoMapper.getDetallePedidos(detPedAux);
                    lstPedEmp.addAll(detPedEmp);
                }
                mapPed.put(ped, lstPedEmp);
            }
            output.setMapa(mapPed);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getAction())) {
            Integer pedMainId = null;
            final BigDecimal total = (BigDecimal) input.getObject();
            final Map<Integer, Map<DTO_Pedido, List<DTO_DetallePedido>>> map = input.getMapa();
            for (final Entry<Integer, Map<DTO_Pedido, List<DTO_DetallePedido>>> entry : map.entrySet()) {
                for (final Entry<DTO_Pedido, List<DTO_DetallePedido>> entry2 : entry.getValue().entrySet()) {
                    final DTO_Pedido ped = obtenerPedido(entry2.getKey());
                    if (pedMainId == null) {
                        final DTO_Pedido pedMain = obtenerPedido(ped);
                        pedMain.setCompany(Constantes.INSTITUCION_JX_MARKET);
                        pedMain.setTotal(total);
                        DTO_Pedido pedTmp = pedidoMapper.getPedidoXCodigo(pedMain);
                        if (pedTmp == null) {
                            pedMainId = pedidoMapper.insertPedido(pedMain);
                        } else {
                            pedidoMapper.updatePedido(pedMain);
                            pedMainId = pedTmp.getCodigo();
                        }
                    }
                    final Integer idPedido = pedidoMapper.insertPedido(ped);
                    pedidoMapper.connectPedido(pedMainId, idPedido);
                    final List<DTO_DetallePedido> lstDet = entry2.getValue();
                    for (final DTO_DetallePedido detPed : lstDet) {
                        final DTO_DetallePedido detAux = obtenerDetallePedido(detPed);
                        detAux.setPedido(idPedido);
                        detallePedidoMapper.insertDetallePedido(detAux);
                        
                        // Ver si el update es factible
                    }
                }
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getAction())) {
            //final DTO_Product art = dao.getProductXCodigo((DTO_Product) input.getObject());
            //output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    private DTO_Pedido obtenerPedido (final DTO_Pedido ped) {
        final DTO_Pedido pedAux = new DTO_Pedido();
        pedAux.setClient(ped.getClient());
        pedAux.setCompany(ped.getCompany());
        pedAux.setFecha(ped.getFecha());
        pedAux.setIgv(ped.getIgv());
        pedAux.setTipo(ped.getTipo());
        pedAux.setTotal(ped.getTotal());
        return pedAux;
    }

    private DTO_DetallePedido obtenerDetallePedido (final DTO_DetallePedido det) {
        final DTO_DetallePedido detAux = new DTO_DetallePedido();
        detAux.setProduct(det.getProduct());
        detAux.setCantidad(det.getCantidad());
        return detAux;
    }

    public PedidoMapper getPedidoDAO()
    {
        return pedidoMapper;
    }

    public void setPedidoDAO(final PedidoMapper pedidoMapper)
    {
        this.pedidoMapper = pedidoMapper;
    }

    public DetallePedidoMapper getDetalleDAO()
    {
        return detallePedidoMapper;
    }

    public void setDetalleDAO(final DetallePedidoMapper detallePedidoMapper)
    {
        this.detallePedidoMapper = detallePedidoMapper;
    }

}
