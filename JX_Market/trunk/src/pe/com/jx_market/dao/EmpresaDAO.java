/**
 * 
 */
package pe.com.jx_market.dao;

import java.util.List;

import pe.com.jx_market.domain.DTO_Cliente;
import pe.com.jx_market.domain.DTO_Empresa;

/**
 * @author George
 *
 */
public interface EmpresaDAO {

    /**
     * Metodo para obtener una empresa a partir de su codigo.
     * 
     * @param codigo codigo de la empresa.
     * @return DTO_Empresa con la data de la empresa.
     */
    public DTO_Empresa getEmpresaXCodigo (String codigo);

    /**
     * @param codigo
     * @return
     */
    public boolean eliminaEmpresa (String codigo);

    /**
     * @param empresa
     * @return
     */
    public boolean registraEmpresa (DTO_Empresa empresa);

    /**
     * @return
     */
    public List<DTO_Empresa> getEmpresas (String nombre, Integer ruc);
}
