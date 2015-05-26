package pe.com.jx_market.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_User;
import pe.com.jx_market.persistence.UserMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * Servicio de Autenticacion de Users.
 *
 */
@Service
public class AuthService
    implements BusinessService<DTO_User>
{

    /**
     *
     */
    private final Log logger = LogFactory.getLog(AuthService.class);
    /**
     *
     */
    @Autowired
    private UserMapper userMapper;
    /**
     *
     */
    @Autowired
    private BusinessService<String> passwordHashService;

    /**
     * El ServiceInput debe contener un objeto de tipo DTO_Login el cual contiene
     * solo los atributos username y password. En caso de autenticacion
     * positiva, el ServiceOutput tiene codigo de error OK
     *
     * @param _input Objeto estandar de entrada
     * @return Objeto estandar de salida
     */
    @Override
    @Transactional
    public ServiceOutput<DTO_User> execute(final ServiceInput<DTO_User> _input)
    {
        final ServiceOutput<DTO_User> output = new ServiceOutput<DTO_User>();
        final DTO_User suposedUser = _input.getObject();
        final String password = suposedUser.getPassword();
        suposedUser.setPassword("erased");
        final DTO_User us = this.userMapper.getUser4Username(suposedUser);

        if (us == null) {
            this.logger.error("No se proporciono user valido");
            output.setErrorCode(Constantes.NOT_FOUND);
        }
        if (password == null || password.length() == 0) {
            this.logger.error("No se proporciono password valido");
            output.setErrorCode(Constantes.NOT_FOUND);
        }

        if (encriptacion(password).equals(us.getPassword())) {
            output.setObject(us);
            output.setErrorCode(Constantes.OK);
            // por mientras ya que no hay encriptacion....
        } else if (password.equals(us.getPassword())) {
            output.setObject(us);
            output.setErrorCode(Constantes.OK);
        } else {
            output.setErrorCode(Constantes.AUTH_ERROR);
        }
        return output;
    }

    /**
     * @param _pass Password
     * @return String with encrypted password.
     */
    private String encriptacion(final String _pass)
    {
        String passEncriptada = "";
        ServiceOutput<String> output;

        output = this.passwordHashService.execute(new ServiceInput<String>(_pass));
        if (output.getErrorCode() == Constantes.OK) {
            passEncriptada = output.getObject();
        } else {
            throw new RuntimeException("Error en encriptacion hash de password");
        }
        return passEncriptada;
    }
}
