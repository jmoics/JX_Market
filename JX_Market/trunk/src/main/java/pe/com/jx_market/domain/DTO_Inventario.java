package pe.com.jx_market.domain;

import java.math.BigDecimal;
import java.util.Date;


public class DTO_Inventario
{
    private Integer id;
    private Integer articuloId;
    private BigDecimal stock;
    private BigDecimal reserva;
    private Date fecha;
    
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
    
    public BigDecimal getStock()
    {
        return stock;
    }
    
    public void setStock(BigDecimal stock)
    {
        this.stock = stock;
    }
    
    public BigDecimal getReserva()
    {
        return reserva;
    }
    
    public void setReserva(BigDecimal reserva)
    {
        this.reserva = reserva;
    }
    
    public Date getFecha()
    {
        return fecha;
    }
    
    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }
}
