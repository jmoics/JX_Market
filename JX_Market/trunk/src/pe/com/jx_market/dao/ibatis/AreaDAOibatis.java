package pe.com.jx_market.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.jx_market.dao.AreaDAO;
import pe.com.jx_market.domain.DTO_Area;

public class AreaDAOibatis
    extends SqlMapClientDaoSupport
    implements AreaDAO
{

    @Override
    public List<DTO_Area> getAreas(final DTO_Area area)
    {
        return getSqlMapClientTemplate().queryForList("getAreas", area);
    }

    @Override
    public DTO_Area getAreaXCodigo(final DTO_Area area)
    {
        return (DTO_Area) getSqlMapClientTemplate().queryForObject("getAreaXCodigo", area);
    }

    @Override
    public boolean insertArea(final DTO_Area area)
    {
        final DTO_Area per = (DTO_Area) getSqlMapClientTemplate().queryForObject("getAreaXCodigo", area);
        if (per == null)
            getSqlMapClientTemplate().insert("insertArea", area);
        else
            getSqlMapClientTemplate().update("updateArea", area);
        return true;
    }

    @Override
    public boolean deleteArea(final DTO_Area area)
    {
        getSqlMapClientTemplate().delete("deleteArea", area);
        return true;
    }

}
