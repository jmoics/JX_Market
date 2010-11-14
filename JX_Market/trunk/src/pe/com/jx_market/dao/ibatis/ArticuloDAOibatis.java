/**
 * 
 */
package pe.com.jx_market.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.jx_market.dao.ArticuloDAO;
import pe.com.jx_market.domain.DTO_Articulo;
import pe.com.jx_market.domain.DTO_Usuario;

/**
 * @author George
 * 
 */
public class ArticuloDAOibatis
    extends SqlMapClientDaoSupport
    implements ArticuloDAO
{

    /*
     * (non-Javadoc)
     * @see
     * pe.com.jx_market.dao.ArticuloDAO#getArticulos(pe.com.jx_market.domain
     * .DTO_Articulo)
     */
    @Override
    public List<DTO_Articulo> getArticulos(DTO_Articulo art)
    {
        return getSqlMapClientTemplate().queryForList("getArticulos", art);
    }

    /*
     * (non-Javadoc)
     * @see
     * pe.com.jx_market.dao.ArticuloDAO#getArticuloXCodigo(pe.com.jx_market.
     * domain.DTO_Articulo)
     */
    @Override
    public DTO_Articulo getArticuloXCodigo(DTO_Articulo art)
    {
        return (DTO_Articulo) getSqlMapClientTemplate().queryForObject("getArticuloXCodigo", art);
    }

    /*
     * (non-Javadoc)
     * @see
     * pe.com.jx_market.dao.ArticuloDAO#insertArticulo(pe.com.jx_market.domain
     * .DTO_Articulo)
     */
    @Override
    public boolean insertArticulo(DTO_Articulo art)
    {
        final DTO_Articulo articulo = (DTO_Articulo) getSqlMapClientTemplate().queryForObject("getArticuloXCodigo", art);
        if (articulo == null)
            getSqlMapClientTemplate().insert("insertArticulo", art);
        else
            getSqlMapClientTemplate().update("updateArticulo", art);
        return true;
    }

    /*
     * (non-Javadoc)
     * @see
     * pe.com.jx_market.dao.ArticuloDAO#insertStock(pe.com.jx_market.domain.
     * DTO_Articulo)
     */
    @Override
    public boolean insertStock(DTO_Articulo art)
    {
        getSqlMapClientTemplate().update("updateStock", art);
        return true;
    }

}
