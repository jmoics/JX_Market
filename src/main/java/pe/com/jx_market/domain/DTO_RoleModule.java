package pe.com.jx_market.domain;

import java.io.Serializable;

public class DTO_RoleModule
    implements Serializable
{
    private Integer roleId;
    private Integer moduleId;
    private Boolean active;

    public Integer getRole()
    {
        return roleId;
    }

    public void setRole(final Integer roleId)
    {
        this.roleId = roleId;
    }

    public Integer getModuleId()
    {
        return moduleId;
    }

    public void setModuleId(final Integer moduleId)
    {
        this.moduleId = moduleId;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(final Boolean active)
    {
        this.active = active;
    }

}
