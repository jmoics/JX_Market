package pe.com.jx_market.domain;

import java.util.Date;

public class DTO_Client
    implements java.io.Serializable
{

    private Integer id;
    private Integer companyId;
    private Integer userId;
    private String clientName;
    private String clientLastName;
    private String clientLastName2;
    private Date birthday;
    private Integer documentTypeId;
    private String documentNumber;
    private String address;
    private String phone;
    private String cellPhone;
    private String email;
    private String city;
    private String ubigeo;
    private Integer postalCode;
    private Boolean active;

    public Integer getId()
    {
        return id;
    }

    public void setId(final Integer id)
    {
        this.id = id;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(final Integer companyId)
    {
        this.companyId = companyId;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(final Integer userId)
    {
        this.userId = userId;
    }

    public String getClientName()
    {
        return clientName;
    }

    public void setClientName(final String clientName)
    {
        this.clientName = clientName;
    }

    public String getClientLastName()
    {
        return clientLastName;
    }

    public void setClientLastName(final String clientLastName)
    {
        this.clientLastName = clientLastName;
    }

    public String getClientLastName2()
    {
        return clientLastName2;
    }

    public void setClientLastName2(final String clientLastName2)
    {
        this.clientLastName2 = clientLastName2;
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

    public Integer getDocumentTypeId()
    {
        return documentTypeId;
    }

    public void setDocumentTypeId(final Integer documentTypeId)
    {
        this.documentTypeId = documentTypeId;
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

    public Integer getPostalCode()
    {
        return postalCode;
    }

    public void setPostalCode(final Integer postalCode)
    {
        this.postalCode = postalCode;
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
