package pe.com.jx_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Modulo;
import pe.com.jx_market.persistence.ModuloMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

@Service
public class ModuloService
    implements BusinessService
{
    @Autowired
    private ModuloMapper moduloMapper;

    @Override
    @Transactional
    public DTO_Output execute(final DTO_Input input)
    {
        final DTO_Output output = new DTO_Output();
        if (Constantes.V_LIST.equals(input.getVerbo())) {
            output.setLista(moduloMapper.getModulos((DTO_Modulo) input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getVerbo())) {
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
        } else if (Constantes.V_GET.equals(input.getVerbo())) {
            final DTO_Modulo mod = moduloMapper.getModuloXCodigo((DTO_Modulo) input.getObject());
            output.setObject(mod);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_DELETE.equals(input.getVerbo())) {
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
