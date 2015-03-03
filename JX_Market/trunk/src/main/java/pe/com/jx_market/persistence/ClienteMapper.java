package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.DTO_Cliente;

public interface ClienteMapper {

    public DTO_Cliente leeCliente (DTO_Cliente cliente);

    public boolean eliminaCliente (DTO_Cliente cliente);

    public boolean insertCliente (DTO_Cliente cliente);
    
    public boolean updateCliente (DTO_Cliente cliente);

    public List<DTO_Cliente> getClientes (DTO_Cliente cliente);
}
