package pe.com.jx_market.dao;

import java.util.List;

import pe.com.jx_market.domain.DTO_Modulo;

public interface ModuloDAO
{
    /**
     * @param modulo
     * @return
     */
    public List<DTO_Modulo> getModulos(DTO_Modulo modulo);

    /**
     * @param modulo
     * @return
     */
    public DTO_Modulo getModuloXCodigo(DTO_Modulo modulo);

    /**
     * @param modulo
     * @return
     */
    public Integer insertModulo(DTO_Modulo modulo);

    /**
     * @param modulo
     * @return
     */
    public boolean deleteModulo(DTO_Modulo modulo);
}
