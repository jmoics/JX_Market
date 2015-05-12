package pe.com.jx_market.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DTO_Costo
    implements Serializable
{

    private Integer id;
    private Integer productId;
    private Date desde;
    private Date hasta;
    private BigDecimal monto;

    public Integer getId()
    {
        return id;
    }

    public void setId(final Integer id)
    {
        this.id = id;
    }

    public Integer getProductId()
    {
        return productId;
    }

    public void setProductId(final Integer productId)
    {
        this.productId = productId;
    }

    public Date getDesde()
    {
        return desde;
    }

    public void setDesde(final Date desde)
    {
        this.desde = desde;
    }

    public Date getHasta()
    {
        return hasta;
    }

    public void setHasta(final Date hasta)
    {
        this.hasta = hasta;
    }

    public BigDecimal getMonto()
    {
        return monto;
    }

    public void setMonto(final BigDecimal monto)
    {
        this.monto = monto;
    }

}
