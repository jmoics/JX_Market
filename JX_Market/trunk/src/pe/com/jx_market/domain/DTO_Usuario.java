package pe.com.jx_market.domain;

public class DTO_Usuario implements java.io.Serializable {

    private Integer codigo;
    private String username;
    private Integer empresa;
    private String contrasena;
    public Integer getCodigo () {
        return codigo;
    }
    public void setCodigo (Integer codigo) {
        this.codigo = codigo;
    }
    public String getUsername () {
        return username;
    }
    public void setUsername (String username) {
        this.username = username;
    }
    public Integer getEmpresa () {
        return empresa;
    }
    public void setEmpresa (Integer empresa) {
        this.empresa = empresa;
    }
    public String getContrasena () {
        return contrasena;
    }
    public void setContrasena (String contrasena) {
        this.contrasena = contrasena;
    }

    
}
