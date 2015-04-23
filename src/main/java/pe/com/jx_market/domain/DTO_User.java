package pe.com.jx_market.domain;

public class DTO_User
    implements java.io.Serializable
{

    private Integer id;
    private String username;
    private Integer companyId;
    private String password;

    public Integer getId()
    {
        return id;
    }

    public void setId(final Integer id)
    {
        this.id = id;
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
