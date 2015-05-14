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
public interface CompanyMapper
{

    /**
     * Metodo para obtener una company a partir de su codigo.
     *
     * @param _company company.
     * @return DTO_Company con la data de la company.
     */
    DTO_Company getCompany4DocNumber(DTO_Company _company);

    /**
     * @param _company company.
     * @return boolean with delete confirmation
     */
    boolean deleteCompany(DTO_Company _company);

    /**
     * @param _company company.
     * @return boolean with insert confirmation
     */
    boolean insertCompany(DTO_Company _company);

    /**
     * @param _company company.
     * @return boolean with update confirmation
     */
    boolean updateCompany(DTO_Company _company);

    /**
     * @param _businessName Name of Company.
     * @param _docNumber Document Number.
     * @return List of companies.
     */
    List<DTO_Company> getCompanies(@Param("businessName") String _businessName,
                                   @Param("docNumber") String _docNumber);
}
