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

/**
 * @author jcuevas
 *
 */
@Service
public class AreaService
    implements BusinessService<DTO_Area>
{

    /**
     *
     */
    @Autowired
    private AreaMapper areaMapper;

    @Transactional
    @Override
    public ServiceOutput<DTO_Area> execute(final ServiceInput<DTO_Area> _input)
    {
        final ServiceOutput<DTO_Area> output = new ServiceOutput<DTO_Area>();
        if (Constantes.V_LIST.equals(_input.getAction())) {
            output.setLista(this.areaMapper.getAreas(_input.getObject()));
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_REGISTER.equals(_input.getAction())) {
            final DTO_Area area = _input.getObject();
            final DTO_Area areaTmp = this.areaMapper.getAreaXCodigo(area);
            if (areaTmp == null) {
                this.areaMapper.insertArea(area);
            } else {
                this.areaMapper.updateArea(area);
            }
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_GET.equals(_input.getAction())) {
            final DTO_Area art = this.areaMapper.getAreaXCodigo(_input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_DELETE.equals(_input.getAction())) {
            this.areaMapper.deleteArea(_input.getObject());
            output.setErrorCode(Constantes.OK);
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
        return output;
    }
}
