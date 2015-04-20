package pe.com.jx_market.domain;

import java.io.Serializable;

public class DTO_Module
    implements Serializable
{
    private Integer id;
    private Integer companyId;
    private String description;
    private String resource;

    public Integer getId()
    {
        return id;
    }

    public void setId(final Integer id)
    {
        this.id = id;
    }

    public String getResource()
    {
        return resource;
    }

    public void setResource(final String resource)
    {
        this.resource = resource;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(final Integer companyId)
    {
        this.companyId = companyId;
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
