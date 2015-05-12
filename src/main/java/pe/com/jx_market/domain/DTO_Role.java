package pe.com.jx_market.domain;

import java.io.Serializable;
import java.util.List;

/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public class DTO_Role
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
    private Integer areaId;
    /**
     *
     */
    private String roleName;
    /**
     *
     */
    private String roleDescription;
    /**
     *
     */
    private DTO_Area area;
    /**
     *
     */
    private List<DTO_Module> modules;

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
     * Getter method for the variable {@link #areaId}.
     *
     * @return value of variable {@link #areaId}
     */
    public final Integer getAreaId()
    {
        return this.areaId;
    }

    /**
     * Setter method for variable {@link #areaId}.
     *
     * @param _areaId value for variable {@link #areaId}
     */
    public final void setAreaId(final Integer _areaId)
    {
        this.areaId = _areaId;
    }

    /**
     * Getter method for the variable {@link #roleName}.
     *
     * @return value of variable {@link #roleName}
     */
    public final String getRoleName()
    {
        return this.roleName;
    }

    /**
     * Setter method for variable {@link #roleName}.
     *
     * @param _roleName value for variable {@link #roleName}
     */

    public final void setRoleName(final String _roleName)
    {
        this.roleName = _roleName;
    }

    /**
     * Getter method for the variable {@link #roleDescription}.
     *
     * @return value of variable {@link #roleDescription}
     */
    public final String getRoleDescription()
    {
        return this.roleDescription;
    }

    /**
     * Setter method for variable {@link #roleDescription}.
     *
     * @param _roleDescription value for variable {@link #roleDescription}
     */
    public final void setRoleDescription(final String _roleDescription)
    {
        this.roleDescription = _roleDescription;
    }

    /**
     * Getter method for the variable {@link #area}.
     *
     * @return value of variable {@link #area}
     */
    public final DTO_Area getArea()
    {
        return this.area;
    }

    /**
     * Setter method for variable {@link #area}.
     *
     * @param _area value for variable {@link #area}
     */
    public final void setArea(final DTO_Area _area)
    {
        this.area = _area;
    }

    /**
     * Getter method for the variable {@link #modules}.
     *
     * @return value of variable {@link #modules}
     */
    public final List<DTO_Module> getModules()
    {
        return this.modules;
    }

    /**
     * Setter method for variable {@link #modules}.
     *
     * @param _modules value for variable {@link #modules}
     */

    public final void setModules(final List<DTO_Module> _modules)
    {
        this.modules = _modules;
    }

    @Override
    public String toString()
    {
        return this.roleName;
    }
}
