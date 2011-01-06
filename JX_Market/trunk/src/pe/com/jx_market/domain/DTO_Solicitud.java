package pe.com.jx_market.domain;

import java.io.Serializable;
import java.util.Date;

public class DTO_Solicitud implements Serializable
{
	private Integer codigo;
	private String razon;
	private Integer ruc;
	private String correo;
	private Integer estado;
	private Date fecha;
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	public String getRazon() {
		return razon;
	}
	
	public void setRazon(String razon) {
		this.razon = razon;
	}
	
	public Integer getRuc() {
		return ruc;
	}
	
	public void setRuc(Integer ruc) {
		this.ruc = ruc;
	}
	
	public String getCorreo() {
		return correo;
	}
	
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public Integer getEstado() {
		return estado;
	}
	
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}
