package pe.com.jx_market.dao.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.jx_market.dao.ClienteDAO;
import pe.com.jx_market.domain.DTO_Cliente;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.domain.DTO_Usuario;
import pe.com.jx_market.service.Constantes;

public class EmpleadoDAOibatis extends SqlMapClientDaoSupport implements ClienteDAO {

    public boolean registraCliente (DTO_Cliente cl) {
        HashMap mapa = new HashMap();
        mapa.put("codigo", cl.getCodigo());
        mapa.put("empresa", cl.getEmpresa());
        mapa.put("usuario", cl.getUsuario());
        mapa.put("nombre", cl.getNombre());
        mapa.put("apellido", cl.getApellido());
        mapa.put("fecNac", cl.getFecNac());
        mapa.put("dni", cl.getDni());
        mapa.put("direccion", cl.getDireccion());
        mapa.put("telefono", cl.getTelefono());
        mapa.put("celular", cl.getCelular());
        mapa.put("email", cl.getEmail());
        mapa.put("ciudad", cl.getCiudad());
        mapa.put("region", cl.getRegion());
        mapa.put("codPostal", cl.getCodPostal());
        mapa.put("estado", cl.getEstado());

        DTO_Cliente cliente = (DTO_Cliente) getSqlMapClientTemplate().queryForObject(
                "getClientePorCodigo", mapa);
        // mapa.put("pass", p.getPass());
        // mapa.put("nombre", p.getNombre());

        if (cliente == null) {
            getSqlMapClientTemplate().insert("insertCliente", mapa);
            return true;
        } else {
            getSqlMapClientTemplate().update("updateCliente", mapa);
            return true;
        }
    }

    public DTO_Cliente leeCliente (String codigo, DTO_Empresa empresa) {
        HashMap mapa = new HashMap();
        mapa.put("usuario_n_codigo", codigo);
        mapa.put("empresa_n_codigo", empresa.getCodigo());
        return (DTO_Cliente) getSqlMapClientTemplate().queryForObject("getClientePorCodigo", mapa);

    }

    public boolean eliminaCliente (DTO_Cliente cliente) {
        HashMap mapa = new HashMap();
        mapa.put("cliente_n_codigo", cliente.getCodigo());
        mapa.put("empresa_n_codigo", cliente.getEmpresa());
        // mapa.put("", cliente.getUsuario().getCodigo());
        getSqlMapClientTemplate().delete("delCliente", mapa);
        // eliminar usuario
        return false;
    }

    public List<DTO_Cliente> getClientes () {
        HashMap mapa = new HashMap();
        return getSqlMapClientTemplate().queryForList("getClientes");
    }

    public boolean cambiaPassword (String codigo, String password, DTO_Empresa empresa) {
        DTO_Cliente c = leeCliente(codigo, empresa);
        if (c == null) {
            throw new RuntimeException("No se pudo hallar usuario " + codigo + " en tabla");
        }
        HashMap mapa = new HashMap();
        mapa.put("usuario_n_codigo", codigo);
        mapa.put("usuario_v_contrasena", password);
        mapa.put("empresa_n_codigo", empresa);
        getSqlMapClientTemplate().update("chgPass", mapa);
        return true;
    }

}
