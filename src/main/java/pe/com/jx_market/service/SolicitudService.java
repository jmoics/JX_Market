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

@Service
public class SolicitudService
    implements BusinessService
{
    @Autowired
    private SolicitudMapper solicitudMapper;

    @Override
    @Transactional
    public ServiceOutput execute(final ServiceInput input)
    {
        final ServiceOutput output = new ServiceOutput();

        if (Constantes.V_LIST.equals(input.getAction())) {
            output.setLista(solicitudMapper.getSolicitudes((DTO_Solicitud) input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getAction())) {
            DTO_Solicitud solTmp = solicitudMapper.getSolicitudxCodigo((DTO_Solicitud) input.getObject());
            if (solTmp == null) {
                solicitudMapper.insertSolicitud((DTO_Solicitud) input.getObject());
            } else {
                solicitudMapper.updateSolicitud((DTO_Solicitud) input.getObject());
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getAction())) {
            final DTO_Solicitud art = solicitudMapper.getSolicitudxCodigo((DTO_Solicitud) input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_DELETE.equals(input.getAction())) {
            solicitudMapper.deleteSolicitud((DTO_Solicitud) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("SolicitudService: No se especifico verbo adecuado");
        }
    }

    public SolicitudMapper getDao()
    {
        return solicitudMapper;
    }

    public void setDao(final SolicitudMapper solicitudMapper)
    {
        this.solicitudMapper = solicitudMapper;
    }

}
