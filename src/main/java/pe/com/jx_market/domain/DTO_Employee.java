package pe.com.jx_market.domain;

import java.io.Serializable;
import java.util.Date;

public class DTO_Employee
    implements Serializable
{

    private String id;
    private Integer roleId;
    private Integer userId;
    private Integer companyId;
    private String employeeName;
    private String employeeLastName;
    private Date birthday;
    private String documentNumber;
    private String address;
    private String phone;
    private String cellPhone;
    private String email;
    private String city;
    private String ubigeo;
    private Boolean active;

    public String getId()
    {
        return id;
    }

    public void setId(final String id)
    {
        this.id = id;
    }

    public Integer getRole()
    {
        return roleId;
    }

    public void setRole(final Integer roleId)
    {
        this.roleId = roleId;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(final Integer userId)
    {
        this.userId = userId;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(final Integer companyId)
    {
        this.companyId = companyId;
    }

    public String getEmployeeName()
    {
        return employeeName;
    }

    public void setEmployeeName(final String employeeName)
    {
        this.employeeName = employeeName;
    }

    public String getEmployeeLastName()
    {
        return employeeLastName;
    }

    public void setEmployeeLastName(final String employeeLastName)
    {
        this.employeeLastName = employeeLastName;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(final Date birthday)
    {
        this.birthday = birthday;
    }

    public String getDocumentNumber()
    {
        return documentNumber;
    }

    public void setDocumentNumber(final String documentNumber)
    {
        this.documentNumber = documentNumber;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(final String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(final String phone)
    {
        this.phone = phone;
    }

    public String getCellPhone()
    {
        return cellPhone;
    }

    public void setCellPhone(final String cellPhone)
    {
        this.cellPhone = cellPhone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(final String email)
    {
        this.email = email;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(final String city)
    {
        this.city = city;
    }

    public String getUbigeo()
    {
        return ubigeo;
    }

    public void setUbigeo(final String ubigeo)
    {
        this.ubigeo = ubigeo;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(final Boolean active)
    {
        this.active = active;
    }
}
