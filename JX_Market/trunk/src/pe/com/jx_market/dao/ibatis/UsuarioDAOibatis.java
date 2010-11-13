package pe.com.jx_market.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.jx_market.dao.UsuarioDAO;
import pe.com.jx_market.domain.DTO_Usuario;

public class UsuarioDAOibatis
    extends SqlMapClientDaoSupport
    implements UsuarioDAO
{

    @Override
    public boolean registraUsuario(DTO_Usuario us)
    {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("codigo", us.getCodigo());
        mapa.put("username", us.getUsername());
        mapa.put("empresa", us.getEmpresa());
        mapa.put("contrasena", us.getContrasena());

        DTO_Usuario usuario = (DTO_Usuario) getSqlMapClientTemplate().queryForObject(
                        "getUsuarioPorUsername", mapa);

        if (usuario == null) {
            getSqlMapClientTemplate().insert("insertUsuario", us);
            return true;
        } else {
            getSqlMapClientTemplate().update("updateUsuario", mapa);
            return true;
        }
    }

    @Override
    public DTO_Usuario leeUsuario(String username,
                                  Integer empresa)
    {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("usuario_v_username", username);
        mapa.put("empresa_n_codigo", empresa);
        return (DTO_Usuario) getSqlMapClientTemplate().queryForObject("getUsuarioPorUsername", mapa);

    }

    @Override
    public boolean eliminaUsuario(DTO_Usuario usuario)
    {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("usuario_v_username", usuario.getUsername());
        mapa.put("empresa_n_codigo", usuario.getEmpresa());
        getSqlMapClientTemplate().delete("delUsuario", mapa);
        // eliminar usuario
        return false;
    }

    @Override
    public List<DTO_Usuario> getUsuarios(Integer empresa)
    {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("empresa_n_codigo", empresa);
        return getSqlMapClientTemplate().queryForList("getUsuarios", mapa);
    }

    @Override
    public boolean cambiaPassword(DTO_Usuario usuario)
    {
        DTO_Usuario c = leeUsuario(usuario.getUsername(), usuario.getEmpresa());
        if (c == null) {
            throw new RuntimeException("No se pudo hallar usuario " + usuario.getCodigo()
                            + " en tabla");
        }
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("usuario_v_username", usuario.getUsername());
        mapa.put("usuario_v_contrasena", usuario.getContrasena());
        mapa.put("empresa_n_codigo", usuario.getEmpresa());
        // hacer alguna validacion con el password anterior, aunque creo q seria
        // mejor en el servicio
        getSqlMapClientTemplate().update("chgPass", mapa);
        return true;
    }
}
