package pe.com.jx_market.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Usuario;
import pe.com.jx_market.persistence.UsuarioMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * Servicio de Autenticacion de Usuarios.
 *
 */
@Service
public class AuthService
    implements BusinessService<DTO_Usuario>
{

    static Log logger = LogFactory.getLog(AuthService.class);
    @Autowired
    private UsuarioMapper usuarioMapper;
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
    public ServiceOutput<DTO_Usuario> execute(final ServiceInput<DTO_Usuario> input)
    {
        final ServiceOutput<DTO_Usuario> output = new ServiceOutput<DTO_Usuario>();
        final DTO_Usuario suposedUser = input.getObject();
        final String password = suposedUser.getContrasena();
        suposedUser.setContrasena("erased");
        final DTO_Usuario us = usuarioMapper.getUsuarioXUsername(suposedUser);

        if (us == null) {
            logger.error("No se proporciono usuario valido");
            output.setErrorCode(Constantes.NOT_FOUND);
            return output;
        }
        if (password == null || password.length() == 0) {
            logger.error("No se proporciono password valido");
            output.setErrorCode(Constantes.NOT_FOUND);
            return output;
        }

        if (encriptacion(password).equals(us.getContrasena())) {
            output.setObject(us);
            output.setErrorCode(Constantes.OK);
            return output;
            // por mientras ya que no hay encriptacion....
        } else if (password.equals(us.getContrasena())) {
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

    public UsuarioMapper getDao()
    {
        return usuarioMapper;
    }

    public void setDao(final UsuarioMapper usuarioMapper)
    {
        this.usuarioMapper = usuarioMapper;
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
