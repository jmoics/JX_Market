package pe.com.jx_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Module;
import pe.com.jx_market.persistence.ModuleMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
@Service
public class ModuleService
    implements BusinessService<DTO_Module>
{
    /**
     *
     */
    @Autowired
    private ModuleMapper moduleMapper;

    @Override
    @Transactional
    public ServiceOutput<DTO_Module> execute(final ServiceInput<DTO_Module> _input)
    {
        final ServiceOutput<DTO_Module> output = new ServiceOutput<DTO_Module>();
        if (Constantes.V_LIST.equals(_input.getAction())) {
            output.setLista(this.moduleMapper.getModules(_input.getObject()));
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_REGISTER.equals(_input.getAction())) {
            final DTO_Module modTmp = this.moduleMapper.getModuleXId(_input.getObject());
            if (modTmp == null) {
                this.moduleMapper.insertModule(_input.getObject());
            } else {
                this.moduleMapper.updateModule(_input.getObject());
            }
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_GET.equals(_input.getAction())) {
            final DTO_Module mod = this.moduleMapper.getModuleXId(_input.getObject());
            output.setObject(mod);
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_DELETE.equals(_input.getAction())) {
            this.moduleMapper.deleteModule(_input.getObject());
            output.setErrorCode(Constantes.OK);
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
        return output;
    }
}
