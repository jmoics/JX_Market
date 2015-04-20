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
    implements BusinessService
{
    @Autowired
    private ModuleMapper moduleMapper;

    @Override
    @Transactional
    public ServiceOutput execute(final ServiceInput input)
    {
        final ServiceOutput output = new ServiceOutput();
        if (Constantes.V_LIST.equals(input.getAccion())) {
            output.setLista(moduleMapper.getModules((DTO_Module) input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getAccion())) {
            final DTO_Module modTmp = moduleMapper.getModuleXId((DTO_Module) input.getObject());
            if (modTmp == null) {
                final Integer cod = moduleMapper.insertModule((DTO_Module) input.getObject());
                output.setObject(cod);
            } else {
                moduleMapper.updateModule((DTO_Module) input.getObject());
                output.setObject(new Integer(-1));
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getAccion())) {
            final DTO_Module mod = moduleMapper.getModuleXId((DTO_Module) input.getObject());
            output.setObject(mod);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_DELETE.equals(input.getAccion())) {
            moduleMapper.deleteModule((DTO_Module) input.getObject());
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
