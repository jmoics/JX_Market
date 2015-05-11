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
public class PedidosService
    implements BusinessService
{

    /**
     *
     */
    private final Log logger = LogFactory.getLog(PedidosService.class);
    /**
     *
     */
    @Autowired
    private PedidoMapper pedidoMapper;
    /**
     *
     */
    @Autowired
    private DetallePedidoMapper detallePedidoMapper;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public ServiceOutput execute(final ServiceInput _input)
    {
        final ServiceOutput output = new ServiceOutput();
        if (Constantes.V_LISTEMP.equals(_input.getAction())) {
            final List<DTO_Pedido> lst = this.pedidoMapper.getPedidos((DTO_Pedido) _input.getObject());
            /*for (final DTO_Pedido ped : lst) {

            }*/
            // output.setLista(dao.getProducts((DTO_Product)_input.getObject()));
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_LIST.equals(_input.getAction())) {
            final Map<DTO_Pedido, List<DTO_DetallePedido>> mapPed = new HashMap<DTO_Pedido, List<DTO_DetallePedido>>();
            // Pedidos de JX_Market para un client
            final List<DTO_Pedido> lstPedCli = this.pedidoMapper.getPedidos((DTO_Pedido) _input.getObject());
            for (final DTO_Pedido ped : lstPedCli) {
                final List<DTO_DetallePedido> lstPedEmp = new ArrayList<DTO_DetallePedido>();
                // Codigos de los pedidos de las company
                final List<Integer> lstPeds = this.pedidoMapper.getConnected(ped.getCodigo());
                for (final Integer codPed : lstPeds) {
                    final DTO_Pedido pedAux = new DTO_Pedido();
                    pedAux.setCodigo(codPed);
                    // Pedido de la company
                    final DTO_Pedido pedEmp = this.pedidoMapper.getPedidoXCodigo(pedAux);
                    final DTO_DetallePedido detPedAux = new DTO_DetallePedido();
                    detPedAux.setPedido(pedEmp.getCodigo());
                    final List<DTO_DetallePedido> detPedEmp = this.detallePedidoMapper.getDetallePedidos(detPedAux);
                    lstPedEmp.addAll(detPedEmp);
                }
                mapPed.put(ped, lstPedEmp);
            }
            output.setMapa(mapPed);
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_REGISTER.equals(_input.getAction())) {
            Integer pedMainId = null;
            final BigDecimal total = (BigDecimal) _input.getObject();
            final Map<Integer, Map<DTO_Pedido, List<DTO_DetallePedido>>> map = _input.getMapa();
            for (final Entry<Integer, Map<DTO_Pedido, List<DTO_DetallePedido>>> entry : map.entrySet()) {
                for (final Entry<DTO_Pedido, List<DTO_DetallePedido>> entry2 : entry.getValue().entrySet()) {
                    final DTO_Pedido ped = obtenerPedido(entry2.getKey());
                    if (pedMainId == null) {
                        final DTO_Pedido pedMain = obtenerPedido(ped);
                        pedMain.setCompany(Constantes.INSTITUCION_JX_MARKET);
                        pedMain.setTotal(total);
                        final DTO_Pedido pedTmp = this.pedidoMapper.getPedidoXCodigo(pedMain);
                        if (pedTmp == null) {
                            pedMainId = this.pedidoMapper.insertPedido(pedMain);
                        } else {
                            this.pedidoMapper.updatePedido(pedMain);
                            pedMainId = pedTmp.getCodigo();
                        }
                    }
                    final Integer idPedido = this.pedidoMapper.insertPedido(ped);
                    this.pedidoMapper.connectPedido(pedMainId, idPedido);
                    final List<DTO_DetallePedido> lstDet = entry2.getValue();
                    for (final DTO_DetallePedido detPed : lstDet) {
                        final DTO_DetallePedido detAux = obtenerDetallePedido(detPed);
                        detAux.setPedido(idPedido);
                        this.detallePedidoMapper.insertDetallePedido(detAux);

                        // Ver si el update es factible
                    }
                }
            }
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_GET.equals(_input.getAction())) {
            // final DTO_Product art = dao.getProductXCodigo((DTO_Product)
            // _input.getObject());
            // output.setObject(art);
            output.setErrorCode(Constantes.OK);
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
        return output;
    }

    /**
     * @param _ped
     * @return
     */
    private DTO_Pedido obtenerPedido(final DTO_Pedido _ped)
    {
        final DTO_Pedido pedAux = new DTO_Pedido();
        pedAux.setClient(_ped.getClient());
        pedAux.setCompany(_ped.getCompany());
        pedAux.setFecha(_ped.getFecha());
        pedAux.setIgv(_ped.getIgv());
        pedAux.setTipo(_ped.getTipo());
        pedAux.setTotal(_ped.getTotal());
        return pedAux;
    }

    /**
     * @param _det
     * @return
     */
    private DTO_DetallePedido obtenerDetallePedido(final DTO_DetallePedido _det)
    {
        final DTO_DetallePedido detAux = new DTO_DetallePedido();
        detAux.setProduct(_det.getProduct());
        detAux.setCantidad(_det.getCantidad());
        return detAux;
    }
}
