package pe.com.jx_market.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.jx_market.dao.EmpleadoDAO;
import pe.com.jx_market.domain.DTO_Empleado;

public class EmpleadoDAOibatis extends SqlMapClientDaoSupport implements EmpleadoDAO {

    @Override
    public boolean registraEmpleado (final DTO_Empleado empleado) {
        final DTO_Empleado emp = (DTO_Empleado) getSqlMapClientTemplate().queryForObject("getEmpleados", empleado);

        if (emp == null) {
            getSqlMapClientTemplate().insert("insertEmpleado", empleado);
            return true;
        } else {
            getSqlMapClientTemplate().update("updateEmpleado", empleado);
            return true;
        }
    }

    @Override
    public DTO_Empleado leeEmpleado (final DTO_Empleado empleado) {
        return (DTO_Empleado) getSqlMapClientTemplate().queryForObject("getEmpleados", empleado);

    }

    @Override
    public boolean eliminaEmpleado (final DTO_Empleado empleado) {
        getSqlMapClientTemplate().delete("deleteEmpleado", empleado);
        return false;
    }

    @Override
    public List<DTO_Empleado> getEmpleados (final DTO_Empleado empleado) {
        return getSqlMapClientTemplate().queryForList("getEmpleados", empleado);
    }
}
