package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.DTO_Role;

public interface RoleMapper
{
    /**
     * @param role
     * @return
     */
    public List<DTO_Role> getRoles(DTO_Role role);

    /**
     * @param role
     * @return
     */
    public DTO_Role getRoleXId(DTO_Role role);

    /**
     * @param role
     * @return
     */
    public Integer insertRole(DTO_Role role);

    /**
     * @param role
     * @return
     */
    public Integer updateRole(DTO_Role role);

    /**
     * @param role
     * @return
     */
    public boolean deleteRole(DTO_Role role);
}
