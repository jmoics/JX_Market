/**
 *
 */
package pe.com.jx_market.persistence;

import java.util.List;
import java.util.Map;

import pe.com.jx_market.domain.DTO_Articulo;
import pe.com.jx_market.domain.DTO_Categoria2Articulo;

/**
 * @author George
 *
 */
public interface ProductMapper
{
    public List<DTO_Articulo> getArticulos (Map<Object, Object> parameterMap);
    public DTO_Articulo getArticuloXCodigo (DTO_Articulo art);
    public boolean insertArticulo (DTO_Articulo art);
    public boolean updateArticulo (DTO_Articulo art);
    public boolean updateStock (DTO_Articulo art);
    public boolean insertCategory4Product (DTO_Categoria2Articulo cat2art);
    public boolean deleteCategory4Product (DTO_Categoria2Articulo cat2art);
    public int getCategories4Product (DTO_Categoria2Articulo art);
}
