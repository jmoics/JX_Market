package pe.com.jx_market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.Ubication;
import pe.com.jx_market.persistence.UbicationMapper;
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
public class UbicationService<T>
    implements BusinessService<T>
{

    /**
     *
     */
    @Autowired
    private UbicationMapper ubicationMapper;

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public ServiceOutput<T> execute(final ServiceInput<T> _input)
    {
        final ServiceOutput<T> output = new ServiceOutput<T>();
        if (Constantes.V_LIST.equals(_input.getAction())) {
            output.setLista((List<T>) this.ubicationMapper.getUbications((Ubication) _input.getObject()));
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_REGISTER.equals(_input.getAction())) {
            final Ubication ubic = (Ubication) _input.getObject();
            final Ubication ubicTmp = this.ubicationMapper.getUbication4Id(ubic);
            if (ubicTmp == null) {
                this.ubicationMapper.insertUbication(ubic);
            } else {
                this.ubicationMapper.updateUbication(ubic);
            }
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_GET.equals(_input.getAction())) {
            final Ubication ubic = this.ubicationMapper.getUbication4Id((Ubication) _input.getObject());
            if (ubic != null) {
                output.setObject((T) ubic);
                output.setErrorCode(Constantes.OK);
            } else {
                output.setErrorCode(Constantes.NOT_FOUND);
            }
        } else if (Constantes.V_DELETE.equals(_input.getAction())) {
            this.ubicationMapper.deleteUbication((Ubication) _input.getObject());
            output.setErrorCode(Constantes.OK);
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
        return output;
    }
}
