package pe.com.jx_market.testing;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import pe.com.jx_market.domain.DTO_User;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;
import pe.com.jx_market.utilities.ServiceProvider;

public class UserServiceTest {

    private static BusinessService userService, passwordHashService, authService;

    @BeforeClass
    public static void setUpBeforeClass () throws Exception {
        // de este modo se deben obtener todos los servicios
        userService = ServiceProvider.getServiceProvider().getService("userService");
        passwordHashService = ServiceProvider.getServiceProvider()
                .getService("passwordHashService");

        authService = ServiceProvider.getServiceProvider().getService("authService");
    }

    @Test
    public void registrarUsers () {

        // nuevo user
        final DTO_User unew = new DTO_User();
        unew.setUsername("jcueva");
        unew.setPassword("jcueva");
        unew.setCompanyId(3);

        final ServiceInput input = new ServiceInput(unew);
        input.setAction("register");
        final ServiceOutput output = userService.execute(input);
        assertEquals(Constantes.OK, output.getErrorCode());

        // ahora debe aparecer en el listado
        final ServiceInput inputList = new ServiceInput(unew.getCompanyId());
        inputList.setAction("list");
        final ServiceOutput outputList = userService.execute(inputList);
        assertEquals(Constantes.OK, outputList.getErrorCode());

        final List<DTO_User> ulist = outputList.getLista();
        boolean hallado = false;
        for (final DTO_User uOut : ulist) {
            if (uOut.getId().equals("jmoics")) {
                hallado = true;
            }
        }
        assertEquals(true, hallado);
    }

    @Test
    public void registraCSeq (){

    }

    @Test
    public void authUsers () {

        // nuevo user
        final DTO_User unew = new DTO_User();
        unew.setUsername("mdiaz");
        unew.setPassword("mdiaz");
        unew.setCompanyId(1);

        final ServiceInput input = new ServiceInput(unew);
        final ServiceOutput output = authService.execute(input);
        assertEquals(Constantes.OK, output.getErrorCode());

    }

    @Test
    public void cambiaPassword () {

        final DTO_User user = new DTO_User();
        user.setUsername("jmoics");
        user.setPassword("xyz");
        user.setCompanyId(1);
        final ServiceInput input = new ServiceInput(user);
        input.setAction("chgpass");
        final Map<String, String> mapa = new HashMap<String, String>();
        mapa.put("oldPass", "jmoics");
        input.setMapa(mapa);
        ServiceOutput output = userService.execute(input);
        // password muy debil
        assertEquals(Constantes.BAD_PASS, output.getErrorCode());

        user.setPassword("bfsfsf4r2dsd");
        output = userService.execute(input);
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
