package pe.com.jx_market.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.jx_market.dao.UsuarioDAO;
import pe.com.jx_market.domain.DTO_Usuario;

public class UsuarioDAOibatis
    extends SqlMapClientDaoSupport
    implements UsuarioDAO
{

    @Override
    public boolean registraUsuario(final DTO_Usuario us)
    {
        final DTO_Usuario usuario = (DTO_Usuario) getSqlMapClientTemplate().queryForObject(
                        "getUsuarioPorUsername", us);

        if (usuario == null) {
            getSqlMapClientTemplate().insert("insertUsuario", us);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public DTO_Usuario leeUsuario(final DTO_Usuario usuario)
    {
        return (DTO_Usuario) getSqlMapClientTemplate().queryForObject("getUsuarioPorUsername", usuario);

    }

    @Override
    public boolean eliminaUsuario(final DTO_Usuario usuario)
    {
        getSqlMapClientTemplate().delete("delUsuario", usuario);
        return false;
    }

    @Override
    public List<DTO_Usuario> getUsuarios(final DTO_Usuario usuario)
    {
        return getSqlMapClientTemplate().queryForList("getUsuarios", usuario);
    }

    @Override
    public boolean cambiaPassword(final DTO_Usuario usuario)
    {
        final DTO_Usuario c = leeUsuario(usuario);
        if (c == null) {
            throw new RuntimeException("No se pudo hallar usuario " + usuario.getCodigo()
                            + " en tabla");
        }
        getSqlMapClientTemplate().update("chgPass", usuario);
        return true;
    }
}
