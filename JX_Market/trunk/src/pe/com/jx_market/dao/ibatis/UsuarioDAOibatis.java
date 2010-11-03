package pe.com.jx_market.dao.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.jx_market.dao.UsuarioDAO;
import pe.com.jx_market.domain.DTO_Usuario;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.service.Constantes;

public class UsuarioDAOibatis extends SqlMapClientDaoSupport implements UsuarioDAO {

	@Override
	public boolean registraUsuario(DTO_Usuario us) {
        HashMap mapa = new HashMap();
        mapa.put("usuario_n_codigo", us.getCodigo());
		mapa.put("empresa_n_codigo", us.getEmpresa());
		mapa.put("usuario_v_contrasena", us.getContrasena());

        
        DTO_Usuario usuario = (DTO_Usuario)getSqlMapClientTemplate().
                queryForObject("getUsuarioPorCodigo", mapa);
        
        if(usuario == null) {
            getSqlMapClientTemplate().insert("insertUsuario", mapa);
            return true;
        } else {
            getSqlMapClientTemplate().update("updateUsuario", mapa);
            return true;
        }
    }
	
	@Override
	public DTO_Usuario leeUsuario(String codigo, String empresa){
		HashMap mapa = new HashMap();
        mapa.put("usuario_n_codigo", codigo);
        mapa.put("empresa_n_codigo", empresa);
		return (DTO_Usuario)getSqlMapClientTemplate().queryForObject("getUsuarioPorCodigo", mapa);
		
	}
	
	@Override
	public boolean eliminaUsuario(DTO_Usuario cliente){
		HashMap mapa = new HashMap();
		mapa.put("cliente_n_codigo", cliente.getCodigo());
		mapa.put("empresa_n_codigo", cliente.getEmpresa());
		getSqlMapClientTemplate().delete("delUsuario", mapa);
		//eliminar usuario
		return false;
	}
	
	@Override
	public List<DTO_Usuario> getUsuarios(){
		HashMap mapa = new HashMap();
		return getSqlMapClientTemplate().queryForList("getUsuarios");
	}
	
	@Override
    public boolean cambiaPassword(DTO_Usuario usuario) {
    	
        DTO_Usuario c = leeUsuario(usuario.getCodigo(), usuario.getEmpresa());
        if(c == null) {
                throw new RuntimeException("No se pudo hallar usuario " + usuario.getCodigo() + " en tabla");
        }
        HashMap mapa = new HashMap();
		mapa.put("usuario_n_codigo", usuario.getCodigo());
		mapa.put("usuario_v_contrasena", usuario.getContrasena());
		mapa.put("empresa_n_codigo", usuario.getEmpresa());
		getSqlMapClientTemplate().update("chgPass", mapa);
        return true;
    }
}
