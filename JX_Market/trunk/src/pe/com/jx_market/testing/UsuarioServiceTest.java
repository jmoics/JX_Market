package pe.com.jx_market.testing;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import pe.com.jx_market.domain.DTO_Cliente;
import pe.com.jx_market.domain.DTO_Usuario;
import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.*;

public class UsuarioServiceTest {

    private static BusinessService usuarioService, passwordHashService, authService;

    @BeforeClass
    public static void setUpBeforeClass () throws Exception {
        ServiceProvider.setConfigurationMode(ServiceProvider.MODE_JDBC);
        // de este modo se deben obtener todos los servicios
        usuarioService = ServiceProvider.getServiceProvider().getService("usuarioService");
        passwordHashService = ServiceProvider.getServiceProvider()
                .getService("passwordHashService");

        authService = ServiceProvider.getServiceProvider().getService("authService");
    }

    @Test
    public void registrarUsuarios () {

        // nuevo usuario
        DTO_Usuario unew = new DTO_Usuario();
        unew.setUsername("jcueva");
        unew.setContrasena("jcueva");
        unew.setEmpresa(3);

        DTO_Input input = new DTO_Input(unew);
        input.setVerbo("register");
        DTO_Output output = usuarioService.execute(input);
        assertEquals(Constantes.OK, output.getErrorCode());

        // ahora debe aparecer en el listado
        DTO_Input inputList = new DTO_Input(unew.getEmpresa());
        inputList.setVerbo("list");
        DTO_Output outputList = usuarioService.execute(inputList);
        assertEquals(Constantes.OK, outputList.getErrorCode());

        List<DTO_Usuario> ulist = outputList.getLista();
        boolean hallado = false;
        for (DTO_Usuario uOut : ulist) {
            if (uOut.getCodigo().equals("jmoics")) {
                hallado = true;
            }
        }
        assertEquals(true, hallado);
    }
    
    @Test
    public void registraCSeq (){
        
    }

    @Test
    public void authUsuarios () {

        // nuevo usuario
        DTO_Usuario unew = new DTO_Usuario();
        unew.setUsername("mdiaz");
        unew.setContrasena("mdiaz");
        unew.setEmpresa(1);

        DTO_Input input = new DTO_Input(unew);
        DTO_Output output = authService.execute(input);
        assertEquals(Constantes.OK, output.getErrorCode());

    }

    @Test
    public void cambiaPassword () {

        DTO_Usuario user = new DTO_Usuario();
        user.setUsername("jmoics");
        user.setContrasena("xyz");
        user.setEmpresa(1);
        DTO_Input input = new DTO_Input(user);
        input.setVerbo("chgpass");
        Map<String, String> mapa = new HashMap<String, String>();
        mapa.put("oldPass", "jmoics");
        input.setMapa(mapa);
        DTO_Output output = usuarioService.execute(input);
        // password muy debil
        assertEquals(Constantes.BAD_PASS, output.getErrorCode());

        user.setContrasena("bfsfsf4r2dsd");
        output = usuarioService.execute(input);
        // password correcto
        assertEquals(Constantes.OK, output.getErrorCode());

    }

    public String encriptacion (String pass) {
        String passEncriptada = "";
        DTO_Output output;

        output = passwordHashService.execute(new DTO_Input(pass));
        if (output.getErrorCode() == Constantes.OK) {
            passEncriptada = (String) output.getObject();
        }
        return passEncriptada;
    }

}
