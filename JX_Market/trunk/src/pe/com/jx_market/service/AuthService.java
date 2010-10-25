package pe.com.jx_market.service;

import org.apache.commons.logging.*;

import pe.com.jx_market.dao.ContactoDAO;
import pe.com.jx_market.domain.DTO_Contacto;
import pe.com.jx_market.utilities.*;


/**
 * Servicio de Autenticacion de Usuarios.
 *
 */

public class AuthService implements BusinessService {

	static Log logger = LogFactory.getLog(AuthService.class);
	private ContactoDAO dao;
	private BusinessService passwordHashService;
	
	/** El DTO_Input debe contener un objeto de tipo DTO_Login el cual contiene solo los atributos username y password.
	 *  En caso de autenticacion positiva, el DTO_Output tiene codigo de error OK
	 *  @param		Objeto estandar de entrada
	 *  @return	 	Objeto estandar de salida
	 */
	public DTO_Output execute(DTO_Input input) {
		DTO_Output output = new DTO_Output();
		DTO_Contacto suposedUser = (DTO_Contacto) input.getObject();
		String username = (String)suposedUser.getUsername();
		String password = (String)suposedUser.getPass();
		suposedUser.setPass("erased");
		DTO_Contacto usr = dao.leeContacto(username);
		
		if(usr == null) {
			logger.error("No se proporciono usuario valido");
			output.setErrorCode(Constantes.NOT_FOUND);
			return output;
		}
		if(password == null || password.length() == 0) {
			logger.error("No se proporciono password valido");
			output.setErrorCode(Constantes.NOT_FOUND);
			return output;
		}
	
		if(encriptacion(password).equals(usr.getPass())) {
				output.setObject(usr);
				output.setErrorCode(Constantes.OK);
			return output;
		} else {
			output.setErrorCode(Constantes.AUTH_ERROR);
			return output;
		}			
	}
	
	private String encriptacion(String pass){
		String passEncriptada="";
		DTO_Output output ;
		
		output = passwordHashService.execute(new DTO_Input(pass));
		if(output.getErrorCode()==Constantes.OK){
			passEncriptada= (String) output.getObject();
		}else{
			throw new RuntimeException("Error en encriptacion hash de password");
		}		
		return passEncriptada;
	}
	
	public ContactoDAO getDao() {
		return dao;
	}

	public void setDao(ContactoDAO dao) {
		this.dao = dao;
	}

	public BusinessService getPasswordHashService() {
		return passwordHashService;
	}

	public void setPasswordHashService(BusinessService passwordHashService) {
		this.passwordHashService = passwordHashService;
	}

	
}
