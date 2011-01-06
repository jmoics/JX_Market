package pe.com.jx_market.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.jx_market.dao.SolicitudDAO;
import pe.com.jx_market.domain.DTO_Solicitud;

public class SolicitudDAOibatis
    extends SqlMapClientDaoSupport
    implements SolicitudDAO
{

	@Override
	public List<DTO_Solicitud> getSolicitudes(final DTO_Solicitud solicitud) {
		return getSqlMapClientTemplate().queryForList("getSolicitudes", solicitud);
	}

	@Override
	public DTO_Solicitud getSolicitudxCodigo(final DTO_Solicitud solicitud) {
		return (DTO_Solicitud) getSqlMapClientTemplate().queryForObject("getSolicitudxCodigo", solicitud);
	}

	@Override
	public boolean insertSolicitud(DTO_Solicitud solicitud) {
		final DTO_Solicitud sol = (DTO_Solicitud) getSqlMapClientTemplate().queryForObject("getSolicitudxCodigo", solicitud);
        if (sol == null)
            getSqlMapClientTemplate().insert("insertSolicitud", solicitud);
        else
            getSqlMapClientTemplate().update("updateSolicitud", solicitud);
        return true;
	}

	@Override
	public boolean deleteSolicitud(DTO_Solicitud solicitud) {
		getSqlMapClientTemplate().delete("deleteSolicitud", solicitud);
        return true;
	}

}
