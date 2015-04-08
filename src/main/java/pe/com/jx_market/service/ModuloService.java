package pe.com.jx_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Modulo;
import pe.com.jx_market.persistence.ModuloMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

@Service
public class ModuloService
    implements BusinessService
{
    @Autowired
    private ModuloMapper moduloMapper;

    @Override
    @Transactional
    public ServiceOutput execute(final ServiceInput input)
    {
        final ServiceOutput output = new ServiceOutput();
        if (Constantes.V_LIST.equals(input.getAccion())) {
            output.setLista(moduloMapper.getModulos((DTO_Modulo) input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getAccion())) {
            DTO_Modulo modTmp = moduloMapper.getModuloXCodigo((DTO_Modulo) input.getObject());
            if (modTmp == null) {
                final Integer cod = moduloMapper.insertModulo((DTO_Modulo) input.getObject());
                output.setObject(cod);
            } else {
                moduloMapper.updateModulo((DTO_Modulo) input.getObject());
                output.setObject(new Integer(-1));
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getAccion())) {
            final DTO_Modulo mod = moduloMapper.getModuloXCodigo((DTO_Modulo) input.getObject());
            output.setObject(mod);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_DELETE.equals(input.getAccion())) {
            moduloMapper.deleteModulo((DTO_Modulo) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    public ModuloMapper getDao()
    {
        return moduloMapper;
    }

    public void setDao(final ModuloMapper moduloMapper)
    {
        this.moduloMapper = moduloMapper;
    }

}
