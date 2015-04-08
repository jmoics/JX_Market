package pe.com.jx_market.domain;

import java.io.Serializable;

public class DTO_Empresa implements Serializable {

    private Integer codigo;
    private String razonsocial;
    private Integer estado;
    private String ruc;
    private String dominio;

    public Integer getCodigo()
    {
        return codigo;
    }
    public void setCodigo(final Integer codigo)
    {
        this.codigo = codigo;
    }
    public String getRazonsocial()
    {
        return razonsocial;
    }
    public void setRazonsocial(final String razonsocial)
    {
        this.razonsocial = razonsocial;
    }
    public Integer getEstado()
    {
        return estado;
    }
    public void setEstado(final Integer estado)
    {
        this.estado = estado;
    }
    public String getRuc()
    {
        return ruc;
    }
    public void setRuc(final String ruc)
    {
        this.ruc = ruc;
    }
    public String getDominio()
    {
        return dominio;
    }
    public void setDominio(final String dominio)
    {
        this.dominio = dominio;
    }
}
