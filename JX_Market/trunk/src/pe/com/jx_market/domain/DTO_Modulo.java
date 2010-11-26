package pe.com.jx_market.domain;

import java.io.Serializable;

public class DTO_Modulo
    implements Serializable
{
    private Integer modulo;
    private Integer empresa;
    private String descripcion;

    public Integer getModulo()
    {
        return modulo;
    }

    public void setModulo(final Integer modulo)
    {
        this.modulo = modulo;
    }

    public Integer getEmpresa()
    {
        return empresa;
    }

    public void setEmpresa(final Integer empresa)
    {
        this.empresa = empresa;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(final String descripcion)
    {
        this.descripcion = descripcion;
    }

}
