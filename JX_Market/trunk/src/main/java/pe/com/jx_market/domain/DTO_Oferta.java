package pe.com.jx_market.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class DTO_Oferta
    implements Serializable
{
    private Integer id;
    private Integer articuloId;
    private Date desde;
    private Date hasta;
    private BigDecimal monto;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public Integer getArticuloId()
    {
        return articuloId;
    }
    
    public void setArticuloId(Integer articuloId)
    {
        this.articuloId = articuloId;
    }
    
    public Date getDesde()
    {
        return desde;
    }
    
    public void setDesde(Date desde)
    {
        this.desde = desde;
    }
    
    public Date getHasta()
    {
        return hasta;
    }
    
    public void setHasta(Date hasta)
    {
        this.hasta = hasta;
    }
    
    public BigDecimal getMonto()
    {
        return monto;
    }
    
    public void setMonto(BigDecimal monto)
    {
        this.monto = monto;
    }
    
    
}
