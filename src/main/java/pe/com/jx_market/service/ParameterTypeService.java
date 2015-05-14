package pe.com.jx_market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.ParameterType;
import pe.com.jx_market.persistence.ParameterMapper;
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
public class ParameterTypeService
    implements BusinessService<ParameterType>
{

    /**
     *
     */
    @Autowired
    private ParameterMapper parameterMapper;

    @Transactional
    @Override
    public ServiceOutput<ParameterType> execute(final ServiceInput<ParameterType> _input)
    {
        final ServiceOutput<ParameterType> output = new ServiceOutput<ParameterType>();
        if (Constantes.V_LIST.equals(_input.getAction())) {
            output.setLista(this.parameterMapper.getParameterTypes(_input.getObject()));
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_REGISTER.equals(_input.getAction())) {
            final ParameterType paramType = _input.getObject();
            final ParameterType paramTypeTmp = this.parameterMapper.getParameterType4Id(paramType);
            if (paramTypeTmp == null) {
                this.parameterMapper.insertParameterType(paramType);
            } else {
                this.parameterMapper.updateParameterType(paramType);
            }
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_GET.equals(_input.getAction())) {
            final List<ParameterType> params = this.parameterMapper.getParameterTypes(_input.getObject());
            if (params != null && params.size() > 0) {
                output.setObject(params.get(0));
                output.setErrorCode(Constantes.OK);
            } else {
                output.setErrorCode(Constantes.NOT_FOUND);
            }
        } else if (Constantes.V_DELETE.equals(_input.getAction())) {
            this.parameterMapper.deleteParameterType(_input.getObject());
            output.setErrorCode(Constantes.OK);
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
        return output;
    }
}
