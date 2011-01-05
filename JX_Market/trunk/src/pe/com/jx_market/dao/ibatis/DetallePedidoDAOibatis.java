/**
 *
 */
package pe.com.jx_market.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.jx_market.dao.DetallePedidoDAO;
import pe.com.jx_market.domain.DTO_DetallePedido;

/**
 * @author George
 *
 */
public class DetallePedidoDAOibatis
    extends SqlMapClientDaoSupport
    implements DetallePedidoDAO
{

    /*
     * (non-Javadoc)
     * @see
     * pe.com.jx_market.dao.DetallePedidoDAO#getDetallePedidos(pe.com.jx_market.domain
     * .DTO_DetallePedido)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DTO_DetallePedido> getDetallePedidos(final DTO_DetallePedido detalle)
    {
        return getSqlMapClientTemplate().queryForList("getDetallePedidos", detalle);
    }

    /*
     * (non-Javadoc)
     * @see
     * pe.com.jx_market.dao.DetallePedidoDAO#getDetallePedidoXCodigo(pe.com.jx_market.
     * domain.DTO_DetallePedido)
     */
    @Override
    public DTO_DetallePedido getDetallePedidoXCodigo(final DTO_DetallePedido detalle)
    {
        return (DTO_DetallePedido) getSqlMapClientTemplate().queryForObject("getDetallePedidos", detalle);
    }

    /*
     * (non-Javadoc)
     * @see
     * pe.com.jx_market.dao.DetallePedidoDAO#insertDetallePedido(pe.com.jx_market.domain
     * .DTO_DetallePedido)
     */
    @Override
    public boolean insertDetallePedido(final DTO_DetallePedido detalle)
    {
        final DTO_DetallePedido detped = (DTO_DetallePedido) getSqlMapClientTemplate().queryForObject("getDetallePedidos", detalle);
        if (detped == null)
            getSqlMapClientTemplate().insert("insertDetallePedido", detalle);
        else
            getSqlMapClientTemplate().update("updateDetallePedido", detalle);
        return true;
    }

    @Override
    public boolean deleteDetallePedido(final DTO_DetallePedido ped)
    {
        // TODO Auto-generated method stub
        return false;
    }


}
