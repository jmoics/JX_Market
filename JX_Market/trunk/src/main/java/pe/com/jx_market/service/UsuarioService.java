package pe.com.jx_market.service;

import java.util.HashSet;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Usuario;
import pe.com.jx_market.persistence.UsuarioMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

/**
 * Servicio de Administracion de Contactos
 *
 * @author jorge
 *
 */
@Service
public class UsuarioService
    implements BusinessService
{
    @Autowired
    private UsuarioMapper usuarioDAO;
    @Autowired
    private BusinessService passwordHashService;

    /**
     * El DTO_Input contendrá como verbo un String: para realizar una consulta
     * se usará el verbo "list" y un string con el codigo de la institucion a la
     * que pertenece el contacto, para ingresar o actualizar datos se usará el
     * verbo "register" conteniendo además un objeto DTO_Contacto con los datos
     * nuevos a ingresar, y para eliminar datos se usará el verbo "delete"
     * adeḿas de contener un string con el codigo del contacto a eliminar. El
     * DTO_Output tiene codigo de error OK; y si el verbo es "list" contendra
     * una lista de objetos DTO_Contacto con todos los campos leidos de la Base
     * de Datos.
     *
     * @param Objeto estandar de entrada
     * @return Objeto estandar de salida
     */
    @SuppressWarnings("rawtypes")
    @Override
    @Transactional
    public DTO_Output execute(final DTO_Input input)
    {

        final DTO_Output output = new DTO_Output();
        if (Constantes.V_LIST.equals(input.getVerbo())) {
            final DTO_Usuario usuario = (DTO_Usuario) input.getObject();
            output.setLista(usuarioDAO.getUsuarios(usuario));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getVerbo())) {
            output.setObject(usuarioDAO.getUsuarioXUsername((DTO_Usuario) input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        }else if (Constantes.V_REGISTER.equals(input.getVerbo())) {
            final DTO_Usuario usuario = (DTO_Usuario) input.getObject();
            usuario.setContrasena(encriptaPass(usuario.getContrasena()));
            DTO_Usuario nonused = usuarioDAO.getUsuarioXUsername(usuario);
            if (nonused == null) {
                final Integer idUser = usuarioDAO.insertUsuario(usuario);
                output.setErrorCode(Constantes.OK);
            } else {
                output.setErrorCode(Constantes.ERROR_RC);
            }
            return output;
        } else if (Constantes.V_DELETE.equals(input.getVerbo())) {
            usuarioDAO.eliminaUsuario((DTO_Usuario) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else if ("consultaSiEstaDisponible".equals(input.getVerbo())) {
            final DTO_Usuario us = usuarioDAO.getUsuarioXUsername((DTO_Usuario) input.getObject());
            if (us == null) {
                output.setErrorCode(Constantes.OK);
            } else {
                output.setErrorCode(Constantes.ALREADY_USED);
            }
            return output;
        } else if ("chgpass".equals(input.getVerbo())) {
            final DTO_Usuario usuario = (DTO_Usuario) input.getObject();
            final String nuevoPassword = usuario.getContrasena();
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
            /*if (nonPass == null && !checkPasswordAnterior(usuario, oldPass)) {
                output.setErrorCode(Constantes.BAD_PASS);
                return output;
            }*/
            // encriptar el password..
            usuario.setContrasena(encriptaPass(nuevoPassword));

            DTO_Usuario usrTmp = usuarioDAO.getUsuarioXUsername(usuario);
            if (usrTmp != null) {
                usuarioDAO.cambiaPassword(usuario);
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

    private boolean checkPasswordAnterior(final DTO_Usuario us,
                                          final String pass)
    {
        final DTO_Usuario usuario = usuarioDAO.getUsuarioXUsername(us);
        String passEncriptado;
        // encriptar password enviado
        final DTO_Output output = passwordHashService.execute(new DTO_Input(pass));
        if (output.getErrorCode() == Constantes.OK) {
            passEncriptado = (String) output.getObject();
            if (passEncriptado.equals(usuario.getContrasena())) {
                return true;
            }
        }
        return false;
    }

    private String encriptaPass(final String pass)
    {
        final DTO_Output output = passwordHashService.execute(new DTO_Input(pass));
        if (output.getErrorCode() == Constantes.OK) {
            return (String) output.getObject();
        }
        return null;
    }

    public UsuarioMapper getDao()
    {
        return usuarioDAO;
    }

    public void setDao(final UsuarioMapper usuarioDAO)
    {
        this.usuarioDAO = usuarioDAO;
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
