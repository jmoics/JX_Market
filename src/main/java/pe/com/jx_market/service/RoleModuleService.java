package pe.com.jx_market.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Module;
import pe.com.jx_market.domain.DTO_Role;
import pe.com.jx_market.domain.DTO_RoleModule;
import pe.com.jx_market.persistence.ModuleMapper;
import pe.com.jx_market.persistence.RoleMapper;
import pe.com.jx_market.persistence.RoleModuleMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * Servicio de Administración de Recursos por Role
 * @author diego
 *
 */
@Service
public class RoleModuleService implements BusinessService
{
    @Autowired
    private RoleModuleMapper roleModuleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private ModuleMapper moduleMapper;

    /**El ServiceInput debe contener un Verbo, el cual es un String en el cual se específica la acción a realizar
     * ya sea consulta, registro o eliminación ("list", "register" o "delete" respectivamente)
     */
    @Override
    @Transactional
    public ServiceOutput execute(final ServiceInput input) {

        final ServiceOutput output = new ServiceOutput();
        if(Constantes.V_LIST.equals(input.getAccion())) {
            // obtener los roles
            final Map<String, Object> map = input.getMapa();
            final DTO_Role role = (DTO_Role) map.get("role");
            final DTO_Module module = (DTO_Module) map.get("module");

            final List<DTO_Role> roles = roleMapper.getRoles(role);
            final HashMap <DTO_Role, Set<Integer>> perfXMod = new HashMap<DTO_Role, Set<Integer>>();
            for(final DTO_Role perf : roles) {
                perfXMod.put(perf, roleModuleMapper.getModules4Role(perf));
            }
            output.setLista(moduleMapper.getModules(module));
            output.setMapa(perfXMod);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if(Constantes.V_REGISTER.equals(input.getAccion())) {
            final Map<DTO_Role, Set<DTO_RoleModule>> mapa = input.getMapa();
            final Iterator<DTO_Role> roleIterator = mapa.keySet().iterator();
            while(roleIterator.hasNext()) {
                final DTO_Role role = roleIterator.next();
                roleModuleMapper.deleteModuleRole(role);
                final Set <DTO_RoleModule> recursos =  mapa.get(role);
                final Iterator <DTO_RoleModule> itBlock = recursos.iterator();
                while(itBlock.hasNext()) {
                    final DTO_RoleModule perfMod = itBlock.next();
                    System.out.println("Agrego: " + perfMod.getRole() + " cod: " + perfMod.getModuleId());
                    roleModuleMapper.insertModuleRole(perfMod);
                }
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if(Constantes.V_REGISTERPM.equals(input.getAccion())) {
            final DTO_RoleModule perfMod = (DTO_RoleModule) input.getObject();
            roleModuleMapper.insertModuleRole(perfMod);
            output.setErrorCode(Constantes.OK);
            return output;
        }

        throw new RuntimeException("Verbo incorrecto");
    }

    public ModuleMapper getModuleDAO() {
        return moduleMapper;
    }

    public void setModuleDAO(final ModuleMapper moduleMapper)
    {
        this.moduleMapper = moduleMapper;
    }

    public RoleModuleMapper getDao()
    {
        return roleModuleMapper;
    }

    public void setDao(final RoleModuleMapper roleModuleMapper)
    {
        this.roleModuleMapper = roleModuleMapper;
    }

    public RoleMapper getRoleDAO() {
        return roleMapper;
    }

    public void setRoleDAO(final RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

}
