package pe.com.jx_market.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DTO_Pedido implements Serializable
{
    private Integer codigo;
    private Integer company;
    private Integer client;
    private Integer tipo;
    private Date fecha;
    private BigDecimal igv;
    private BigDecimal total;

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
    public Integer getClient()
    {
        return client;
    }
    public void setClient(final Integer client)
    {
        this.client = client;
    }
    public Integer getTipo()
    {
        return tipo;
    }
    public void setTipo(final Integer tipo)
    {
        this.tipo = tipo;
    }
    public Date getFecha()
    {
        return fecha;
    }
    public void setFecha(final Date fecha)
    {
        this.fecha = fecha;
    }
    public BigDecimal getIgv()
    {
        return igv;
    }
    public void setIgv(final BigDecimal igv)
    {
        this.igv = igv;
    }
    public BigDecimal getTotal()
    {
        return total;
    }
    public void setTotal(final BigDecimal total)
    {
        this.total = total;
    }

}
