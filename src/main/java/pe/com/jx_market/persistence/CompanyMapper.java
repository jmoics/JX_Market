/**
 *
 */
package pe.com.jx_market.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pe.com.jx_market.domain.DTO_Company;

/**
 * @author George
 *
 */
public interface CompanyMapper {

    /**
     * Metodo para obtener una company a partir de su codigo.
     *
     * @param codigo codigo de la company.
     * @return DTO_Company con la data de la company.
     */
    public DTO_Company getCompany4DocNumber (DTO_Company company);

    /**
     * @param codigo
     * @return
     */
    public boolean deleteCompany (String codigo);

    /**
     * @param company
     * @return
     */
    public Integer insertCompany (DTO_Company company);

    /**
     * @param company
     * @return
     */
    public Integer updateCompany (DTO_Company company);

    /**
     * @return
     */
    public List<DTO_Company> getCompanies (@Param("businessName") String businessName,
                                          @Param("docNumber")String docNumber);
}
