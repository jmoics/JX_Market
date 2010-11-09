/**
 * 
 */
package pe.com.jx_market.service;

import java.util.Map;

import pe.com.jx_market.dao.EmpresaDAO;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.domain.DTO_Usuario;
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
    public DTO_Output execute (DTO_Input input) {
        DTO_Output output = new DTO_Output();
        if ("list".equals(input.getVerbo())) {
            String nombre = null;
            Integer ruc = null;
            if (input.getMapa() != null) {
                Map mapa = input.getMapa();
                nombre = (String)mapa.get("nombre");
                ruc = (Integer)mapa.get("ruc");
            }
            output.setLista(dao.getEmpresas(nombre, ruc));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if ("register".equals(input.getVerbo())) {
            DTO_Empresa empresa = (DTO_Empresa) input.getObject();
            dao.registraEmpresa(empresa);
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

    public void setDao (EmpresaDAO dao) {
        this.dao = dao;
    }
    

}
