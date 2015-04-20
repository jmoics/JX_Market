package pe.com.jx_market.domain;

import java.io.Serializable;

public class DTO_Role
    implements Serializable
{
    private Integer id;
    private Integer companyId;
    private Integer areaId;
    private String name;
    private String description;

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

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(final String description)
    {
        this.description = description;
    }

}
