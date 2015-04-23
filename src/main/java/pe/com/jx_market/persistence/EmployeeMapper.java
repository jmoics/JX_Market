package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.DTO_Client;
import pe.com.jx_market.domain.DTO_Employee;

public interface EmployeeMapper {

    public boolean eliminaEmployee (DTO_Employee employee);

    public boolean insertEmployee (DTO_Employee employee);
    
    public boolean updateEmployee (DTO_Employee employee);

    public List<DTO_Employee> getEmployees (DTO_Employee employee);
}
