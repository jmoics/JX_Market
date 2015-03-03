package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.DTO_Cliente;
import pe.com.jx_market.domain.DTO_Empleado;

public interface EmpleadoMapper {

    public boolean eliminaEmpleado (DTO_Empleado empleado);

    public boolean insertEmpleado (DTO_Empleado empleado);
    
    public boolean updateEmpleado (DTO_Empleado empleado);

    public List<DTO_Empleado> getEmpleados (DTO_Empleado empleado);
}
