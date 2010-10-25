package pe.com.jx_market.testing;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import pe.com.jx_market.domain.DTO_Contacto;
import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.*;

public class ContactoServiceTest {

	private static BusinessService contactoService, passwordHashService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ServiceProvider.setConfigurationMode(ServiceProvider.MODE_JDBC);
		// de este modo se deben obtener todos los servicios
		contactoService = ServiceProvider.getServiceProvider().
			getService("contactoService");
		passwordHashService = ServiceProvider.getServiceProvider().
		getService("passwordHashService");
	}

	@Test
	public void registrarUsuarios() {
		
		// nuevo usuario
		DTO_Contacto unew = new DTO_Contacto();
		unew.setUsername("prueba");
		unew.setPass(encriptacion("jocajoca"));
		unew.setNombre("Panchita Vertiz");
		// no se setea ID (es decir, queda en null) para creacion
		// en cambio se debe especificar para actualizarlo
		
		DTO_Input input = new DTO_Input(unew);
		input.setVerbo("register");
		DTO_Output output = contactoService.execute(input);
		assertEquals(Constantes.OK, output.getErrorCode());

		// ahora debe aparecer en el listado
		//el hola demuestra q se puede mandar cualkier objeto pa ser usado pero ahora no se usa
		DTO_Input inputList = new DTO_Input("hola");
		inputList.setVerbo("list");
		DTO_Output outputList = contactoService.execute(inputList);
		assertEquals(Constantes.OK, outputList.getErrorCode());
		
		List<DTO_Contacto> ulist = outputList.getLista();
		boolean hallado = false;
		/*for(DTO_Contacto uOut : ulist) {
			if(uOut.getE_mail().equals("pvertiz@scotia.com") &&
					uOut.getNombre().equals("Panchita Vertiz")) {
				hallado = true;
			}
		}
		assertEquals(true, hallado);*/
		
	}/*
	@Test
	public void cambiaPassword() {
		
		DTO_Contacto contact = new DTO_Contacto();
		contact.setUsername("prueba");
		contact.setPass("xyz");
		DTO_Input input = new DTO_Input(contact);
		input.setVerbo("chgpass");
		DTO_Output output = contactoService.execute(input);
		// password muy debil
		assertEquals(Constantes.BAD_PASS, output.getErrorCode());
		
		contact.setPass("bfsfsf4r2dsd");
		output = contactoService.execute(input);
		// password correcto
		assertEquals(Constantes.OK, output.getErrorCode());		

	}*/
	
	public String encriptacion(String pass) {
		String passEncriptada = "";
		DTO_Output output;

		output = passwordHashService.execute(new DTO_Input(pass));
		if (output.getErrorCode() == Constantes.OK) {
			passEncriptada = (String) output.getObject();
		}
		return passEncriptada;
	}
	
}
