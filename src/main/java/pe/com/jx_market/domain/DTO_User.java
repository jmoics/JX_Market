package pe.com.jx_market.domain;

/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public class DTO_User
    implements java.io.Serializable
{

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String username;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private String password;
    /**
     *
     */
    private Integer roleId;
    /**
     *
     */
    private DTO_Role role;

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
     * Getter method for the variable {@link #username}.
     *
     * @return value of variable {@link #username}
     */
    public final String getUsername()
    {
        return this.username;
    }

    /**
     * Setter method for variable {@link #username}.
     *
     * @param _username value for variable {@link #username}
     */
    public final void setUsername(final String _username)
    {
        this.username = _username;
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
     * Getter method for the variable {@link #password}.
     *
     * @return value of variable {@link #password}
     */
    public final String getPassword()
    {
        return this.password;
    }

    /**
     * Setter method for variable {@link #password}.
     *
     * @param _password value for variable {@link #password}
     */
    public final void setPassword(final String _password)
    {
        this.password = _password;
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
     * Getter method for the variable {@link #role}.
     *
     * @return value of variable {@link #role}
     */
    public final DTO_Role getRole()
    {
        return this.role;
    }

    /**
     * Setter method for variable {@link #role}.
     *
     * @param _role value for variable {@link #role}
     */
    public final void setRole(final DTO_Role _role)
    {
        this.role = _role;
    }
}
