package pe.com.jx_market.domain;

import java.io.Serializable;

/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public class Parameter
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
    private String parameterName;
    /**
    *
    */
    private String parameterDesc;
    /**
     *
     */
    private Integer parameterTypeId;
    /**
     *
     */
    private ParameterType parameterType;

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
     * Getter method for the variable {@link #parameterName}.
     *
     * @return value of variable {@link #parameterName}
     */
    public final String getParameterName()
    {
        return this.parameterName;
    }

    /**
     * Setter method for variable {@link #parameterName}.
     *
     * @param _parameterName value for variable {@link #parameterName}
     */
    public final void setParameterName(final String _parameterName)
    {
        this.parameterName = _parameterName;
    }

    /**
     * Getter method for the variable {@link #parameterDesc}.
     *
     * @return value of variable {@link #parameterDesc}
     */
    public final String getParameterDesc()
    {
        return this.parameterDesc;
    }

    /**
     * Setter method for variable {@link #parameterDesc}.
     *
     * @param _parameterDesc value for variable {@link #parameterDesc}
     */
    public final void setParameterDesc(final String _parameterDesc)
    {
        this.parameterDesc = _parameterDesc;
    }

    /**
     * Getter method for the variable {@link #parameterTypeId}.
     *
     * @return value of variable {@link #parameterTypeId}
     */
    public final Integer getParameterTypeId()
    {
        return this.parameterTypeId;
    }

    /**
     * Setter method for variable {@link #parameterTypeId}.
     *
     * @param _parameterTypeId value for variable {@link #parameterTypeId}
     */
    public final void setParameterTypeId(final Integer _parameterTypeId)
    {
        this.parameterTypeId = _parameterTypeId;
    }

    /**
     * Getter method for the variable {@link #parameterType}.
     *
     * @return value of variable {@link #parameterType}
     */
    public final ParameterType getParameterType()
    {
        return this.parameterType;
    }

    /**
     * Setter method for variable {@link #parameterType}.
     *
     * @param _parameterType value for variable {@link #parameterType}
     */
    public final void setParameterType(final ParameterType _parameterType)
    {
        this.parameterType = _parameterType;
    }


}
