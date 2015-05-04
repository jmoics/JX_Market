package pe.com.jx_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Area;
import pe.com.jx_market.persistence.AreaMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

@Service
public class AreaService
    implements BusinessService<DTO_Area>
{
    @Autowired
    private AreaMapper areaMapper;

    @Transactional
    @Override
    public ServiceOutput<DTO_Area> execute(final ServiceInput<DTO_Area> input)
    {
        final ServiceOutput<DTO_Area> output = new ServiceOutput<DTO_Area>();
        if (Constantes.V_LIST.equals(input.getAction())) {
            output.setLista(areaMapper.getAreas(input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getAction())) {
            final DTO_Area area = input.getObject();
            final DTO_Area areaTmp = areaMapper.getAreaXCodigo(area);
            if (areaTmp == null) {
                areaMapper.insertArea(area);
            } else {
                areaMapper.updateArea(area);
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getAction())) {
            final DTO_Area art = areaMapper.getAreaXCodigo(input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_DELETE.equals(input.getAction())) {
            areaMapper.deleteArea(input.getObject());
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
