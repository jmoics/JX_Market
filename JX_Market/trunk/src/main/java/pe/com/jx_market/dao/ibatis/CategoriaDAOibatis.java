/**
 * 
 */
package pe.com.jx_market.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.jx_market.dao.CategoriaDAO;
import pe.com.jx_market.domain.DTO_Articulo;
import pe.com.jx_market.domain.DTO_Categoria;

/**
 * @author George
 *
 */
public class CategoriaDAOibatis extends SqlMapClientDaoSupport implements CategoriaDAO
{

    /* (non-Javadoc)
     * @see pe.com.jx_market.dao.CategoriaDAO#getCategorias(pe.com.jx_market.domain.DTO_Categoria)
     */
    @Override
    public List<DTO_Categoria> getCategorias(DTO_Categoria categoria)
    {
        return (List<DTO_Categoria>) getSqlMapClientTemplate().queryForList("getCategorias", categoria);
    }

    /* (non-Javadoc)
     * @see pe.com.jx_market.dao.CategoriaDAO#insertCategoria(pe.com.jx_market.domain.DTO_Categoria)
     */
    @Override
    public boolean insertCategoria(DTO_Categoria categoria)
    {
        final DTO_Categoria articulo = (DTO_Categoria) getSqlMapClientTemplate().queryForObject("getCategoriaXCodigo", categoria);
        if (articulo == null)
            getSqlMapClientTemplate().insert("insertCategoria", categoria);
        else
            getSqlMapClientTemplate().update("updateCategoria", categoria);
        return true;
    }

    /* (non-Javadoc)
     * @see pe.com.jx_market.dao.CategoriaDAO#deleteCategoria(pe.com.jx_market.domain.DTO_Categoria)
     */
    @Override
    public boolean deleteCategoria(DTO_Categoria categoria)
    {
        getSqlMapClientTemplate().delete("deleteCategoria", categoria);
        return true;
    }

    /* (non-Javadoc)
     * @see pe.com.jx_market.dao.CategoriaDAO#getCategoriaXCodigo(pe.com.jx_market.domain.DTO_Categoria)
     */
    @Override
    public DTO_Categoria getCategoriaXCodigo(DTO_Categoria categoria)
    {
        return (DTO_Categoria)getSqlMapClientTemplate().queryForObject("getCategoriaXCodigo", categoria);
    }
}
