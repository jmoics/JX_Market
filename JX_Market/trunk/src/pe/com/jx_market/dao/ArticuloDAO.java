/**
 * 
 */
package pe.com.jx_market.dao;

import java.util.List;

import pe.com.jx_market.domain.DTO_Articulo;

/**
 * @author George
 *
 */
public interface ArticuloDAO
{
    public List<DTO_Articulo> getArticulos (DTO_Articulo art);
    public DTO_Articulo getArticuloXCodigo (DTO_Articulo art);
    public boolean insertArticulo (DTO_Articulo art);
    public boolean insertStock (DTO_Articulo art);
}
