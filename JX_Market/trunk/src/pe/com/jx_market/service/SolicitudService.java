package pe.com.jx_market.service;

import pe.com.jx_market.dao.SolicitudDAO;
import pe.com.jx_market.domain.DTO_Area;
import pe.com.jx_market.domain.DTO_Solicitud;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

public class SolicitudService implements BusinessService
{

	private SolicitudDAO dao;
	
	@Override
	public DTO_Output execute(final DTO_Input input) {
		final DTO_Output output = new DTO_Output();
		
		if(Constantes.V_LIST.equals(input.getVerbo()))
		{
			output.setLista(dao.getSolicitudes((DTO_Solicitud) input.getObject()));
			output.setErrorCode(Constantes.OK);
			return output;
		} else if(Constantes.V_REGISTER.equals(input.getVerbo())) {
			dao.insertSolicitud((DTO_Solicitud) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
		} else if(Constantes.V_GET.equals(input.getVerbo())) {
			final DTO_Solicitud art = dao.getSolicitudxCodigo((DTO_Solicitud) input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
		} else if (Constantes.V_DELETE.equals(input.getVerbo())) {
            dao.deleteSolicitud((DTO_Solicitud) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("SolicitudService: No se especifico verbo adecuado");
        }
	}
	
	public SolicitudDAO getDao()
	{
		return dao;
	}
	
	public void setDao(final SolicitudDAO dao)
	{
		this.dao = dao;
	}

}
