package pe.com.jx_market.domain;

import java.util.Date;

/**
 * @author jcuevas
 *
 */
public class DTO_Client
    implements java.io.Serializable
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
    private Integer userId;
    /**
     *
     */
    private String clientName;
    /**
     *
     */
    private String clientLastName;
    /**
     *
     */
    private String clientLastName2;
    /**
     *
     */
    private Date birthday;
    /**
     *
     */
    private Integer documentTypeId;
    /**
     *
     */
    private String documentNumber;
    /**
     *
     */
    private String address;
    /**
     *
     */
    private String phone;
    /**
     *
     */
    private String cellPhone;
    /**
     *
     */
    private String email;
    /**
     *
     */
    private String city;
    /**
     *
     */
    private String ubigeo;
    /**
     *
     */
    private Integer postalCode;
    /**
     *
     */
    private Boolean active;

    /**
     * @return Client Id.
     */
    public Integer getId()
    {
        return this.id;
    }

    /**
     * @param _id Client Id.
     */
    public void setId(final Integer _id)
    {
        this.id = _id;
    }

    /**
     * @return Company Id.
     */
    public Integer getCompanyId()
    {
        return this.companyId;
    }

    /**
     * @param _companyId Company Id.
     */
    public void setCompanyId(final Integer _companyId)
    {
        this.companyId = _companyId;
    }

    /**
     * @return User Id.
     */
    public Integer getUserId()
    {
        return this.userId;
    }

    /**
     * @param _userId User Id.
     */
    public void setUserId(final Integer _userId)
    {
        this.userId = _userId;
    }

    /**
     * @return Client Name.
     */
    public String getClientName()
    {
        return this.clientName;
    }

    /**
     * @param _clientName Client Name.
     */
    public void setClientName(final String _clientName)
    {
        this.clientName = _clientName;
    }

    /**
     * @return Client Last Name.
     */
    public String getClientLastName()
    {
        return this.clientLastName;
    }

    /**
     * @param _clientLastName Client Last Name.
     */
    public void setClientLastName(final String _clientLastName)
    {
        this.clientLastName = _clientLastName;
    }

    /**
     * @return Client Second Last Name.
     */
    public String getClientLastName2()
    {
        return this.clientLastName2;
    }

    /**
     * @param _clientLastName2 Client Second Last Name.
     */
    public void setClientLastName2(final String _clientLastName2)
    {
        this.clientLastName2 = _clientLastName2;
    }

    /**
     * @return Birthday.
     */
    public Date getBirthday()
    {
        return this.birthday;
    }

    /**
     * @param _birthday Birthday.
     */
    public void setBirthday(final Date _birthday)
    {
        this.birthday = _birthday;
    }

    /**
     * @return Document Number.
     */
    public String getDocumentNumber()
    {
        return this.documentNumber;
    }

    /**
     * @param _documentNumber Document Number.
     */
    public void setDocumentNumber(final String _documentNumber)
    {
        this.documentNumber = _documentNumber;
    }

    /**
     * @return Document Type.
     */
    public Integer getDocumentTypeId()
    {
        return this.documentTypeId;
    }

    /**
     * @param _documentTypeId Document Type.
     */
    public void setDocumentTypeId(final Integer _documentTypeId)
    {
        this.documentTypeId = _documentTypeId;
    }

    /**
     * @return Address.
     */
    public String getAddress()
    {
        return this.address;
    }

    /**
     * @param _address Address.
     */
    public void setAddress(final String _address)
    {
        this.address = _address;
    }

    /**
     * @return Phone Number.
     */
    public String getPhone()
    {
        return this.phone;
    }

    /**
     * @param _phone Phone Number.
     */
    public void setPhone(final String _phone)
    {
        this.phone = _phone;
    }

    /**
     * @return Cell phone number.
     */
    public String getCellPhone()
    {
        return this.cellPhone;
    }

    /**
     * @param _cellPhone Cell phone number.
     */
    public void setCellPhone(final String _cellPhone)
    {
        this.cellPhone = _cellPhone;
    }

    /**
     * @return E-Mail.
     */
    public String getEmail()
    {
        return this.email;
    }

    /**
     * @param _email E-Mail.
     */
    public void setEmail(final String _email)
    {
        this.email = _email;
    }

    /**
     * @return City.
     */
    public String getCity()
    {
        return this.city;
    }

    /**
     * @param _city City.
     */
    public void setCity(final String _city)
    {
        this.city = _city;
    }

    /**
     * @return Ubigeo.
     */
    public String getUbigeo()
    {
        return this.ubigeo;
    }

    /**
     * @param _ubigeo Ubigeo.
     */
    public void setUbigeo(final String _ubigeo)
    {
        this.ubigeo = _ubigeo;
    }

    /**
     * @return Postal Code.
     */
    public Integer getPostalCode()
    {
        return this.postalCode;
    }

    /**
     * @param _postalCode Postal Code.
     */
    public void setPostalCode(final Integer _postalCode)
    {
        this.postalCode = _postalCode;
    }

    /**
     * @return Status.
     */
    public Boolean getActive()
    {
        return this.active;
    }

    /**
     * @param _active Status.
     */
    public void setActive(final Boolean _active)
    {
        this.active = _active;
    }
}