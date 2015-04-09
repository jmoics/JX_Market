/**
 *
 */
package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.DTO_Marca;

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
    public List<DTO_Marca> getMarcas(DTO_Marca marca);

    /**
     * @param marca
     * @return
     */
    public DTO_Marca getMarcaXCodigo(DTO_Marca marca);

    /**
     * @param marca
     * @return
     */
    public boolean insertMarca(DTO_Marca marca);

    /**
     * @param marca
     * @return
     */
    public boolean updateMarca(DTO_Marca marca);

    /**
     * @param marca
     * @return
     */
    public boolean deleteMarca(DTO_Marca marca);
}
