package pe.com.jx_market.domain;

public class DTO_User
    implements java.io.Serializable
{

    private Integer id;
    private String username;
    private Integer companyId;
    private String password;
    private Integer roleId;
    private DTO_Role role;

    public Integer getId()
    {
        return id;
    }

    public void setId(final Integer id)
    {
        this.id = id;
    }

    public Integer getRoleId()
    {
        return roleId;
    }

    public DTO_Role getRole()
    {
        return role;
    }

    public void setRole(final DTO_Role role)
    {
        this.role = role;
    }

    public void setRoleId(final Integer roleId)
    {
        this.roleId = roleId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(final String username)
    {
        this.username = username;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(final Integer companyId)
    {
        this.companyId = companyId;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(final String password)
    {
        this.password = password;
    }

}
