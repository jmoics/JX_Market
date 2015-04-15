/**
 *
 */
package pe.com.jx_market.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.persistence.CompanyMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * @author George
 *
 */
@Service
public class CompanyService 
    implements BusinessService 
{
    @Autowired
    private CompanyMapper companyMapper;

    /* (non-Javadoc)
     * @see pe.com.jx_market.utilities.BusinessService#execute(pe.com.jx_market.utilities.ServiceInput)
     */
    @Override
    @Transactional
    public ServiceOutput execute (final ServiceInput input) {
        final ServiceOutput output = new ServiceOutput();
        if ("list".equals(input.getAccion())) {
            String nombre = null;
            String ruc = null;
            if (input.getMapa() != null) {
                final Map mapa = input.getMapa();
                nombre = mapa.containsKey("nombre") ? (String)mapa.get("nombre") : null;
                ruc = mapa.containsKey("ruc") ? (String)mapa.get("ruc") : null;
            }
            output.setLista(companyMapper.getCompanies(nombre, ruc));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getAccion())) {
            final DTO_Company company = (DTO_Company) input.getObject();
            DTO_Company empTmp = companyMapper.getCompany4DocNumber(company);
            if (empTmp == null) {
                final Integer cod = companyMapper.insertCompany(company);
                output.setObject(cod);
            } else {
                companyMapper.updateCompany(company);
                output.setObject(new Integer(-1));
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if ("delete".equals(input.getAccion())) {
            companyMapper.deleteCompany((String) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    public CompanyMapper getDao () {
        return companyMapper;
    }

    public void setDao (final CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }


}
