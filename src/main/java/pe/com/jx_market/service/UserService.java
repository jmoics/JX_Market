package pe.com.jx_market.service;

import java.util.HashSet;
import java.util.List;
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
    implements BusinessService<DTO_User>
{
    /**
     *
     */
    @Autowired
    private UserMapper userDAO;
    /**
     *
     */
    @Autowired
    private BusinessService<String> passwordHashService;

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
     * @param _input Objeto estandar de entrada
     * @return Objeto estandar de salida
     */
    @SuppressWarnings("rawtypes")
    @Override
    @Transactional
    public ServiceOutput<DTO_User> execute(final ServiceInput<DTO_User> _input)
    {

        final ServiceOutput<DTO_User> output = new ServiceOutput<DTO_User>();
        if (Constantes.V_LIST.equals(_input.getAction())) {
            final DTO_User user = _input.getObject();
            final List<DTO_User> lstUsers = this.userDAO.getUsers(user);
            // buildLocaleFromString(lstUsers);
            output.setLista(lstUsers);
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_GET.equals(_input.getAction())) {
            final DTO_User user = this.userDAO.getUser4Username(_input.getObject());
            output.setObject(user);
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_REGISTER.equals(_input.getAction())) {
            final DTO_User user = _input.getObject();
            user.setPassword(encriptaPass(user.getPassword()));
            final DTO_User nonused = this.userDAO.getUser4Username(user);
            if (nonused == null) {
                this.userDAO.insertUser(user);
                output.setErrorCode(Constantes.OK);
            } else {
                output.setErrorCode(Constantes.ERROR_RC);
            }
        } else if (Constantes.V_DELETE.equals(_input.getAction())) {
            this.userDAO.eliminaUser(_input.getObject());
            output.setErrorCode(Constantes.OK);
        } else if ("consultaSiEstaDisponible".equals(_input.getAction())) {
            final DTO_User us = this.userDAO.getUser4Username(_input.getObject());
            if (us == null) {
                output.setErrorCode(Constantes.OK);
            } else {
                output.setErrorCode(Constantes.ALREADY_USED);
            }
        } else if ("chgpass".equals(_input.getAction())) {
            final DTO_User user = _input.getObject();
            final String nuevoPassword = user.getPassword();
            final Map map = _input.getMapa();
            //final String oldPass = (String) map.get("oldPass");
            String nonPass = null;
            if (map != null && map.containsKey("nonPass")) {
                nonPass = (String) map.get("nonPass");
            }

            // aqui se puede aprovechar para hacer algunas validaciones
            if (nuevoPassword.length() < 6) {
                output.setErrorCode(Constantes.BAD_PASS);
            }
            if (!checkRepeticiones(nuevoPassword)) {
                output.setErrorCode(Constantes.BAD_PASS);
            }
            /*if (nonPass == null && !checkPasswordAnterior(user, oldPass)) {
                output.setErrorCode(Constantes.BAD_PASS);
                return output;
            }*/
            // encriptar el password..
            user.setPassword(encriptaPass(nuevoPassword));

            final DTO_User usrTmp = this.userDAO.getUser4Username(user);
            if (usrTmp != null) {
                this.userDAO.cambiaPassword(user);
                output.setErrorCode(Constantes.OK);
            } else {
                throw new RuntimeException("Ocurrio un error al intentar guardar el nuevo tema");
            }
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
        return output;
    }

    /**
     * @param pass Password.
     * @return Boolean with compare answer.
     */
    private boolean checkRepeticiones(final String _pass)
    {
        final HashSet<Character> letras = new HashSet<Character>();
        boolean ret = true;
        for (int z = 0; z < _pass.length(); z++) {
            letras.add(_pass.charAt(z));
        }
        if (letras.size() < 4) {
            ret = false;
        }
        return ret;
    }

    /**
     * @param _us User.
     * @param _pass Previous password
     * @return Boolean with compare answer.
     */
    private boolean checkPasswordAnterior(final DTO_User _us,
                                          final String _pass)
    {
        final DTO_User user = this.userDAO.getUser4Username(_us);
        boolean ret = false;
        String passEncriptado;
        // encriptar password enviado
        final ServiceOutput<String> output = this.passwordHashService.execute(new ServiceInput<String>(_pass));
        if (output.getErrorCode() == Constantes.OK) {
            passEncriptado = output.getObject();
            if (passEncriptado.equals(user.getPassword())) {
                ret = true;
            }
        }
        return ret;
    }

    /**
     * @param _pass password.
     * @return String with encrypted password.
     */
    private String encriptaPass(final String _pass)
    {
        String ret = null;
        final ServiceOutput output = this.passwordHashService.execute(new ServiceInput(_pass));
        if (output.getErrorCode() == Constantes.OK) {
            ret = (String) output.getObject();
        }
        return ret;
    }

    /*@SuppressWarnings("unchecked")
    private void buildLocaleFromString(final Object _user) {
        if (_user instanceof List) {
            final List<DTO_User> lstUsers = (List<DTO_User>) _user;
            for (final DTO_User user : lstUsers) {
                if (user.getLocaleStr().indexOf("_") >= 0) {
                    final Locale loc = new Locale(user.getLocaleStr().split("_")[0], user.getLocaleStr().split("_")[1]);
                    user.setLocale(loc);
                } else {
                    final Locale loc = new Locale(user.getLocaleStr());
                    user.setLocale(loc);
                }
            }
        } else if (_user instanceof DTO_User) {
            final DTO_User user = (DTO_User) _user;
            if (user.getLocaleStr().indexOf("_") >= 0) {
                final Locale loc = new Locale(user.getLocaleStr().split("_")[0], user.getLocaleStr().split("_")[1]);
                user.setLocale(loc);
            } else {
                final Locale loc = new Locale(user.getLocaleStr());
                user.setLocale(loc);
            }
        }
    }*/
}
