package pe.com.jx_market.domain;

public class DTO_Empresa {

    private Integer codigo;
    private String razonsocial;
    private Integer estado;
    private String ruc;
    private String dominio;

    public String getDominio()
    {
        return dominio;
    }

    public void setDominio(String dominio)
    {
        this.dominio = dominio;
    }

    public void setRazonsocial(String razonsocial)
    {
        this.razonsocial = razonsocial;
    }

    public Integer getCodigo () {
        return codigo;
    }

    public void setCodigo (Integer codigo) {
        this.codigo = codigo;
    }

    public String getRazonsocial () {
        return razonsocial;
    }

    public void setRazonSocial (String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public Integer getEstado () {
        return estado;
    }

    public void setEstado (Integer estado) {
        this.estado = estado;
    }

    public String getRuc () {
        return ruc;
    }

    public void setRuc (String ruc) {
        this.ruc = ruc;
    }
}
