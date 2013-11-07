package pe.com.jx_market.dao;

import java.util.List;

import pe.com.jx_market.domain.DTO_Cliente;

public interface ClienteDAO {

    public DTO_Cliente leeCliente (DTO_Cliente cliente);

    public boolean eliminaCliente (DTO_Cliente cliente);

    public boolean registraCliente (DTO_Cliente cliente);

    public List<DTO_Cliente> getClientes (DTO_Cliente cliente);
}
