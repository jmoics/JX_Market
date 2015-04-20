package pe.com.jx_market.domain;

import java.io.Serializable;
import java.util.List;

public class DTO_Role
    implements Serializable
{
    private Integer id;
    private Integer companyId;
    private Integer areaId;
    private String roleName;
    private String roleDescription;
    private DTO_Area area;
    private List<DTO_Module> modules;

    public Integer getId()
    {
        return id;
    }

    public void setId(final Integer id)
    {
        this.id = id;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(final Integer companyId)
    {
        this.companyId = companyId;
    }

    public Integer getAreaId()
    {
        return areaId;
    }

    public void setAreaId(final Integer areaId)
    {
        this.areaId = areaId;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(final String roleName)
    {
        this.roleName = roleName;
    }

    public String getRoleDescription()
    {
        return roleDescription;
    }

    public void setRoleDescription(final String roleDescription)
    {
        this.roleDescription = roleDescription;
    }

    public List<DTO_Module> getModules()
    {
        return modules;
    }

    public void setModules(final List<DTO_Module> modules)
    {
        this.modules = modules;
    }

    public DTO_Area getArea()
    {
        return area;
    }

    public void setArea(final DTO_Area area)
    {
        this.area = area;
    }
}
