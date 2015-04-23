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

    static Log logger = LogFactory.getLog(AuthService.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BusinessService<String> passwordHashService;

    /**
     * El ServiceInput debe contener un objeto de tipo DTO_Login el cual contiene
     * solo los atributos username y password. En caso de autenticacion
     * positiva, el ServiceOutput tiene codigo de error OK
     *
     * @param Objeto estandar de entrada
     * @return Objeto estandar de salida
     */
    @Override
    @Transactional
    public ServiceOutput<DTO_User> execute(final ServiceInput<DTO_User> input)
    {
        final ServiceOutput<DTO_User> output = new ServiceOutput<DTO_User>();
        final DTO_User suposedUser = input.getObject();
        final String password = suposedUser.getPassword();
        suposedUser.setPassword("erased");
        final DTO_User us = userMapper.getUser4Username(suposedUser);

        if (us == null) {
            logger.error("No se proporciono user valido");
            output.setErrorCode(Constantes.NOT_FOUND);
            return output;
        }
        if (password == null || password.length() == 0) {
            logger.error("No se proporciono password valido");
            output.setErrorCode(Constantes.NOT_FOUND);
            return output;
        }

        if (encriptacion(password).equals(us.getPassword())) {
            output.setObject(us);
            output.setErrorCode(Constantes.OK);
            return output;
            // por mientras ya que no hay encriptacion....
        } else if (password.equals(us.getPassword())) {
            output.setObject(us);
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            output.setErrorCode(Constantes.AUTH_ERROR);
            return output;
        }
    }

    private String encriptacion(final String pass)
    {
        String passEncriptada = "";
        ServiceOutput<String> output;

        output = passwordHashService.execute(new ServiceInput<String>(pass));
        if (output.getErrorCode() == Constantes.OK) {
            passEncriptada = output.getObject();
        } else {
            throw new RuntimeException("Error en encriptacion hash de password");
        }
        return passEncriptada;
    }

    public UserMapper getDao()
    {
        return userMapper;
    }

    public void setDao(final UserMapper userMapper)
    {
        this.userMapper = userMapper;
    }

    public BusinessService<String> getPasswordHashService()
    {
        return passwordHashService;
    }

    public void setPasswordHashService(final BusinessService<String> passwordHashService)
    {
        this.passwordHashService = passwordHashService;
    }

}
