package pe.com.jx_market.dao.ibatis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.jx_market.dao.PerfilModuloDAO;
import pe.com.jx_market.domain.DTO_Perfil;
import pe.com.jx_market.domain.DTO_PerfilModulo;

public class PerfilModuloDAOibatis extends SqlMapClientDaoSupport implements PerfilModuloDAO {

    
    @Override
    public Set<Integer> listaPorPerfil(final DTO_Perfil perfil){
        final List<Integer> lista = getSqlMapClientTemplate().queryForList("getModuloXPerfil",perfil);
        final Set listaSet = new HashSet(lista);
        return listaSet;
    }
  
    @Override
    public void eliminaPorPerfil(final DTO_Perfil perfil){
        getSqlMapClientTemplate().delete("delModuloPerfil", perfil);
        
    }
    
    @Override
    public void agregaPorPerfil(final DTO_PerfilModulo perfMod){
         getSqlMapClientTemplate().insert("insertModuloPerfil", perfMod);
    }
    
}
