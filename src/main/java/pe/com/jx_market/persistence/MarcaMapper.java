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
public interface MarcaMapper
{
    /**
     * @param marca
     * @return
     */
    public List<DTO_TradeMark> getMarcas(DTO_TradeMark marca);

    /**
     * @param marca
     * @return
     */
    public DTO_TradeMark getMarcaXCodigo(DTO_TradeMark marca);

    /**
     * @param marca
     * @return
     */
    public boolean insertMarca(DTO_TradeMark marca);

    /**
     * @param marca
     * @return
     */
    public boolean updateMarca(DTO_TradeMark marca);

    /**
     * @param marca
     * @return
     */
    public boolean deleteMarca(DTO_TradeMark marca);
}
