package pe.com.jx_market.domain;

import java.io.Serializable;
import java.util.List;


/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public class Currency
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
    private String currencyName;
    /**
     *
     */
    private Boolean curBase;
    /**
     *
     */
    private List<CurrencyRate> currencyRates;

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
     * Getter method for the variable {@link #currencyName}.
     *
     * @return value of variable {@link #currencyName}
     */
    public final String getCurrencyName()
    {
        return this.currencyName;
    }

    /**
     * Setter method for variable {@link #currencyName}.
     *
     * @param _currencyName value for variable {@link #currencyName}
     */
    public final void setCurrencyName(final String _currencyName)
    {
        this.currencyName = _currencyName;
    }

    /**
     * Getter method for the variable {@link #curBase}.
     *
     * @return value of variable {@link #curBase}
     */
    public final Boolean isCurBase()
    {
        return this.curBase;
    }

    /**
     * Setter method for variable {@link #curBase}.
     *
     * @param _curBase value for variable {@link #curBase}
     */
    public final void setCurBase(final Boolean _curBase)
    {
        this.curBase = _curBase;
    }

    /**
     * Getter method for the variable {@link #currencyRates}.
     *
     * @return value of variable {@link #currencyRates}
     */
    public final List<CurrencyRate> getCurrencyRates()
    {
        return this.currencyRates;
    }

    /**
     * Setter method for variable {@link #currencyRates}.
     *
     * @param _currencyRates value for variable {@link #currencyRates}
     */
    public final void setCurrencyRates(final List<CurrencyRate> _currencyRates)
    {
        this.currencyRates = _currencyRates;
    }

}
