/**
 *
 */
package pe.com.jx_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_TradeMark;
import pe.com.jx_market.persistence.MarcaMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * @author George
 *
 */
@Service
public class MarcaService
    implements BusinessService<DTO_TradeMark>
{
    @Autowired
    private MarcaMapper marcaMapper;

    /* (non-Javadoc)
     * @see pe.com.jx_market.utilities.BusinessService#execute(pe.com.jx_market.utilities.ServiceInput)
     */
    @Override
    @Transactional
    public ServiceOutput<DTO_TradeMark> execute(final ServiceInput<DTO_TradeMark> input)
    {
        final ServiceOutput<DTO_TradeMark> output = new ServiceOutput<DTO_TradeMark>();
        if (Constantes.V_LIST.equals(input.getAccion())) {
            output.setLista(marcaMapper.getMarcas(input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getAccion())) {
            final DTO_TradeMark categTmp = marcaMapper.getMarcaXCodigo(input.getObject());
            if (categTmp == null) {
                marcaMapper.insertMarca(input.getObject());
            } else {
                marcaMapper.updateMarca(input.getObject());
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getAccion())) {
            final DTO_TradeMark art = marcaMapper.getMarcaXCodigo(input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        }else if (Constantes.V_DELETE.equals(input.getAccion())) {
            marcaMapper.deleteMarca(input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    public MarcaMapper getDao()
    {
        return marcaMapper;
    }

    public void setDao(final MarcaMapper marcaMapper)
    {
        this.marcaMapper = marcaMapper;
    }
}
