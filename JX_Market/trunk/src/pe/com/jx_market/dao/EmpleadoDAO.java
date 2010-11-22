package pe.com.jx_market.dao;

import java.util.List;

import pe.com.jx_market.domain.DTO_Cliente;
import pe.com.jx_market.domain.DTO_Empleado;

public interface EmpleadoDAO {

    public DTO_Empleado leeEmpleado (DTO_Empleado empleado);

    public boolean eliminaEmpleado (DTO_Empleado empleado);

    public boolean registraEmpleado (DTO_Empleado empleado);

    public List<DTO_Empleado> getEmpleados (DTO_Empleado empleado);
}
