package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.DTO_Client;

public interface ClientMapper {

    public DTO_Client leeClient (DTO_Client client);

    public boolean eliminaClient (DTO_Client client);

    public boolean insertClient (DTO_Client client);
    
    public boolean updateClient (DTO_Client client);

    public List<DTO_Client> getClients (DTO_Client client);
}
