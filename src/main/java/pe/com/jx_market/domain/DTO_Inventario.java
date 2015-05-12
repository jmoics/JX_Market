package pe.com.jx_market.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jcuevas
 *
 */
public class DTO_Inventario
    implements Serializable
{

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Integer productId;
    /**
     *
     */
    private BigDecimal stock;
    /**
     *
     */
    private BigDecimal reserva;
    /**
     *
     */
    private Date fecha;

    /**
     * @return the id
     */
    public final Integer getId()
    {
        return this.id;
    }

    /**
     * @param _id the id to set
     */
    public final void setId(final Integer _id)
    {
        this.id = _id;
    }

    /**
     * @return the productId
     */
    public final Integer getProductId()
    {
        return this.productId;
    }

    /**
     * @param _productId the productId to set
     */
    public final void setProductId(final Integer _productId)
    {
        this.productId = _productId;
    }

    /**
     * @return the stock
     */
    public final BigDecimal getStock()
    {
        return this.stock;
    }

    /**
     * @param _stock the stock to set
     */
    public final void setStock(final BigDecimal _stock)
    {
        this.stock = _stock;
    }

    /**
     * @return the reserva
     */
    public final BigDecimal getReserva()
    {
        return this.reserva;
    }

    /**
     * @param _reserva the reserva to set
     */
    public final void setReserva(final BigDecimal _reserva)
    {
        this.reserva = _reserva;
    }

    /**
     * @return the fecha
     */
    public final Date getFecha()
    {
        return this.fecha;
    }

    /**
     * @param _fecha the fecha to set
     */
    public final void setFecha(final Date _fecha)
    {
        this.fecha = _fecha;
    }


}
