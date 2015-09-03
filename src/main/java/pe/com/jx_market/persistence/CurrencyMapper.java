package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.Currency;
import pe.com.jx_market.domain.CurrencyRate;

public interface CurrencyMapper
{
    /**
     * @param _currency
     * @return
     */
    public List<Currency> getCurrencies(Currency _currency);

    /**
     * @param _currency
     * @return
     */
    public Currency getCurrency4Id(Currency _currency);

    /**
     * @param _currency
     * @return
     */
    public Integer insertCurrency(Currency _currency);

    /**
     * @param _currency
     * @return
     */
    public Integer updateCurrency(Currency _currency);

    /**
     * @param _currency
     * @return
     */
    public boolean deleteCurrency(Currency _currency);

    /**
     * @param _currencyRate
     * @return
     */
    public Integer insertCurrencyRate(CurrencyRate _currencyRate);

    /**
     * @param _currency
     * @return
     */
    public Integer updateCurrencyRate(CurrencyRate _currency);
}
