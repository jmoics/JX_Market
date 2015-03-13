package pe.com.jx_market.domain;

import java.io.Serializable;

public class DTO_Categoria2Articulo
    implements Serializable
{
    private Integer categoriaId;
    private Integer articuloId;
    
    public Integer getCategoriaId()
    {
        return categoriaId;
    }
    
    public void setCategoriaId(Integer categoriaId)
    {
        this.categoriaId = categoriaId;
    }
    
    public Integer getArticuloId()
    {
        return articuloId;
    }
    
    public void setArticuloId(Integer articuloId)
    {
        this.articuloId = articuloId;
    }
    
    
}
