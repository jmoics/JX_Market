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

@Service
public class ModuleService
    implements BusinessService<DTO_Module>
{
    @Autowired
    private ModuleMapper moduleMapper;

    @Override
    @Transactional
    public ServiceOutput<DTO_Module> execute(final ServiceInput<DTO_Module> input)
    {
        final ServiceOutput<DTO_Module> output = new ServiceOutput<DTO_Module>();
        if (Constantes.V_LIST.equals(input.getAction())) {
            output.setLista(moduleMapper.getModules(input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getAction())) {
            final DTO_Module modTmp = moduleMapper.getModuleXId(input.getObject());
            if (modTmp == null) {
                moduleMapper.insertModule(input.getObject());
            } else {
                moduleMapper.updateModule(input.getObject());
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getAction())) {
            final DTO_Module mod = moduleMapper.getModuleXId(input.getObject());
            output.setObject(mod);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_DELETE.equals(input.getAction())) {
            moduleMapper.deleteModule(input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    public ModuleMapper getDao()
    {
        return moduleMapper;
    }

    public void setDao(final ModuleMapper moduleMapper)
    {
        this.moduleMapper = moduleMapper;
    }

}
