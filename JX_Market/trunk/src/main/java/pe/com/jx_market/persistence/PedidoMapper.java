/**
 *
 */
package pe.com.jx_market.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pe.com.jx_market.domain.DTO_Pedido;

/**
 * @author George
 *
 */
public interface PedidoMapper
{
    public List<DTO_Pedido> getPedidos (DTO_Pedido ped);
    public DTO_Pedido getPedidoXCodigo (DTO_Pedido ped);
    public List<Integer> getConnected (Integer pedFrom);
    public Integer insertPedido (DTO_Pedido ped);
    public Integer updatePedido (DTO_Pedido ped);
    public boolean deletePedido (DTO_Pedido ped);
    public boolean connectPedido (@Param("fromLink") Integer pedFrom, @Param("toLink")Integer pedTo);
}
