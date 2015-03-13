package pe.com.jx_market.domain;

import java.io.Serializable;

public class DTO_Atributo
    implements Serializable
{
    private Integer id;
    private String nombre;
    private String descr;
    private Boolean activo;
    private Boolean filtrable;
    private Integer categoriaId;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getNombre()
    {
        return nombre;
    }
    
    public String getDescr()
    {
        return descr;
    }
    
    public void setDescr(String descr)
    {
        this.descr = descr;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    public Boolean getActivo()
    {
        return activo;
    }
    
    public void setActivo(Boolean activo)
    {
        this.activo = activo;
    }
    
    public Boolean getFiltrable()
    {
        return filtrable;
    }
    
    public void setFiltrable(Boolean filtrable)
    {
        this.filtrable = filtrable;
    }

    
    public Integer getCategoriaId()
    {
        return categoriaId;
    }

    
    public void setCategoriaId(Integer categoriaId)
    {
        this.categoriaId = categoriaId;
    }
}
