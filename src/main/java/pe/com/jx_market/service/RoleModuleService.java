package pe.com.jx_market.service;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Module;
import pe.com.jx_market.domain.DTO_Role;
import pe.com.jx_market.domain.DTO_RoleModule;
import pe.com.jx_market.persistence.ModuleMapper;
import pe.com.jx_market.persistence.RoleModuleMapper;
import pe.com.jx_market.utilities.BusinessServiceConnection;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceInputConnection;
import pe.com.jx_market.utilities.ServiceOutput;
import pe.com.jx_market.utilities.ServiceOutputConnection;

/**
 * Servicio de Administración de Recursos por Role.
 *
 * @author George
 *
 */
@Service
public class RoleModuleService
    implements BusinessServiceConnection<DTO_RoleModule, DTO_Role, DTO_Module>
{

    /**
     *
     */
    @Autowired
    private RoleModuleMapper roleModuleMapper;
    /**
     *
     */
    @Autowired
    private ModuleMapper moduleMapper;

    /**
     * El ServiceInput debe contener un Verbo, el cual es un String en el cual
     * se específica la acción a realizar ya sea consulta, registro o
     * eliminación ("list", "register" o "delete" respectivamente).
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public ServiceOutputConnection<DTO_RoleModule, DTO_Role, DTO_Module> execute(
                                             final ServiceInputConnection<DTO_RoleModule, DTO_Role, DTO_Module> _input)
    {

        final ServiceOutputConnection<DTO_RoleModule, DTO_Role, DTO_Module> output =
                        new ServiceOutputConnection<DTO_RoleModule, DTO_Role, DTO_Module>();
        if (Constantes.V_LIST.equals(_input.getAction())) {
            output.setResultListTo(this.moduleMapper.getModules(_input.getObjectTo()));
            output.setResultListFrom(this.roleModuleMapper.getModules4Role(_input.getMapa()));
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_REGISTER.equals(_input.getAction())) {
            final Map<DTO_Role, Set<DTO_RoleModule>> mapa = (Map<DTO_Role, Set<DTO_RoleModule>>) _input.getMapa();
            final Iterator<DTO_Role> roleIterator = mapa.keySet().iterator();
            while (roleIterator.hasNext()) {
                final DTO_Role role = roleIterator.next();
                this.roleModuleMapper.deleteModuleRole(role);
                final Set<DTO_RoleModule> recursos = mapa.get(role);
                final Iterator<DTO_RoleModule> itBlock = recursos.iterator();
                while (itBlock.hasNext()) {
                    final DTO_RoleModule perfMod = itBlock.next();
                    perfMod.setActive(true);
                    // System.out.println("Agrego: " + perfMod.getRole() +
                    // " cod: " + perfMod.getModuleId());
                    this.roleModuleMapper.insertModuleRole(perfMod);
                }
            }
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_REGISTERPM.equals(_input.getAction())) {
            final DTO_RoleModule perfMod = _input.getObject();
            this.roleModuleMapper.insertModuleRole(perfMod);
            output.setErrorCode(Constantes.OK);
        } else {
            throw new RuntimeException("Verbo incorrecto");
        }
        return output;
    }

    /*
     * (non-Javadoc)
     * @see
     * pe.com.jx_market.utilities.BusinessService#execute(pe.com.jx_market.utilities
     * .ServiceInput)
     */
    @Override
    public ServiceOutput<DTO_RoleModule> execute(final ServiceInput<DTO_RoleModule> _input)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
