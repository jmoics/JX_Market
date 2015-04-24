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
    implements BusinessService<DTO_Role>
{
    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Transactional
    public ServiceOutput<DTO_Role> execute(final ServiceInput<DTO_Role> input)
    {
        final ServiceOutput<DTO_Role> output = new ServiceOutput<DTO_Role>();
        if (Constantes.V_LIST.equals(input.getAccion())) {
            output.setLista(roleMapper.getRoles(input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getAccion())) {
            final DTO_Role roleTmp = roleMapper.getRole4Id(input.getObject());
            if (roleTmp == null) {
                roleMapper.insertRole(input.getObject());
            } else {
                roleMapper.updateRole(input.getObject());
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getAccion())) {
            final DTO_Role art = roleMapper.getRole4Id(input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_DELETE.equals(input.getAccion())) {
            roleMapper.deleteRole(input.getObject());
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
