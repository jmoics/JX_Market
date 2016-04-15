package pe.com.jx_market.domain;

import java.io.Serializable;


/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public class ModuleWidgetUI
    implements Serializable
{

    /**
    *
    */
    private Integer id;
    /**
    *
    */
    private Integer accessUiId;
    /**
    *
    */
    private String nameUi;

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
     * Getter method for the variable {@link #accessUiId}.
     *
     * @return value of variable {@link #accessUiId}
     */
    public final Integer getAccessUiId()
    {
        return this.accessUiId;
    }


    /**
     * Setter method for variable {@link #accessUiId}.
     *
     * @param accessUiId value for variable {@link #accessUiId}
     */
    public final void setAccessUiId(final Integer accessUiId)
    {
        this.accessUiId = accessUiId;
    }

    /**
     * Getter method for the variable {@link #nameUi}.
     *
     * @return value of variable {@link #nameUi}
     */
    public final String getNameUi()
    {
        return this.nameUi;
    }

    /**
     * Setter method for variable {@link #nameUi}.
     *
     * @param _nameUi value for variable {@link #nameUi}
     */
    public final void setNameUi(final String _nameUi)
    {
        this.nameUi = _nameUi;
    }

}
