package pe.com.jx_market.domain;

import java.io.Serializable;

public class DTO_Module
    implements Serializable
{
    private Integer id;
    private Integer companyId;
    private String moduleDescription;
    private String moduleResource;
    private Boolean activeConnection;

    public Integer getId()
    {
        return id;
    }

    public void setId(final Integer id)
    {
        this.id = id;
    }

    public String getModuleResource()
    {
        return moduleResource;
    }

    public void setModuleResource(final String moduleResource)
    {
        this.moduleResource = moduleResource;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(final Integer companyId)
    {
        this.companyId = companyId;
    }

    public String getModuleDescription()
    {
        return moduleDescription;
    }

    public void setModuleDescription(final String moduleDescription)
    {
        this.moduleDescription = moduleDescription;
    }

    public Boolean getActiveConnection()
    {
        return activeConnection;
    }

    public void setActiveConnection(final Boolean activeConnection)
    {
        this.activeConnection = activeConnection;
    }

}
