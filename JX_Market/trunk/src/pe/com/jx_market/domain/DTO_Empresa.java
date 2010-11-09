package pe.com.jx_market.domain;

public class DTO_Empresa {

    private String codigo;
    private String razonsocial;
    private Integer estado;
    private String ruc;

    public String getCodigo () {
        return codigo;
    }

    public void setCodigo (String codigo) {
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
