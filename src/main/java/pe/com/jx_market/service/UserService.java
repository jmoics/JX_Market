package pe.com.jx_market.service;

import java.util.HashSet;
import java.util.Map;

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
 * Servicio de Administracion de Contactos
 *
 * @author jorge
 *
 */
@Service
public class UserService
    implements BusinessService
{
    @Autowired
    private UserMapper userDAO;
    @Autowired
    private BusinessService passwordHashService;

    /**
     * El ServiceInput contendrá como verbo un String: para realizar una consulta
     * se usará el verbo "list" y un string con el codigo de la institucion a la
     * que pertenece el contacto, para ingresar o actualizar datos se usará el
     * verbo "register" conteniendo además un objeto DTO_Contacto con los datos
     * nuevos a ingresar, y para eliminar datos se usará el verbo "delete"
     * adeḿas de contener un string con el codigo del contacto a eliminar. El
     * ServiceOutput tiene codigo de error OK; y si el verbo es "list" contendra
     * una lista de objetos DTO_Contacto con todos los campos leidos de la Base
     * de Datos.
     *
     * @param Objeto estandar de entrada
     * @return Objeto estandar de salida
     */
    @SuppressWarnings("rawtypes")
    @Override
    @Transactional
    public ServiceOutput execute(final ServiceInput input)
    {

        final ServiceOutput output = new ServiceOutput();
        if (Constantes.V_LIST.equals(input.getAccion())) {
            final DTO_User user = (DTO_User) input.getObject();
            output.setLista(userDAO.getUsers(user));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getAccion())) {
            output.setObject(userDAO.getUser4Username((DTO_User) input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        }else if (Constantes.V_REGISTER.equals(input.getAccion())) {
            final DTO_User user = (DTO_User) input.getObject();
            user.setPassword(encriptaPass(user.getPassword()));
            final DTO_User nonused = userDAO.getUser4Username(user);
            if (nonused == null) {
                final Integer idUser = userDAO.insertUser(user);
                output.setErrorCode(Constantes.OK);
            } else {
                output.setErrorCode(Constantes.ERROR_RC);
            }
            return output;
        } else if (Constantes.V_DELETE.equals(input.getAccion())) {
            userDAO.eliminaUser((DTO_User) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else if ("consultaSiEstaDisponible".equals(input.getAccion())) {
            final DTO_User us = userDAO.getUser4Username((DTO_User) input.getObject());
            if (us == null) {
                output.setErrorCode(Constantes.OK);
            } else {
                output.setErrorCode(Constantes.ALREADY_USED);
            }
            return output;
        } else if ("chgpass".equals(input.getAccion())) {
            final DTO_User user = (DTO_User) input.getObject();
            final String nuevoPassword = user.getPassword();
            final Map map = input.getMapa();
            //final String oldPass = (String) map.get("oldPass");
            String nonPass = null;
            if(map != null && map.containsKey("nonPass")){
                nonPass = (String) map.get("nonPass");
            }

            // aqui se puede aprovechar para hacer algunas validaciones
            if (nuevoPassword.length() < 6) {
                output.setErrorCode(Constantes.BAD_PASS);
                return output;
            }
            if (!checkRepeticiones(nuevoPassword)) {
                output.setErrorCode(Constantes.BAD_PASS);
                return output;
            }
            /*if (nonPass == null && !checkPasswordAnterior(user, oldPass)) {
                output.setErrorCode(Constantes.BAD_PASS);
                return output;
            }*/
            // encriptar el password..
            user.setPassword(encriptaPass(nuevoPassword));

            final DTO_User usrTmp = userDAO.getUser4Username(user);
            if (usrTmp != null) {
                userDAO.cambiaPassword(user);
                output.setErrorCode(Constantes.OK);
                return output;
            } else {
                throw new RuntimeException("Ocurrio un error al intentar guardar el nuevo tema");
            }
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    private boolean checkRepeticiones(final String pass)
    {
        final HashSet<Character> letras = new HashSet<Character>();
        for (int z = 0; z < pass.length(); z++) {
            letras.add(pass.charAt(z));
        }
        if (letras.size() < 4) {
            return false;
        }
        return true;
    }

    private boolean checkPasswordAnterior(final DTO_User us,
                                          final String pass)
    {
        final DTO_User user = userDAO.getUser4Username(us);
        String passEncriptado;
        // encriptar password enviado
        final ServiceOutput output = passwordHashService.execute(new ServiceInput(pass));
        if (output.getErrorCode() == Constantes.OK) {
            passEncriptado = (String) output.getObject();
            if (passEncriptado.equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    private String encriptaPass(final String pass)
    {
        final ServiceOutput output = passwordHashService.execute(new ServiceInput(pass));
        if (output.getErrorCode() == Constantes.OK) {
            return (String) output.getObject();
        }
        return null;
    }

    public UserMapper getDao()
    {
        return userDAO;
    }

    public void setDao(final UserMapper userDAO)
    {
        this.userDAO = userDAO;
    }

    public BusinessService getPasswordHashService()
    {
        return passwordHashService;
    }

    public void setPasswordHashService(final BusinessService passwordHashService)
    {
        this.passwordHashService = passwordHashService;
    }
}
