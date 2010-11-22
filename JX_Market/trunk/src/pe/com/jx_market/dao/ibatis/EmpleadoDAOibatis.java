package pe.com.jx_market.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.jx_market.dao.EmpleadoDAO;
import pe.com.jx_market.domain.DTO_Empleado;

public class EmpleadoDAOibatis extends SqlMapClientDaoSupport implements EmpleadoDAO {

    public boolean registraEmpleado (DTO_Empleado empleado) {
        DTO_Empleado emp = (DTO_Empleado) getSqlMapClientTemplate().queryForObject(
                "getEmpleados", empleado);

        if (emp == null) {
            getSqlMapClientTemplate().insert("insertEmpleado", empleado);
            return true;
        } else {
            getSqlMapClientTemplate().update("updateEmpleado", empleado);
            return true;
        }
    }

    public DTO_Empleado leeEmpleado (DTO_Empleado empleado) {
        return (DTO_Empleado) getSqlMapClientTemplate().queryForObject("getEmpleados", empleado);

    }

    public boolean eliminaEmpleado (DTO_Empleado empleado) {
        getSqlMapClientTemplate().delete("deleteEmpleado", empleado);
        return false;
    }

    public List<DTO_Empleado> getEmpleados (DTO_Empleado empleado) {
        return getSqlMapClientTemplate().queryForList("getEmpleados", empleado);
    }
}
