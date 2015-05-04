package pe.com.jx_market.domain;

import java.io.Serializable;
import java.util.Set;

public class DTO_RoleModule
    implements Serializable
{
    private Integer id;
    private Integer roleId;
    private Integer moduleId;
    private Boolean active;
    private String accessTypesStr;
    private Set<String> accessTypes;

    public Integer getId()
    {
        return id;
    }

    public void setId(final Integer id)
    {
        this.id = id;
    }

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

    public String getAccessTypesStr()
    {
        return accessTypesStr;
    }

    public void setAccessTypesStr(final String accessTypesStr)
    {
        this.accessTypesStr = accessTypesStr;
    }

    public Set<String> getAccessTypes()
    {
        return accessTypes;
    }

    public void setAccessTypes(final Set<String> accessTypes)
    {
        this.accessTypes = accessTypes;
    }
}
