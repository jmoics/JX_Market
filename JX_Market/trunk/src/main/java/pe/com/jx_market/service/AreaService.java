package pe.com.jx_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Area;
import pe.com.jx_market.persistence.AreaMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

@Service
public class AreaService
    implements BusinessService
{
    @Autowired
    private AreaMapper areaMapper;

    @Transactional
    @Override
    public DTO_Output execute(final DTO_Input input)
    {
        final DTO_Output output = new DTO_Output();
        if (Constantes.V_LIST.equals(input.getVerbo())) {
            output.setLista(areaMapper.getAreas((DTO_Area) input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getVerbo())) {
            DTO_Area areaTmp = areaMapper.getAreaXCodigo((DTO_Area) input.getObject());
            if (areaTmp == null) {
                final Integer cod = areaMapper.insertArea((DTO_Area) input.getObject());
                output.setObject(cod);
            } else {
                areaMapper.updateArea((DTO_Area) input.getObject());
                output.setObject(new Integer(-1));
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getVerbo())) {
            final DTO_Area art = areaMapper.getAreaXCodigo((DTO_Area) input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_DELETE.equals(input.getVerbo())) {
            areaMapper.deleteArea((DTO_Area) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    public AreaMapper getDao()
    {
        return areaMapper;
    }

    public void setDao(final AreaMapper areaMapper)
    {
        this.areaMapper = areaMapper;
    }

}
