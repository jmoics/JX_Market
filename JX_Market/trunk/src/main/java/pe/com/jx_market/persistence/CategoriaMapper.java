/**
 * 
 */
package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.DTO_Categoria;

/**
 * @author George
 * 
 */
public interface CategoriaMapper
{
    /**
     * @param categoria
     * @return
     */
    public List<DTO_Categoria> getCategorias(DTO_Categoria categoria);
    
    /**
     * @param categoria
     * @return
     */
    public DTO_Categoria getCategoriaXCodigo(DTO_Categoria categoria);

    /**
     * @param categoria
     * @return
     */
    public boolean insertCategoria(DTO_Categoria categoria);
    
    /**
     * @param categoria
     * @return
     */
    public boolean updateCategoria(DTO_Categoria categoria);

    /**
     * @param categoria
     * @return
     */
    public boolean deleteCategoria(DTO_Categoria categoria);
}
