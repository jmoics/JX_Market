package pe.com.jx_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Solicitud;
import pe.com.jx_market.persistence.SolicitudMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * @author jcuevas
 *
 */
@Service
public class SolicitudService
    implements BusinessService
{
    /**
     *
     */
    @Autowired
    private SolicitudMapper solicitudMapper;

    @Override
    @Transactional
    public ServiceOutput execute(final ServiceInput _input)
    {
        final ServiceOutput output = new ServiceOutput();

        if (Constantes.V_LIST.equals(_input.getAction())) {
            output.setLista(this.solicitudMapper.getSolicitudes((DTO_Solicitud) _input.getObject()));
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_REGISTER.equals(_input.getAction())) {
            final DTO_Solicitud solTmp = this.solicitudMapper.getSolicitudxCodigo((DTO_Solicitud) _input.getObject());
            if (solTmp == null) {
                this.solicitudMapper.insertSolicitud((DTO_Solicitud) _input.getObject());
            } else {
                this.solicitudMapper.updateSolicitud((DTO_Solicitud) _input.getObject());
            }
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_GET.equals(_input.getAction())) {
            final DTO_Solicitud art = this.solicitudMapper.getSolicitudxCodigo((DTO_Solicitud) _input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_DELETE.equals(_input.getAction())) {
            this.solicitudMapper.deleteSolicitud((DTO_Solicitud) _input.getObject());
            output.setErrorCode(Constantes.OK);
        } else {
            throw new RuntimeException("SolicitudService: No se especifico verbo adecuado");
        }
        return output;
    }
}
