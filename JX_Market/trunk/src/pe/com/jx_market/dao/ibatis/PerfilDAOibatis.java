package pe.com.jx_market.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.jx_market.dao.PerfilDAO;
import pe.com.jx_market.domain.DTO_Perfil;

public class PerfilDAOibatis
    extends SqlMapClientDaoSupport
    implements PerfilDAO
{

    @Override
    public List<DTO_Perfil> getPerfiles(final DTO_Perfil perfil)
    {
        return getSqlMapClientTemplate().queryForList("getPerfiles", perfil);
    }

    @Override
    public DTO_Perfil getPerfilXCodigo(final DTO_Perfil perfil)
    {
        return (DTO_Perfil) getSqlMapClientTemplate().queryForObject("getPerfilXCodigo", perfil);
    }

    @Override
    public Integer insertPerfil(final DTO_Perfil perfil)
    {
        final DTO_Perfil per = (DTO_Perfil) getSqlMapClientTemplate().queryForObject("getPerfilXCodigo", perfil);
        Integer ret = -1;
        if (per == null)
            ret = (Integer) getSqlMapClientTemplate().insert("insertPerfil", perfil);
        else
            getSqlMapClientTemplate().update("updatePerfil", perfil);
        return ret;
    }

    @Override
    public boolean deletePerfil(final DTO_Perfil perfil)
    {
        getSqlMapClientTemplate().delete("deletePerfil", perfil);
        return true;
    }

}
