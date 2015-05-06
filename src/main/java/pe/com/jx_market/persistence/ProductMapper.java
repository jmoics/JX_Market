/**
 *
 */
package pe.com.jx_market.persistence;

import java.util.List;
import java.util.Map;

import pe.com.jx_market.domain.DTO_Category2Product;
import pe.com.jx_market.domain.DTO_Product;
import pe.com.jx_market.domain.DTO_ProductImage;

/**
 * @author George
 *
 */
public interface ProductMapper
{
    public List<DTO_Product> getProducts (Map<? extends Object, ? extends Object> parameterMap);
    public DTO_Product getProduct4Id (DTO_Product art);
    public int insertProduct (DTO_Product art);
    public boolean updateProduct (DTO_Product art);
    public boolean updateStock (DTO_Product art);
    public boolean insertCategory4Product (DTO_Category2Product cat2art);
    public boolean deleteCategory4Product (DTO_Category2Product cat2art);
    public int insertImage4Product (DTO_ProductImage img2art);
    public boolean updateImage4Product (DTO_ProductImage img2art);
    public boolean deleteImage4Product (DTO_ProductImage img2art);
    public int getCategories4Product (DTO_Category2Product art);
}
