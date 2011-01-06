/**
 *
 */
package pe.com.jx_market.dao;

import java.util.List;

import pe.com.jx_market.domain.DTO_Pedido;

/**
 * @author George
 *
 */
public interface PedidoDAO
{
    public List<DTO_Pedido> getPedidos (DTO_Pedido ped);
    public DTO_Pedido getPedidoXCodigo (DTO_Pedido ped);
    public List<Integer> getConnected (Integer pedFrom);
    public Integer insertPedido (DTO_Pedido ped);
    public boolean deletePedido (DTO_Pedido ped);
    public boolean connectPedido (Integer pedFrom, Integer pedTo);
}
