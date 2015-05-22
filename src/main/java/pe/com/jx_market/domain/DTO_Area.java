package pe.com.jx_market.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object _obj)
    {
        boolean ret = super.equals(_obj);
        if (!(_obj instanceof DTO_Area)) {
            ret = false;
        } else {
            if (_obj == this) {
                ret = true;
            } else {
                ret = new EqualsBuilder()
                        .append(this.id, ((DTO_Area) _obj).id)
                        .isEquals();
            }
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 31).// two randomly chosen prime numbers
                        // if deriving: appendSuper(super.hashCode()).
                        append(this.id).
                        toHashCode();
    }
}
