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
    implements BusinessService<DTO_Company>
{

    /**
     *
     */
    @Autowired
    private CompanyMapper companyMapper;

    /*
     * (non-Javadoc)
     * @see
     * pe.com.jx_market.utilities.BusinessService#execute(pe.com.jx_market.utilities
     * .ServiceInput)
     */
    @Override
    @Transactional
    public ServiceOutput<DTO_Company> execute(final ServiceInput<DTO_Company> _input)
    {
        final ServiceOutput<DTO_Company> output = new ServiceOutput<DTO_Company>();
        if ("list".equals(_input.getAction())) {
            String nombre = null;
            String ruc = null;
            if (_input.getMapa() != null) {
                final Map mapa = _input.getMapa();
                nombre = mapa.containsKey("nombre") ? (String) mapa.get("nombre") : null;
                ruc = mapa.containsKey("ruc") ? (String) mapa.get("ruc") : null;
            }
            output.setLista(this.companyMapper.getCompanies(nombre, ruc));
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_REGISTER.equals(_input.getAction())) {
            final DTO_Company company = _input.getObject();
            final DTO_Company empTmp = this.companyMapper.getCompany4DocNumber(company);
            if (empTmp == null) {
                this.companyMapper.insertCompany(company);
            } else {
                this.companyMapper.updateCompany(company);
            }
            output.setErrorCode(Constantes.OK);
        } else if ("delete".equals(_input.getAction())) {
            this.companyMapper.deleteCompany(_input.getObject());
            output.setErrorCode(Constantes.OK);
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
        return output;
    }
}
