package pe.com.jx_market.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import org.joda.time.DateTime;

/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public abstract class AbstractPricelist
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
    private DateTime validFrom;
    /**
     *
     */
    private DateTime validTo;
    /**
     *
     */
    private BigDecimal price;
    /**
     *
     */
    private Currency currency;

    /**
     * Getter method for the variable {@link #id}.
     *
     * @return value of variable {@link #id}
     */
    public final Integer getId()
    {
        return this.id;
    }

    /**
     * Setter method for variable {@link #id}.
     *
     * @param _id value for variable {@link #id}
     */
    public final void setId(final Integer _id)
    {
        this.id = _id;
    }

    /**
     * Getter method for the variable {@link #productId}.
     *
     * @return value of variable {@link #productId}
     */
    public final Integer getProductId()
    {
        return this.productId;
    }

    /**
     * Setter method for variable {@link #productId}.
     *
     * @param _productId value for variable {@link #productId}
     */
    public final void setProductId(final Integer _productId)
    {
        this.productId = _productId;
    }

    /**
     * Getter method for the variable {@link #validFrom}.
     *
     * @return value of variable {@link #validFrom}
     */
    public final DateTime getValidFrom()
    {
        return this.validFrom;
    }

    /**
     * Setter method for variable {@link #validFrom}.
     *
     * @param _validFrom value for variable {@link #validFrom}
     */
    public final void setValidFrom(final DateTime _validFrom)
    {
        this.validFrom = _validFrom;
    }

    /**
     * Getter method for the variable {@link #validTo}.
     *
     * @return value of variable {@link #validTo}
     */
    public final DateTime getValidTo()
    {
        return this.validTo;
    }

    /**
     * Setter method for variable {@link #validTo}.
     *
     * @param _validTo value for variable {@link #validTo}
     */
    public final void setValidTo(final DateTime _validTo)
    {
        this.validTo = _validTo;
    }

    /**
     * Getter method for the variable {@link #price}.
     *
     * @return value of variable {@link #price}
     */
    public final BigDecimal getPrice()
    {
        return this.price;
    }

    /**
     * Setter method for variable {@link #price}.
     *
     * @param _price value for variable {@link #price}
     */
    public final void setPrice(final BigDecimal _price)
    {
        this.price = _price;
    }

    /**
     * Getter method for the variable {@link #currency}.
     *
     * @return value of variable {@link #currency}
     */
    public final Currency getCurrency()
    {
        return this.currency;
    }

    /**
     * Setter method for variable {@link #currency}.
     *
     * @param _currency value for variable {@link #currency}
     */
    public final void setCurrency(final Currency _currency)
    {
        this.currency = _currency;
    }
}
