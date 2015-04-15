package pe.com.jx_market.domain;

import java.io.Serializable;

public class DTO_Modulo
    implements Serializable
{
    private Integer codigo;
    private Integer company;
    private String descripcion;
    private String recurso;

    public Integer getCodigo()
    {
        return codigo;
    }

    public void setCodigo(final Integer codigo)
    {
        this.codigo = codigo;
    }

    public String getRecurso()
    {
        return recurso;
    }

    public void setRecurso(final String recurso)
    {
        this.recurso = recurso;
    }

    public Integer getEmpresa()
    {
        return company;
    }

    public void setEmpresa(final Integer company)
    {
        this.company = company;
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
