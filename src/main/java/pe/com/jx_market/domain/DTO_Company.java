package pe.com.jx_market.domain;

import java.io.Serializable;

public class DTO_Company
    implements Serializable
{
    private Integer id;
    private String businessName;
    private Integer active;
    private String docNumber;
    private String domain;

    public Integer getId()
    {
        return id;
    }

    public void setId(final Integer id)
    {
        this.id = id;
    }

    public String getBusinessName()
    {
        return businessName;
    }

    public void setBusinessName(final String businessName)
    {
        this.businessName = businessName;
    }

    public Integer getActive()
    {
        return active;
    }

    public void setActive(final Integer active)
    {
        this.active = active;
    }

    public String getDocNumber()
    {
        return docNumber;
    }

    public void setDocNumber(final String docNumber)
    {
        this.docNumber = docNumber;
    }

    public String getDomain()
    {
        return domain;
    }

    public void setDomain(final String domain)
    {
        this.domain = domain;
    }
}
