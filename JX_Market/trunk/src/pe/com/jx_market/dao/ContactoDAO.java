package pe.com.jx_market.dao;

import java.util.List;

import pe.com.jx_market.domain.DTO_Contacto;

public interface ContactoDAO {

	public DTO_Contacto leeContacto(String uname);
	public boolean eliminaContacto(String uname);
	public boolean registraContacto(DTO_Contacto p);
    public List<DTO_Contacto> getContactos();
    public boolean cambiaPassword(String uname, String password);
    public DTO_Contacto cambiaTheme(String uname, String tema);
}
