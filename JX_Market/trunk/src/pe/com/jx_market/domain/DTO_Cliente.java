package pe.com.jx_market.domain;

import java.util.Date;

public class DTO_Cliente implements java.io.Serializable {

    private String codigo;
    private Integer empresa;
    private String usuario;
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

    public String getCodigo () {
        return codigo;
    }

    public void setCodigo (String codigo) {
        this.codigo = codigo;
    }

    public Integer getEmpresa () {
        return empresa;
    }

    public void setEmpresa (Integer empresa) {
        this.empresa = empresa;
    }

    public String getUsuario () {
        return usuario;
    }

    public void setUsuario (String usuario) {
        this.usuario = usuario;
    }

    public String getNombre () {
        return nombre;
    }

    public void setNombre (String nombre) {
        this.nombre = nombre;
    }

    public String getApellido () {
        return apellido;
    }

    public void setApellido (String apellido) {
        this.apellido = apellido;
    }

    public Date getFecNac () {
        return fecNac;
    }

    public void setFecNac (Date fecNac) {
        this.fecNac = fecNac;
    }

    public Integer getDni () {
        return dni;
    }

    public void setDni (Integer dni) {
        this.dni = dni;
    }

    public String getDireccion () {
        return direccion;
    }

    public void setDireccion (String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono () {
        return telefono;
    }

    public void setTelefono (String telefono) {
        this.telefono = telefono;
    }

    public String getCelular () {
        return celular;
    }

    public void setCelular (String celular) {
        this.celular = celular;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getCiudad () {
        return ciudad;
    }

    public void setCiudad (String ciudad) {
        this.ciudad = ciudad;
    }

    public String getRegion () {
        return region;
    }

    public void setRegion (String region) {
        this.region = region;
    }

    public Integer getCodPostal () {
        return codPostal;
    }

    public void setCodPostal (Integer codPostal) {
        this.codPostal = codPostal;
    }

    public Integer getEstado () {
        return estado;
    }

    public void setEstado (Integer estado) {
        this.estado = estado;
    }
}
