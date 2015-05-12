package pe.com.jx_market.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jcuevas
 *
 */
public class DTO_Employee
    implements Serializable
{

    /**
     *
     */
    private String id;
    /**
     *
     */
    private Integer userId;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private String employeeName;
    /**
     *
     */
    private String employeeLastName;
    /**
     *
     */
    private String employeeLastName2;
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
    private Boolean active;

    /**
     * @return Employee Id.
     */
    public String getId()
    {
        return this.id;
    }

    /**
     * @param _id Employee Id.
     */
    public void setId(final String _id)
    {
        this.id = _id;
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
     * @return Employee Name.
     */
    public String getEmployeeName()
    {
        return this.employeeName;
    }

    /**
     * @param _employeeName Employee Name.
     */
    public void setEmployeeName(final String _employeeName)
    {
        this.employeeName = _employeeName;
    }

    /**
     * @return Employee Last Name.
     */
    public String getEmployeeLastName()
    {
        return this.employeeLastName;
    }

    /**
     * @param _employeeLastName Employee Last Name.
     */
    public void setEmployeeLastName(final String _employeeLastName)
    {
        this.employeeLastName = _employeeLastName;
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
     * @return Employee Second Last Name.
     */
    public String getEmployeeLastName2()
    {
        return this.employeeLastName2;
    }

    /**
     * @param _employeeLastName2 Employee Second Last Name.
     */
    public void setEmployeeLastName2(final String _employeeLastName2)
    {
        this.employeeLastName2 = _employeeLastName2;
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
     * @return Cell Phone Number.
     */
    public String getCellPhone()
    {
        return this.cellPhone;
    }

    /**
     * @param _cellPhone  Cell Phone Number.
     */
    public void setCellPhone(final String _cellPhone)
    {
        this.cellPhone = _cellPhone;
    }

    /**
     * @return EMail.
     */
    public String getEmail()
    {
        return this.email;
    }

    /**
     * @param _email EMail.
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
     * @return Active.
     */
    public Boolean getActive()
    {
        return this.active;
    }

    /**
     * @param _active Active.
     */
    public void setActive(final Boolean _active)
    {
        this.active = _active;
    }
}
