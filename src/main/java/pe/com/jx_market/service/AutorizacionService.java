package pe.com.jx_market.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Role;
import pe.com.jx_market.domain.DTO_User;
import pe.com.jx_market.persistence.RoleModuleMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * Servicio de Autorizacion de recursos por role
 *
 * @author americati
 *
 */
@Service
public class AutorizacionService<T>
    implements BusinessService<T>
{
    /**
     *
     */
    @Autowired
    private RoleModuleMapper roleModuleMapper;

    /**
     * El ServiceInput contendrá un objeto DTO_Contacto y un objeto con un recurso
     * especifico o varios, devolviendo en el ServiceOutput OK si tiene
     * autorizacion para acceder a ese recurso y AUTH_ERROR si no la tiene.
     *
     * @param _input Objeto estándar de entrada
     * @return Objeto estándar de salida
     */
    @Override
    @Transactional
    public ServiceOutput<T> execute(final ServiceInput<T> _input)
    {
        final ServiceOutput<T> output = new ServiceOutput<T>();
        final DTO_User user = (DTO_User) _input.getMapa().get(Constantes.ATTRIBUTE_USER);
        final DTO_Role role = new DTO_Role();
        role.setId(user.getRoleId());
        String[] modules;
        if (_input.getMapa().containsKey(Constantes.ATTRIBUTE_MODULE)) {
            final String module = (String) _input.getMapa().get(Constantes.ATTRIBUTE_MODULE);
            modules = new String[1];
            modules[0] = module;
        } else {
            modules = (String[]) _input.getMapa().get(Constantes.ATTRIBUTE_MODULES);
        }
        final Set<String> modulesDelRole = this.roleModuleMapper.getModuleString4Role(role);
        // debemos validar que todos los recursos solicitados estan en el array
        for (int z = 0; z < modules.length; z++) {
            if (!modulesDelRole.contains(modules[z])) {
                output.setErrorCode(Constantes.AUTH_ERROR);
                break;
            } else {
                output.setErrorCode(Constantes.OK);
            }
        }

        return output;
    }
}
