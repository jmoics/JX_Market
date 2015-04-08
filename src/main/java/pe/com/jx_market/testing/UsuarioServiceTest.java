package pe.com.jx_market.testing;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import pe.com.jx_market.domain.DTO_Usuario;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;
import pe.com.jx_market.utilities.ServiceProvider;

public class UsuarioServiceTest {

    private static BusinessService usuarioService, passwordHashService, authService;

    @BeforeClass
    public static void setUpBeforeClass () throws Exception {
        // de este modo se deben obtener todos los servicios
        usuarioService = ServiceProvider.getServiceProvider().getService("usuarioService");
        passwordHashService = ServiceProvider.getServiceProvider()
                .getService("passwordHashService");

        authService = ServiceProvider.getServiceProvider().getService("authService");
    }

    @Test
    public void registrarUsuarios () {

        // nuevo usuario
        final DTO_Usuario unew = new DTO_Usuario();
        unew.setUsername("jcueva");
        unew.setContrasena("jcueva");
        unew.setEmpresa(3);

        final ServiceInput input = new ServiceInput(unew);
        input.setAccion("register");
        final ServiceOutput output = usuarioService.execute(input);
        assertEquals(Constantes.OK, output.getErrorCode());

        // ahora debe aparecer en el listado
        final ServiceInput inputList = new ServiceInput(unew.getEmpresa());
        inputList.setAccion("list");
        final ServiceOutput outputList = usuarioService.execute(inputList);
        assertEquals(Constantes.OK, outputList.getErrorCode());

        final List<DTO_Usuario> ulist = outputList.getLista();
        boolean hallado = false;
        for (final DTO_Usuario uOut : ulist) {
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
        final DTO_Usuario unew = new DTO_Usuario();
        unew.setUsername("mdiaz");
        unew.setContrasena("mdiaz");
        unew.setEmpresa(1);

        final ServiceInput input = new ServiceInput(unew);
        final ServiceOutput output = authService.execute(input);
        assertEquals(Constantes.OK, output.getErrorCode());

    }

    @Test
    public void cambiaPassword () {

        final DTO_Usuario user = new DTO_Usuario();
        user.setUsername("jmoics");
        user.setContrasena("xyz");
        user.setEmpresa(1);
        final ServiceInput input = new ServiceInput(user);
        input.setAccion("chgpass");
        final Map<String, String> mapa = new HashMap<String, String>();
        mapa.put("oldPass", "jmoics");
        input.setMapa(mapa);
        ServiceOutput output = usuarioService.execute(input);
        // password muy debil
        assertEquals(Constantes.BAD_PASS, output.getErrorCode());

        user.setContrasena("bfsfsf4r2dsd");
        output = usuarioService.execute(input);
        // password correcto
        assertEquals(Constantes.OK, output.getErrorCode());

    }

    public String encriptacion (final String pass) {
        String passEncriptada = "";
        ServiceOutput output;

        output = passwordHashService.execute(new ServiceInput(pass));
        if (output.getErrorCode() == Constantes.OK) {
            passEncriptada = (String) output.getObject();
        }
        return passEncriptada;
    }

}
