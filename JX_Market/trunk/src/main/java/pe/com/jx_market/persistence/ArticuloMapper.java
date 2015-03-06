/**
 * 
 */
package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.DTO_Articulo;

/**
 * @author George
 *
 */
public interface ArticuloMapper
{
    public List<DTO_Articulo> getArticulos (DTO_Articulo art);
    public DTO_Articulo getArticuloXCodigo (DTO_Articulo art);
    public boolean insertArticulo (DTO_Articulo art);
    public boolean updateArticulo (DTO_Articulo art);
    public boolean updateStock (DTO_Articulo art);
}