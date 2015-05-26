package pe.com.jx_market.domain;

import java.io.Serializable;


/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public class Ubication
    implements Serializable
{
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Integer parentId;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String code;

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
     * Getter method for the variable {@link #parentId}.
     *
     * @return value of variable {@link #parentId}
     */
    public final Integer getParentId()
    {
        return this.parentId;
    }

    /**
     * Setter method for variable {@link #parentId}.
     *
     * @param _parentId value for variable {@link #parentId}
     */
    public final void setParentId(final Integer _parentId)
    {
        this.parentId = _parentId;
    }

    /**
     * Getter method for the variable {@link #name}.
     *
     * @return value of variable {@link #name}
     */
    public final String getName()
    {
        return this.name;
    }

    /**
     * Setter method for variable {@link #name}.
     *
     * @param _name value for variable {@link #name}
     */
    public final void setName(final String _name)
    {
        this.name = _name;
    }

    /**
     * Getter method for the variable {@link #code}.
     *
     * @return value of variable {@link #code}
     */
    public final String getCode()
    {
        return this.code;
    }

    /**
     * Setter method for variable {@link #code}.
     *
     * @param _code value for variable {@link #code}
     */
    public final void setCode(final String _code)
    {
        this.code = _code;
    }


}
