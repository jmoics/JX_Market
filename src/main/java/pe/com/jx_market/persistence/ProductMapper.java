/**
 *
 */
package pe.com.jx_market.persistence;

import java.util.List;
import java.util.Map;

import pe.com.jx_market.domain.DTO_Categoria2Articulo;
import pe.com.jx_market.domain.DTO_Product;
import pe.com.jx_market.domain.DTO_ProductImage;

/**
 * @author George
 *
 */
public interface ProductMapper
{
    public List<DTO_Product> getArticulos (Map<Object, Object> parameterMap);
    public DTO_Product getArticuloXCodigo (DTO_Product art);
    public int insertArticulo (DTO_Product art);
    public boolean updateArticulo (DTO_Product art);
    public boolean updateStock (DTO_Product art);
    public boolean insertCategory4Product (DTO_Categoria2Articulo cat2art);
    public boolean deleteCategory4Product (DTO_Categoria2Articulo cat2art);
    public int insertImage4Product (DTO_ProductImage img2art);
    public boolean updateImage4Product (DTO_ProductImage img2art);
    public boolean deleteImage4Product (DTO_ProductImage img2art);
    public int getCategories4Product (DTO_Categoria2Articulo art);
}
