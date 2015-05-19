package pe.com.jx_market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.Parameter;
import pe.com.jx_market.persistence.ParameterMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;


/**
 * TODO comment!
 *
 * @param <T>
 * @author jcuevas
 * @version $Id$
 */
@Service
public class ParameterService<T>
    implements BusinessService<T>
{

    /**
     *
     */
    @Autowired
    private ParameterMapper parameterMapper;

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public ServiceOutput<T> execute(final ServiceInput<T> _input)
    {
        final ServiceOutput<T> output = new ServiceOutput<T>();
        if (Constantes.V_LIST.equals(_input.getAction())) {
            output.setLista((List<T>) this.parameterMapper.getParameters((Parameter) _input.getObject()));
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_REGISTER.equals(_input.getAction())) {
            final Parameter param = (Parameter) _input.getObject();
            final Parameter paramTmp = this.parameterMapper.getParameter4Id(param);
            if (paramTmp == null) {
                this.parameterMapper.insertParameter(param);
            } else {
                this.parameterMapper.updateParameter(param);
            }
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_GET.equals(_input.getAction())) {
            final Parameter param = this.parameterMapper.getParameter4Id((Parameter) _input.getObject());
            if (param != null) {
                output.setObject((T) param);
                output.setErrorCode(Constantes.OK);
            } else {
                output.setErrorCode(Constantes.NOT_FOUND);
            }
        } else if (Constantes.V_DELETE.equals(_input.getAction())) {
            this.parameterMapper.deleteParameter((Parameter) _input.getObject());
            output.setErrorCode(Constantes.OK);
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
        return output;
    }
}
