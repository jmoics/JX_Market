/**
 *
 */
package pe.com.jx_market.service;

import java.util.Map;

import pe.com.jx_market.dao.EmpresaDAO;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

/**
 * @author George
 *
 */
public class EmpresaService implements BusinessService {

    private EmpresaDAO dao;

    /* (non-Javadoc)
     * @see pe.com.jx_market.utilities.BusinessService#execute(pe.com.jx_market.utilities.DTO_Input)
     */
    @Override
    public DTO_Output execute (final DTO_Input input) {
        final DTO_Output output = new DTO_Output();
        if ("list".equals(input.getVerbo())) {
            String nombre = null;
            String ruc = null;
            if (input.getMapa() != null) {
                final Map mapa = input.getMapa();
                nombre = mapa.containsKey("nombre") ? (String)mapa.get("nombre") : null;
                ruc = mapa.containsKey("ruc") ? (String)mapa.get("ruc") : null;
            }
            output.setLista(dao.getEmpresas(nombre, ruc));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getVerbo())) {
            final DTO_Empresa empresa = (DTO_Empresa) input.getObject();
            final Integer cod = dao.registraEmpresa(empresa);
            output.setObject(cod);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if ("delete".equals(input.getVerbo())) {
            dao.eliminaEmpresa((String) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    public EmpresaDAO getDao () {
        return dao;
    }

    public void setDao (final EmpresaDAO dao) {
        this.dao = dao;
    }


}
