package pe.com.jx_market.domain;

public class DTO_Usuario implements java.io.Serializable {

	private java.lang.String username;
	private java.lang.String pass;
	private java.lang.String comentario;
	private java.lang.String cargo;
	private java.lang.String tipo_auth;
	private java.util.Date fecha_creacion;

	public java.lang.String getUsername() {
		return this.username;
	}

	public void setUsername(java.lang.String username) {
		this.username = username;
	}

	public java.lang.String getPass() {
		return this.pass;
	}

	public void setPass(java.lang.String pass) {
		this.pass = pass;
	}

	public java.lang.String getComentario() {
		return this.comentario;
	}

	public void setComentario(java.lang.String comentario) {
		this.comentario = comentario;
	}

	public java.lang.String getCargo() {
		return this.cargo;
	}

	public void setCargo(java.lang.String cargo) {
		this.cargo = cargo;
	}

	public java.lang.String getTipo_auth() {
		return this.tipo_auth;
	}

	public void setTipo_auth(java.lang.String tipo_auth) {
		this.tipo_auth = tipo_auth;
	}

	public java.util.Date getFecha_creacion() {
		return this.fecha_creacion;
	}

	public void setFecha_creacion(java.util.Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

}
