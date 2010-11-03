package pe.com.jx_market.dao;

import java.util.List;

import pe.com.jx_market.domain.DTO_Cliente;
import pe.com.jx_market.domain.DTO_Empresa;

public interface ClienteDAO {

	public DTO_Cliente leeCliente(String codigo, DTO_Empresa empresa);
	public boolean eliminaCliente(DTO_Cliente cliente);
	public boolean registraCliente(DTO_Cliente p);
    public List<DTO_Cliente> getClientes();
    public boolean cambiaPassword(String uname, String password, DTO_Empresa empresa);
}
