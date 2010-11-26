package pe.com.jx_market.domain;

import java.util.Date;

public class DTO_Empleado implements java.io.Serializable {

    private String codigo;
    private Integer perfil;
    private Integer usuario;
    private Integer empresa;
    private String nombre;
    private String apellido;
    private Date fecNac;
    private String dni;
    private String direccion;
    private String telefono;
    private String celular;
    private String email;
    private String ciudad;
    private String region;
    private Integer estado;

    public String getCodigo () {
        return codigo;
    }

    public void setCodigo (final String codigo) {
        this.codigo = codigo;
    }

    public Integer getPerfil () {
        return perfil;
    }

    public void setPerfil (final Integer perfil) {
        this.perfil = perfil;
    }

    public Integer getUsuario () {
        return usuario;
    }

    public void setUsuario (final Integer usuario) {
        this.usuario = usuario;
    }

    public Integer getEmpresa () {
        return empresa;
    }

    public void setEmpresa (final Integer empresa) {
        this.empresa = empresa;
    }

    public String getNombre () {
        return nombre;
    }

    public void setNombre (final String nombre) {
        this.nombre = nombre;
    }

    public String getApellido () {
        return apellido;
    }

    public void setApellido (final String apellido) {
        this.apellido = apellido;
    }

    public Date getFecNac () {
        return fecNac;
    }

    public void setFecNac (final Date fecNac) {
        this.fecNac = fecNac;
    }

    public String getDni () {
        return dni;
    }

    public void setDni (final String dni) {
        this.dni = dni;
    }

    public String getDireccion () {
        return direccion;
    }

    public void setDireccion (final String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono () {
        return telefono;
    }

    public void setTelefono (final String telefono) {
        this.telefono = telefono;
    }

    public String getCelular () {
        return celular;
    }

    public void setCelular (final String celular) {
        this.celular = celular;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (final String email) {
        this.email = email;
    }

    public String getCiudad () {
        return ciudad;
    }

    public void setCiudad (final String ciudad) {
        this.ciudad = ciudad;
    }

    public String getRegion () {
        return region;
    }

    public void setRegion (final String region) {
        this.region = region;
    }

    public Integer getEstado () {
        return estado;
    }

    public void setEstado (final Integer estado) {
        this.estado = estado;
    }
}
