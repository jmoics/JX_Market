package pe.com.jx_market.domain;

import java.util.Date;

public class DTO_Cliente
    implements java.io.Serializable
{

    private Integer codigo;
    private Integer company;
    private Integer usuario;
    private String nombre;
    private String apellido;
    private Date fecNac;
    private Integer dni;
    private String direccion;
    private String telefono;
    private String celular;
    private String email;
    private String ciudad;
    private String region;
    private Integer codPostal;
    private Integer estado;

    public Integer getCodigo()
    {
        return codigo;
    }

    public void setCodigo(final Integer codigo)
    {
        this.codigo = codigo;
    }

    public Integer getCompany()
    {
        return company;
    }

    public void setCompany(final Integer company)
    {
        this.company = company;
    }

    public Integer getUsuario()
    {
        return usuario;
    }

    public void setUsuario(final Integer usuario)
    {
        this.usuario = usuario;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(final String nombre)
    {
        this.nombre = nombre;
    }

    public String getApellido()
    {
        return apellido;
    }

    public void setApellido(final String apellido)
    {
        this.apellido = apellido;
    }

    public Date getFecNac()
    {
        return fecNac;
    }

    public void setFecNac(final Date fecNac)
    {
        this.fecNac = fecNac;
    }

    public Integer getDni()
    {
        return dni;
    }

    public void setDni(final Integer dni)
    {
        this.dni = dni;
    }

    public String getDireccion()
    {
        return direccion;
    }

    public void setDireccion(final String direccion)
    {
        this.direccion = direccion;
    }

    public String getTelefono()
    {
        return telefono;
    }

    public void setTelefono(final String telefono)
    {
        this.telefono = telefono;
    }

    public String getCelular()
    {
        return celular;
    }

    public void setCelular(final String celular)
    {
        this.celular = celular;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(final String email)
    {
        this.email = email;
    }

    public String getCiudad()
    {
        return ciudad;
    }

    public void setCiudad(final String ciudad)
    {
        this.ciudad = ciudad;
    }

    public String getRegion()
    {
        return region;
    }

    public void setRegion(final String region)
    {
        this.region = region;
    }

    public Integer getCodPostal()
    {
        return codPostal;
    }

    public void setCodPostal(final Integer codPostal)
    {
        this.codPostal = codPostal;
    }

    public Integer getEstado()
    {
        return estado;
    }

    public void setEstado(final Integer estado)
    {
        this.estado = estado;
    }
}
