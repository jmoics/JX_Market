package pe.com.jx_market.controller;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Contacto;
import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;


public class PO_Login extends Window {
	
	//BusinessService  passwordHashService;
	Textbox user, pass;
	
	public void onCreate(){
		//passwordHashService = ServiceProvider.getServiceProvider().getService("passwordHashService");
		user = (Textbox)getFellow("user");
		pass = (Textbox)getFellow("pass");
		user.setFocus(true);
	}
	
	public void authenticate() {
		DTO_Contacto usuario = new DTO_Contacto();
		usuario.setUsername(user.getValue());
		usuario.setPass(pass.getValue());
		DTO_Contacto validado=(DTO_Contacto)getUsuario(usuario);
		if(validado!=null) {
			getDesktop().getSession().setAttribute("login", validado);
			Executions.sendRedirect("menu.zul");
		} else {
			user.setText("");
			user.setFocus(true);
			pass.setText("");
			getFellow("badauth").setVisible(true);
		}
	}
	
	public DTO_Contacto getUsuario(DTO_Contacto C){
		DTO_Contacto usuario;
		BusinessService authService = Utility.getService(this, "authService");
		DTO_Input input = new DTO_Input(C);
		
		DTO_Output output = authService.execute(input);
		if (output.getErrorCode() == Constantes.OK) {
			usuario=(DTO_Contacto)output.getObject();
		} else {
			usuario=null;
		}
		
		return usuario;
	}
	
	public void prueba(){
		user.setValue("ASDSAD");
		pass.setValue("PAAS");
	}
}
