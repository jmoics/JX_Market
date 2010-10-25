package pe.com.jx_market.dao.ibatis;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.jx_market.dao.ContactoDAO;
import pe.com.jx_market.domain.DTO_Contacto;
import pe.com.jx_market.service.Constantes;

public class ContactoDAOibatis extends SqlMapClientDaoSupport implements ContactoDAO {

	public boolean registraContacto(DTO_Contacto p) {
        HashMap mapa = new HashMap();
        mapa.put("username", p.getUsername());
        DTO_Contacto contacto = (DTO_Contacto)getSqlMapClientTemplate().
                queryForObject("getContactoPorUsername", mapa);
        mapa.put("pass", p.getPass());
        mapa.put("nombre", p.getNombre());
        
        if(contacto == null) {
            getSqlMapClientTemplate().insert("insertContacto", mapa);
            return true;
        } else {
            getSqlMapClientTemplate().update("updateContacto", mapa);
            return true;
        }
    }
	
	public DTO_Contacto leeContacto(String uname){
		HashMap mapa = new HashMap();
        mapa.put("username", uname);
		return (DTO_Contacto)getSqlMapClientTemplate().queryForObject("getContactoPorUsername", mapa);
		
	}
	public boolean eliminaContacto(String uname){
		getSqlMapClientTemplate().delete("delContacto", uname);
		return false;
	}
	
	public List<DTO_Contacto> getContactos(Integer institucion){
		HashMap mapa = new HashMap();
        mapa.put("institucion", institucion);
		return getSqlMapClientTemplate().queryForList("getContactos",mapa);
	}
	
    public boolean cambiaPassword(String uname, String password) {
        DTO_Contacto c = leeContacto(uname);
        if(c == null) {
                throw new RuntimeException("No se pudo hallar usuario " + uname + " en tabla");
        }
        HashMap mapa = new HashMap();
		mapa.put("username", uname);
		mapa.put("password", password);
		getSqlMapClientTemplate().update("chgPass", mapa);
        return true;
}
    
    public DTO_Contacto cambiaTheme(String uname, String tema) {
        DTO_Contacto c = leeContacto(uname);
        if(c == null) {
                throw new RuntimeException("No se pudo hallar usuario " + uname + " en tabla");
        }
        HashMap mapa = new HashMap();
		mapa.put("username", uname);
		mapa.put("tema", tema);
		getSqlMapClientTemplate().update("chgTheme", mapa);
        return leeContacto(uname);
}

}
