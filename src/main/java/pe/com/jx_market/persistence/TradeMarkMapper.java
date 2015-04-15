/**
 *
 */
package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.DTO_TradeMark;

/**
 * @author George
 *
 */
public interface TradeMarkMapper
{
    /**
     * @param tradeMark
     * @return
     */
    public List<DTO_TradeMark> getTradeMarks(DTO_TradeMark tradeMark);

    /**
     * @param tradeMark
     * @return
     */
    public DTO_TradeMark getTradeMark4Id(DTO_TradeMark tradeMark);

    /**
     * @param tradeMark
     * @return
     */
    public boolean insertTradeMark(DTO_TradeMark tradeMark);

    /**
     * @param tradeMark
     * @return
     */
    public boolean updateTradeMark(DTO_TradeMark tradeMark);

    /**
     * @param tradeMark
     * @return
     */
    public boolean deleteTradeMark(DTO_TradeMark tradeMark);
}
