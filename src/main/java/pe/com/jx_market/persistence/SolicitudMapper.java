package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.DTO_Solicitud;

public interface SolicitudMapper
{

	/**
	 * @param solicitud
	 * @return
	 */
    public List<DTO_Solicitud> getSolicitudes(DTO_Solicitud solicitud);

    /**
     * @param solicitud
     * @return
     */
    public DTO_Solicitud getSolicitudxCodigo(DTO_Solicitud solicitud);

    /**
     * @param solicitud
     * @return
     */
    public boolean insertSolicitud(DTO_Solicitud solicitud);
    
    /**
     * @param solicitud
     * @return
     */
    public boolean updateSolicitud(DTO_Solicitud solicitud);

    /**
     * @param solicitud
     * @return
     */
    public boolean deleteSolicitud(DTO_Solicitud solicitud);
}
