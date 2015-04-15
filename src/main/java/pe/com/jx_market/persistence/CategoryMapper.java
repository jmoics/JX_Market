/**
 * 
 */
package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.DTO_Category;

/**
 * @author George
 * 
 */
public interface CategoryMapper
{
    /**
     * @param categoria
     * @return
     */
    public List<DTO_Category> getCategorias(DTO_Category categoria);
    
    /**
     * @param categoria
     * @return
     */
    public DTO_Category getCategoriaXCodigo(DTO_Category categoria);

    /**
     * @param categoria
     * @return
     */
    public boolean insertCategoria(DTO_Category categoria);
    
    /**
     * @param categoria
     * @return
     */
    public boolean updateCategoria(DTO_Category categoria);

    /**
     * @param categoria
     * @return
     */
    public boolean deleteCategoria(DTO_Category categoria);
}
