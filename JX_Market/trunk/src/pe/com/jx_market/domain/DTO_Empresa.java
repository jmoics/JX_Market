package pe.com.jx_market.domain;

public class DTO_Empresa {

	private String codigo;
	private String nombre;
	private Integer estado;
	private Integer razonSocial;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public Integer getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(Integer razonSocial) {
		this.razonSocial = razonSocial;
	}
}
