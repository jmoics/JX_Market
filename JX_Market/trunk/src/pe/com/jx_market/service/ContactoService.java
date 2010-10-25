package pe.com.jx_market.service;

import java.util.HashSet;

import pe.com.jx_market.dao.ContactoDAO;
import pe.com.jx_market.domain.DTO_Contacto;

import pe.com.jx_market.utilities.*;

/**
 * Servicio de Administracion de Contactos
 * @author jorge
 *
 */

public class ContactoService implements BusinessService {

	private ContactoDAO dao;
	private BusinessService parametroService;

	

	/**El DTO_Input contendrá como verbo un String: para realizar una consulta se usará el verbo "list" y un string con el
	 * codigo de la institucion a la que pertenece el contacto, para ingresar o
	 * actualizar datos se usará el verbo "register" conteniendo además un objeto DTO_Contacto
	 * con los datos nuevos a ingresar, y para eliminar datos se usará el verbo "delete" adeḿas de contener un string 
	 * con el codigo del contacto a eliminar.
	 * El DTO_Output tiene codigo de error OK; y si el verbo es "list" contendra una lista de objetos DTO_Contacto con 
	 * todos los campos leidos de la Base de Datos.
	 * 
	 * @param		Objeto estandar de entrada
	 * @return		Objeto estandar de salida
	 */
	public DTO_Output execute(DTO_Input input) {

        DTO_Output output = new DTO_Output();
        if("list".equals(input.getVerbo())) {
        	//Integer institucion = (Integer)input.getObject();
        	output.setLista(dao.getContactos());
        	output.setErrorCode(Constantes.OK);
        	return output;
        } else if("register".equals(input.getVerbo())) {
        	dao.registraContacto((DTO_Contacto)input.getObject());
        	output.setErrorCode(Constantes.OK);
        	return output;        	
        } else if("delete".equals(input.getVerbo())) {
        	dao.eliminaContacto((String)input.getObject());
        	output.setErrorCode(Constantes.OK);
        	return output; 
        } else if("chgpass".equals(input.getVerbo())) {
            DTO_Contacto contacto = (DTO_Contacto)input.getObject();
            String nuevoPassword = contacto.getPass();
            // aqui se puede aprovechar para hacer algunas validaciones
            if(nuevoPassword.length() < 6) {
                    output.setErrorCode(Constantes.BAD_PASS);
                    return output;
            }
            if(!checkRepeticiones(nuevoPassword)) {
                    output.setErrorCode(Constantes.BAD_PASS);
                    return output;
            }
            // encriptar el password...

            if(dao.cambiaPassword(contacto.getUsername(), nuevoPassword)) {
            	output.setErrorCode(Constantes.OK);
            	return output;
            } else {
                throw new RuntimeException("Ocurrio un error al intentar guardar el nuevo tema");
            }
        } else {
        	throw new RuntimeException("No se especifico verbo adecuado");        
    	}
    }
	
    private boolean checkRepeticiones(String pass) {
        HashSet<Character> letras = new HashSet<Character>();
        for(int z = 0 ; z < pass.length() ; z ++) {
        	letras.add(pass.charAt(z));
        }
        if(letras.size() < 4) {
        	return false;
        }
        return true;
    }

	public ContactoDAO getDao() {
		return dao;
	}

	public void setDao(ContactoDAO dao) {
		this.dao = dao;
	}
    
	public BusinessService getParametroService() {
		return parametroService;
	}

	public void setParametroService(BusinessService parametroService) {
		this.parametroService = parametroService;
	}

}
