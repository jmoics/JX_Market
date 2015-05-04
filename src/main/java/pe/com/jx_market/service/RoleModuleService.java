package pe.com.jx_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Module;
import pe.com.jx_market.domain.DTO_Role;
import pe.com.jx_market.domain.DTO_RoleModule;
import pe.com.jx_market.persistence.ModuleMapper;
import pe.com.jx_market.persistence.RoleMapper;
import pe.com.jx_market.persistence.RoleModuleMapper;
import pe.com.jx_market.utilities.BusinessServiceConnection;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceInputConnection;
import pe.com.jx_market.utilities.ServiceOutput;
import pe.com.jx_market.utilities.ServiceOutputConnection;

/**
 * Servicio de Administración de Recursos por Role
 * @author diego
 *
 */
@Service
public class RoleModuleService
    implements BusinessServiceConnection<DTO_RoleModule, DTO_Role, DTO_Module>
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
    public ServiceOutputConnection<DTO_RoleModule, DTO_Role, DTO_Module> execute(
                    final ServiceInputConnection<DTO_RoleModule, DTO_Role, DTO_Module> input) {

        final ServiceOutputConnection<DTO_RoleModule, DTO_Role, DTO_Module> output =
                        new ServiceOutputConnection<DTO_RoleModule, DTO_Role, DTO_Module>();
        if(Constantes.V_LIST.equals(input.getAction())) {
            output.setResultListTo(moduleMapper.getModules(input.getObjectTo()));
            output.setResultListFrom(roleMapper.getModules4Role(input.getMapa()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if(Constantes.V_REGISTER.equals(input.getAction())) {
            /*final Map<DTO_Role, Set<DTO_RoleModule>> mapa = input.getMapa();
            final Iterator<DTO_Role> roleIterator = mapa.keySet().iterator();
            while(roleIterator.hasNext()) {
                final DTO_Role role = roleIterator.next();
                roleModuleMapper.deleteModuleRole(role);
                final Set <DTO_RoleModule> recursos =  mapa.get(role);
                final Iterator <DTO_RoleModule> itBlock = recursos.iterator();
                while(itBlock.hasNext()) {
                    final DTO_RoleModule perfMod = itBlock.next();
                    perfMod.setActive(true);
                    System.out.println("Agrego: " + perfMod.getRole() + " cod: " + perfMod.getModuleId());
                    roleModuleMapper.insertModuleRole(perfMod);
                }
            }
            output.setErrorCode(Constantes.OK);*/
            return output;
        } else if(Constantes.V_REGISTERPM.equals(input.getAction())) {
            final DTO_RoleModule perfMod = input.getObject();
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

    @Override
    public ServiceOutput<DTO_RoleModule> execute(final ServiceInput<DTO_RoleModule> paramDTO_Input)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
