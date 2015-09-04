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
public class CurrencyRate
    implements Serializable
{
    /**
    *
    */
    private Integer id;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private Integer currencyId;
    /**
     *
     */
    private DateTime fromDate;
    /**
     *
     */
    private DateTime toDate;
    /**
     *
     */
    private BigDecimal rate;

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
     * Getter method for the variable {@link #companyId}.
     *
     * @return value of variable {@link #companyId}
     */
    public final Integer getCompanyId()
    {
        return this.companyId;
    }

    /**
     * Setter method for variable {@link #companyId}.
     *
     * @param _companyId value for variable {@link #companyId}
     */
    public final void setCompanyId(final Integer _companyId)
    {
        this.companyId = _companyId;
    }

    /**
     * Getter method for the variable {@link #currencyId}.
     *
     * @return value of variable {@link #currencyId}
     */
    public final Integer getCurrencyId()
    {
        return this.currencyId;
    }

    /**
     * Setter method for variable {@link #currencyId}.
     *
     * @param _currencyId value for variable {@link #currencyId}
     */
    public final void setCurrencyId(final Integer _currencyId)
    {
        this.currencyId = _currencyId;
    }

    /**
     * Getter method for the variable {@link #fromDate}.
     *
     * @return value of variable {@link #fromDate}
     */
    public final DateTime getFromDate()
    {
        return this.fromDate;
    }

    /**
     * Setter method for variable {@link #fromDate}.
     *
     * @param _fromDate value for variable {@link #fromDate}
     */
    public final void setFromDate(final DateTime _fromDate)
    {
        this.fromDate = _fromDate;
    }

    /**
     * Getter method for the variable {@link #toDate}.
     *
     * @return value of variable {@link #toDate}
     */
    public final DateTime getToDate()
    {
        return this.toDate;
    }

    /**
     * Setter method for variable {@link #toDate}.
     *
     * @param _toDate value for variable {@link #toDate}
     */
    public final void setToDate(final DateTime _toDate)
    {
        this.toDate = _toDate;
    }

    /**
     * Getter method for the variable {@link #rate}.
     *
     * @return value of variable {@link #rate}
     */
    public final BigDecimal getRate()
    {
        return this.rate;
    }

    /**
     * Setter method for variable {@link #rate}.
     *
     * @param _rate value for variable {@link #rate}
     */
    public final void setRateAmount(final BigDecimal _rate)
    {
        this.rate = _rate;
    }


}
