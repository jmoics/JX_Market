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

/**
 * @author jcuevas
 *
 */
@Service
public class RoleService
    implements BusinessService<DTO_Role>
{
    /**
     *
     */
    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Transactional
    public ServiceOutput<DTO_Role> execute(final ServiceInput<DTO_Role> _input)
    {
        final ServiceOutput<DTO_Role> output = new ServiceOutput<DTO_Role>();
        if (Constantes.V_LIST.equals(_input.getAction())) {
            output.setLista(this.roleMapper.getRoles(_input.getObject()));
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_REGISTER.equals(_input.getAction())) {
            final DTO_Role roleTmp = this.roleMapper.getRole4Id(_input.getObject());
            if (roleTmp == null) {
                this.roleMapper.insertRole(_input.getObject());
            } else {
                this.roleMapper.updateRole(_input.getObject());
            }
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_GET.equals(_input.getAction())) {
            final DTO_Role art = this.roleMapper.getRole4Id(_input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_DELETE.equals(_input.getAction())) {
            this.roleMapper.deleteRole(_input.getObject());
            output.setErrorCode(Constantes.OK);
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
        return output;
    }
}
