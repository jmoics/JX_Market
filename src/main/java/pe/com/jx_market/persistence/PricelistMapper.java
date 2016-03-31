package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.PricelistCost;
import pe.com.jx_market.domain.PricelistRetail;

public interface PricelistMapper<E>
{
    /**
     * @param _pricelist
     * @return
     */
    public List<PricelistRetail> getPricelistRetail(E _pricelist);

    /**
     * @param _pricelist
     * @return
     */
    public List<PricelistCost> getPricelistCost(E _pricelist);

    /**
     * @param _pricelist
     * @return
     */
    public E getPricelist4Id(E _pricelist);

    /**
     * @param _pricelist
     * @return
     */
    public Integer insertPricelist(E _pricelist);

    /**
     * @param _pricelist
     * @return
     */
    public Integer insertPricelistRetail(PricelistRetail _pricelist);

    /**
     * @param _pricelist
     * @return
     */
    public Integer insertPricelistCost(PricelistCost _pricelist);

    /**
     * @param _pricelist
     * @return
     */
    public Integer updatePricelist(E _pricelist);

    /**
     * @param _pricelist
     * @return
     */
    // public Integer updatePricelistRetail(PricelistRetail _pricelist);

    /**
     * @param _pricelist
     * @return
     */
    // public Integer updatePricelistCost(PricelistRetail _pricelist);


    /**
     * @param _pricelist
     * @return
     */
    public boolean deletePricelist(E _pricelist);
}
