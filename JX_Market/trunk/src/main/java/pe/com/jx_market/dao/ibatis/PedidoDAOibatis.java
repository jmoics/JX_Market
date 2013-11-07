/**
 *
 */
package pe.com.jx_market.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.jx_market.dao.PedidoDAO;
import pe.com.jx_market.domain.DTO_Pedido;

/**
 * @author George
 *
 */
public class PedidoDAOibatis
    extends SqlMapClientDaoSupport
    implements PedidoDAO
{

    /*
     * (non-Javadoc)
     * @see
     * pe.com.jx_market.dao.PedidoDAO#getPedidos(pe.com.jx_market.domain
     * .DTO_Pedido)
     */
    @Override
    public List<DTO_Pedido> getPedidos(final DTO_Pedido pedido)
    {
        return getSqlMapClientTemplate().queryForList("getPedidos", pedido);
    }

    /*
     * (non-Javadoc)
     * @see
     * pe.com.jx_market.dao.PedidoDAO#getPedidoXCodigo(pe.com.jx_market.
     * domain.DTO_Pedido)
     */
    @Override
    public DTO_Pedido getPedidoXCodigo(final DTO_Pedido pedido)
    {
        return (DTO_Pedido) getSqlMapClientTemplate().queryForObject("getPedidosXCodigo", pedido);
    }

    /*
     * (non-Javadoc)
     * @see
     * pe.com.jx_market.dao.PedidoDAO#insertPedido(pe.com.jx_market.domain
     * .DTO_Pedido)
     */
    @Override
    public Integer insertPedido(final DTO_Pedido pedido)
    {
        final DTO_Pedido ped = (DTO_Pedido) getSqlMapClientTemplate().queryForObject("getPedidosXCodigo", pedido);
        Integer ret = -1;
        if (ped == null) {
            ret = (Integer) getSqlMapClientTemplate().insert("insertPedido", pedido);
        } else {
            getSqlMapClientTemplate().update("updatePedido", pedido);
        }
        return ret;
    }

    @Override
    public boolean deletePedido(final DTO_Pedido ped)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean connectPedido(final Integer pedFrom,
                                 final Integer pedTo)
    {
        final Map<String, Integer> mapa = new HashMap<String, Integer>();
        mapa.put("fromLink", pedFrom);
        mapa.put("toLink", pedTo);
        getSqlMapClientTemplate().insert("connectPedido", mapa);
        return true;
    }

    @Override
    public List<Integer> getConnected(final Integer pedFrom)
    {
        final Map<String, Integer> mapa = new HashMap<String, Integer>();
        mapa.put("fromLink", pedFrom);

        return getSqlMapClientTemplate().queryForList("getConnected", mapa);
    }


}
