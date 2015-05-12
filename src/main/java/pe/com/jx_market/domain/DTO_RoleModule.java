package pe.com.jx_market.domain;

import java.io.Serializable;
import java.util.Set;

/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public class DTO_RoleModule
    implements Serializable
{
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Integer roleId;
    /**
     *
     */
    private Integer moduleId;
    /**
     *
     */
    private Boolean active;
    /**
     *
     */
    private String accessTypesStr;
    /**
     *
     */
    private Set<String> accessTypes;

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
     * Getter method for the variable {@link #roleId}.
     *
     * @return value of variable {@link #roleId}
     */
    public final Integer getRoleId()
    {
        return this.roleId;
    }

    /**
     * Setter method for variable {@link #roleId}.
     *
     * @param _roleId value for variable {@link #roleId}
     */
    public final void setRoleId(final Integer _roleId)
    {
        this.roleId = _roleId;
    }

    /**
     * Getter method for the variable {@link #moduleId}.
     *
     * @return value of variable {@link #moduleId}
     */
    public final Integer getModuleId()
    {
        return this.moduleId;
    }

    /**
     * Setter method for variable {@link #moduleId}.
     *
     * @param _moduleId value for variable {@link #moduleId}
     */
    public final void setModuleId(final Integer _moduleId)
    {
        this.moduleId = _moduleId;
    }

    /**
     * Getter method for the variable {@link #active}.
     *
     * @return value of variable {@link #active}
     */
    public final Boolean getActive()
    {
        return this.active;
    }

    /**
     * Setter method for variable {@link #active}.
     *
     * @param _active value for variable {@link #active}
     */
    public final void setActive(final Boolean _active)
    {
        this.active = _active;
    }

    /**
     * Getter method for the variable {@link #accessTypesStr}.
     *
     * @return value of variable {@link #accessTypesStr}
     */
    public final String getAccessTypesStr()
    {
        return this.accessTypesStr;
    }

    /**
     * Setter method for variable {@link #accessTypesStr}.
     *
     * @param _accessTypesStr value for variable {@link #accessTypesStr}
     */
    public final void setAccessTypesStr(final String _accessTypesStr)
    {
        this.accessTypesStr = _accessTypesStr;
    }

    /**
     * Getter method for the variable {@link #accessTypes}.
     *
     * @return value of variable {@link #accessTypes}
     */
    public final Set<String> getAccessTypes()
    {
        return this.accessTypes;
    }

    /**
     * Setter method for variable {@link #accessTypes}.
     *
     * @param _accessTypes value for variable {@link #accessTypes}
     */
    public final void setAccessTypes(final Set<String> _accessTypes)
    {
        this.accessTypes = _accessTypes;
    }
}
