/**
 *
 */
package pe.com.jx_market.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.persistence.EmpresaMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

/**
 * @author George
 *
 */
@Service
public class EmpresaService 
    implements BusinessService 
{
    @Autowired
    private EmpresaMapper empresaMapper;

    /* (non-Javadoc)
     * @see pe.com.jx_market.utilities.BusinessService#execute(pe.com.jx_market.utilities.DTO_Input)
     */
    @Override
    @Transactional
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
            output.setLista(empresaMapper.getEmpresas(nombre, ruc));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getVerbo())) {
            final DTO_Empresa empresa = (DTO_Empresa) input.getObject();
            DTO_Empresa empTmp = empresaMapper.getEmpresaXRuc(empresa);
            if (empTmp == null) {
                final Integer cod = empresaMapper.insertEmpresa(empresa);
                output.setObject(cod);
            } else {
                empresaMapper.updateEmpresa(empresa);
                output.setObject(new Integer(-1));
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if ("delete".equals(input.getVerbo())) {
            empresaMapper.deleteEmpresa((String) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    public EmpresaMapper getDao () {
        return empresaMapper;
    }

    public void setDao (final EmpresaMapper empresaMapper) {
        this.empresaMapper = empresaMapper;
    }


}
