package pe.com.jx_market.domain;

import java.io.Serializable;

/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public class ParameterType
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
    private String parameterTypeName;
    /**
     *
     */
    private String parameterTypeDesc;

    /**
     * Getter method for the variable {@link #id}.
     *
     * @return value of variable {@link #id}
     */
    public final Integer getId()
    {
        return this.id;
    }

    /**
     * Setter method for variable {@link #id}.
     *
     * @param _id value for variable {@link #id}
     */
    public final void setId(final Integer _id)
    {
        this.id = _id;
    }

    /**
     * Getter method for the variable {@link #companyId}.
     *
     * @return value of variable {@link #companyId}
     */
    public final Integer getCompanyId()
    {
        return this.companyId;
    }

    /**
     * Setter method for variable {@link #companyId}.
     *
     * @param _companyId value for variable {@link #companyId}
     */
    public final void setCompanyId(final Integer _companyId)
    {
        this.companyId = _companyId;
    }

    /**
     * Getter method for the variable {@link #parameterTypeName}.
     *
     * @return value of variable {@link #parameterTypeName}
     */
    public final String getParameterTypeName()
    {
        return this.parameterTypeName;
    }

    /**
     * Setter method for variable {@link #parameterTypeName}.
     *
     * @param _parameterTypeName value for variable {@link #parameterTypeName}
     */
    public final void setParameterTypeName(final String _parameterTypeName)
    {
        this.parameterTypeName = _parameterTypeName;
    }

    /**
     * Getter method for the variable {@link #parameterTypeDesc}.
     *
     * @return value of variable {@link #parameterTypeDesc}
     */
    public final String getParameterTypeDesc()
    {
        return this.parameterTypeDesc;
    }

    /**
     * Setter method for variable {@link #parameterTypeDesc}.
     *
     * @param _parameterTypeDesc value for variable {@link #parameterTypeDesc}
     */
    public final void setParameterTypeDesc(final String _parameterTypeDesc)
    {
        this.parameterTypeDesc = _parameterTypeDesc;
    }


}
