package pe.com.jx_market.domain;

import java.io.Serializable;

/**
 * @author jcuevas
 *
 */
public class DTO_Company
    implements Serializable
{
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String businessName;
    /**
     *
     */
    private Integer active;
    /**
     *
     */
    private String docNumber;
    /**
     *
     */
    private String domain;

    /**
     * @return Company Id.
     */
    public Integer getId()
    {
        return this.id;
    }

    /**
     * @param _id Company Id.
     */
    public void setId(final Integer _id)
    {
        this.id = _id;
    }

    /**
     * @return Company Name.
     */
    public String getBusinessName()
    {
        return this.businessName;
    }

    /**
     * @param _businessName Company Name.
     */
    public void setBusinessName(final String _businessName)
    {
        this.businessName = _businessName;
    }

    /**
     * @return Status.
     */
    public Integer getActive()
    {
        return this.active;
    }

    /**
     * @param _active Status.
     */
    public void setActive(final Integer _active)
    {
        this.active = _active;
    }

    /**
     * @return Document Number.
     */
    public String getDocNumber()
    {
        return this.docNumber;
    }

    /**
     * @param _docNumber Document Number.
     */
    public void setDocNumber(final String _docNumber)
    {
        this.docNumber = _docNumber;
    }

    /**
     * @return Domain.
     */
    public String getDomain()
    {
        return this.domain;
    }

    /**
     * @param _domain Domain.
     */
    public void setDomain(final String _domain)
    {
        this.domain = _domain;
    }
}
