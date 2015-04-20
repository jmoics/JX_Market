package pe.com.jx_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Role;
import pe.com.jx_market.persistence.RoleMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

@Service
public class RoleService
    implements BusinessService
{
    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Transactional
    public ServiceOutput execute(final ServiceInput input)
    {
        final ServiceOutput output = new ServiceOutput();
        if (Constantes.V_LIST.equals(input.getAccion())) {
            output.setLista(roleMapper.getRoles((DTO_Role) input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getAccion())) {
            final DTO_Role perfTmp = roleMapper.getRoleXId((DTO_Role) input.getObject());
            if (perfTmp == null) {
                final Integer cod = roleMapper.insertRole((DTO_Role) input.getObject());
                output.setObject(cod);
            } else {
                roleMapper.updateRole((DTO_Role) input.getObject());
                output.setObject(new Integer(-1));
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getAccion())) {
            final DTO_Role art = roleMapper.getRoleXId((DTO_Role) input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_DELETE.equals(input.getAccion())) {
            roleMapper.deleteRole((DTO_Role) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    public RoleMapper getDao()
    {
        return roleMapper;
    }

    public void setDao(final RoleMapper roleMapper)
    {
        this.roleMapper = roleMapper;
    }

}
