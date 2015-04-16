package pe.com.jx_market.domain;

import java.io.Serializable;

public class DTO_Perfil
    implements Serializable
{
    private Integer codigo;
    private Integer company;
    private Integer area;
    private String funcion;
    private String descripcion;

    public Integer getCodigo()
    {
        return codigo;
    }

    public void setCodigo(final Integer codigo)
    {
        this.codigo = codigo;
    }

    public Integer getCompany()
    {
        return company;
    }

    public void setCompany(final Integer company)
    {
        this.company = company;
    }

    public Integer getArea()
    {
        return area;
    }

    public void setArea(final Integer area)
    {
        this.area = area;
    }

    public String getFuncion()
    {
        return funcion;
    }

    public void setFuncion(final String funcion)
    {
        this.funcion = funcion;
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
