package pe.com.jx_market.dao.ibatis;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.jx_market.dao.ClienteDAO;
import pe.com.jx_market.domain.DTO_Cliente;

public class ClienteDAOibatis extends SqlMapClientDaoSupport implements ClienteDAO {

    @Override
    public boolean registraCliente (final DTO_Cliente cliente) {
        final DTO_Cliente cl = (DTO_Cliente) getSqlMapClientTemplate().queryForObject("getClientes", cliente);
        if (cl == null) {
            getSqlMapClientTemplate().insert("insertCliente", cliente);
            return true;
        } else {
            getSqlMapClientTemplate().update("updateCliente", cliente);
            return true;
        }
    }

    @Override
    public DTO_Cliente leeCliente (final DTO_Cliente cliente) {
        final HashMap mapa = new HashMap();
        return (DTO_Cliente) getSqlMapClientTemplate().queryForObject("getClientes", cliente);

    }

    @Override
    public boolean eliminaCliente (final DTO_Cliente cliente) {
        getSqlMapClientTemplate().delete("delCliente", cliente);
        return false;
    }

    @Override
    public List<DTO_Cliente> getClientes (final DTO_Cliente cliente) {
        return getSqlMapClientTemplate().queryForList("getClientes", cliente);
    }
}
