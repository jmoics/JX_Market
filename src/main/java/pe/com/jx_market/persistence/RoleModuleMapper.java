package pe.com.jx_market.persistence;

import java.util.List;
import java.util.Map;
import java.util.Set;

import pe.com.jx_market.domain.DTO_Role;
import pe.com.jx_market.domain.DTO_RoleModule;

public interface RoleModuleMapper {

    // obtiene los id de los modules asociados a un role
    public List<DTO_Role> getModules4Role (Map<Object, Object> parameterMap);

    // obtiene el bean module asociados a un role
    public Set<String> getModuleString4Role(DTO_Role role);

    // elimina todos los recursos asociados a un role
    public void deleteModuleRole(DTO_Role role);

    // asocia un recurso a un role
    public void insertModuleRole(DTO_RoleModule perfMod);
}
