package pe.com.jx_market.domain;

import java.io.Serializable;
import java.util.Date;

public class DTO_Solicitud implements Serializable
{
	private Integer codigo;
	private String razon;
	private String ruc;
	private String correo;
	private Integer estado;
	private Date fecha;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(final Integer codigo) {
		this.codigo = codigo;
	}

	public String getRazon() {
		return razon;
	}

	public void setRazon(final String razon) {
		this.razon = razon;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(final String ruc) {
		this.ruc = ruc;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(final String correo) {
		this.correo = correo;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(final Integer estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(final Date fecha) {
		this.fecha = fecha;
	}

}
