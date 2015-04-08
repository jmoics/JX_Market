package pe.com.jx_market.domain;

import java.io.Serializable;

public class DTO_Modulo
    implements Serializable
{
    private Integer codigo;
    private Integer empresa;
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
