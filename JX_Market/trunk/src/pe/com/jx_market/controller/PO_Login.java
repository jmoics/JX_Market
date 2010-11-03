package pe.com.jx_market.controller;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Cliente;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.domain.DTO_Usuario;
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
		DTO_Usuario usuario = new DTO_Usuario();
		usuario.setCodigo(user.getValue());
		usuario.setContrasena(pass.getValue());
		usuario.setEmpresa("1");
		
		DTO_Usuario validado = (DTO_Usuario)getUsuario(usuario);
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
	
	public DTO_Usuario getUsuario(DTO_Usuario C){
		DTO_Usuario usuario;
		BusinessService authService = Utility.getService(this, "authService");
		DTO_Input input = new DTO_Input(C);
		
		DTO_Output output = authService.execute(input);
		if (output.getErrorCode() == Constantes.OK) {
			usuario = (DTO_Usuario)output.getObject();
		} else {
			usuario = null;
		}
		
		return usuario;
	}
	
	public void prueba(){
		user.setValue("ASDSAD");
		pass.setValue("PAAS");
	}
}
