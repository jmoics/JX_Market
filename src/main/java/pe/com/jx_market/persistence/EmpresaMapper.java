/**
 *
 */
package pe.com.jx_market.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pe.com.jx_market.domain.DTO_Empresa;

/**
 * @author George
 *
 */
public interface EmpresaMapper {

    /**
     * Metodo para obtener una empresa a partir de su codigo.
     *
     * @param codigo codigo de la empresa.
     * @return DTO_Empresa con la data de la empresa.
     */
    public DTO_Empresa getEmpresaXRuc (DTO_Empresa empresa);

    /**
     * @param codigo
     * @return
     */
    public boolean deleteEmpresa (String codigo);

    /**
     * @param empresa
     * @return
     */
    public Integer insertEmpresa (DTO_Empresa empresa);
    
    /**
     * @param empresa
     * @return
     */
    public Integer updateEmpresa (DTO_Empresa empresa);

    /**
     * @return
     */
    public List<DTO_Empresa> getEmpresas (@Param("razonsocial") String razonsocial, 
                                          @Param("ruc")String ruc);
}
