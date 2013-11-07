package pe.com.jx_market.domain;

import java.io.Serializable;

public class DTO_Area implements Serializable
{
    private Integer codigo;
    private Integer empresa;
    private String nombre;
    
    public Integer getCodigo()
    {
        return codigo;
    }
    public void setCodigo(final Integer codigo)
    {
        this.codigo = codigo;
    }
    public Integer getEmpresa()
    {
        return empresa;
    }
    public void setEmpresa(final Integer empresa)
    {
        this.empresa = empresa;
    }
    public String getNombre()
    {
        return nombre;
    }
    public void setNombre(final String nombre)
    {
        this.nombre = nombre;
    }
    
    
}
