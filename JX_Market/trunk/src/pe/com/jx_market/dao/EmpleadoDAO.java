package pe.com.jx_market.dao;

import java.util.List;

import pe.com.jx_market.domain.DTO_Cliente;

public interface EmpleadoDAO {

	public DTO_Cliente leeCliente(String uname);
	public boolean eliminaCliente(String uname);
	public boolean registraCliente(DTO_Cliente p);
    public List<DTO_Cliente> getClientes();
    public boolean cambiaPassword(String uname, String password);
    public DTO_Cliente cambiaTheme(String uname, String tema);
}
