package pe.com.jx_market.domain;

import java.io.Serializable;

/**
 * @author jcuevas
 *
 */
public class DTO_Area
    implements Serializable
{

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private String areaAreaName;

    /**
     * @return Id of the table.
     */
    public Integer getId()
    {
        return this.id;
    }

    /**
     * @param _id Id of the table.
     */
    public void setId(final Integer _id)
    {
        this.id = _id;
    }

    /**
     * @return Company Id.
     */
    public Integer getCompanyId()
    {
        return this.companyId;
    }

    /**
     * @param _companyId Company Id.
     */
    public void setCompanyId(final Integer _companyId)
    {
        this.companyId = _companyId;
    }

    /**
     * @return Area Name.
     */
    public String getAreaName()
    {
        return this.areaAreaName;
    }

    /**
     * @param _areaAreaName Area Name
     */
    public void setAreaName(final String _areaAreaName)
    {
        this.areaAreaName = _areaAreaName;
    }
}
