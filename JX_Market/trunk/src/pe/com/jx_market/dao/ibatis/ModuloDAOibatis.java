package pe.com.jx_market.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.jx_market.dao.ModuloDAO;
import pe.com.jx_market.domain.DTO_Modulo;

public class ModuloDAOibatis
    extends SqlMapClientDaoSupport
    implements ModuloDAO
{

    @Override
    public List<DTO_Modulo> getModulos(final DTO_Modulo modulo)
    {
        return getSqlMapClientTemplate().queryForList("getModulos", modulo);
    }

    @Override
    public DTO_Modulo getModuloXCodigo(final DTO_Modulo modulo)
    {
        return (DTO_Modulo) getSqlMapClientTemplate().queryForObject("getModuloXCodigo", modulo);
    }

    @Override
    public Integer insertModulo(final DTO_Modulo modulo)
    {
        final DTO_Modulo per = (DTO_Modulo) getSqlMapClientTemplate().queryForObject("getModuloXCodigo", modulo);
        Integer ret = -1;
        if (per == null)
            ret = (Integer) getSqlMapClientTemplate().insert("insertModulo", modulo);
        else
            getSqlMapClientTemplate().update("updateModulo", modulo);
        return ret;
    }

    @Override
    public boolean deleteModulo(final DTO_Modulo modulo)
    {
        getSqlMapClientTemplate().delete("deleteModulo", modulo);
        return true;
    }

}
