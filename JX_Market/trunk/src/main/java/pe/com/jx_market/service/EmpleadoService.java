package pe.com.jx_market.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Empleado;
import pe.com.jx_market.domain.DTO_Usuario;
import pe.com.jx_market.persistence.EmpleadoMapper;
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
public class EmpleadoService 
    implements BusinessService 
{
    static Log logger = LogFactory.getLog(EmpleadoService.class);
    @Autowired
    private EmpleadoMapper empleadoMapper;
    @Autowired
    private BusinessService usuarioService;

    /**
     * El DTO_Input contendrá como verbo un String: para realizar una consulta
     * se usará el verbo "list" y un string con el codigo de la institucion a
     * la que pertenece el contacto, para ingresar o actualizar datos se usará
     * el verbo "register" conteniendo además un objeto DTO_Contacto con los
     * datos nuevos a ingresar, y para eliminar datos se usará el verbo
     * "delete" adeḿas de contener un string con el codigo del contacto a
     * eliminar. El DTO_Output tiene codigo de error OK; y si el verbo es "list"
     * contendra una lista de objetos DTO_Contacto con todos los campos leidos
     * de la Base de Datos.
     *
     * @param Objeto estandar de entrada
     * @return Objeto estandar de salida
     */
    @Override
    @Transactional
    public DTO_Output execute (final DTO_Input input) {

        final DTO_Output output = new DTO_Output();
        if (Constantes.V_LIST.equals(input.getVerbo())) {
            final DTO_Empleado empleado = (DTO_Empleado) input.getObject();
            output.setLista(empleadoMapper.getEmpleados(empleado));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getVerbo())) {
            final DTO_Empleado empleado = (DTO_Empleado) input.getObject();
            output.setObject(empleadoMapper.getEmpleados(empleado).get(0));
            output.setErrorCode(Constantes.OK);
            return output;
        }else if (Constantes.V_REGISTER.equals(input.getVerbo())) {
            final Map map = input.getMapa();
            final DTO_Empleado empleado = (DTO_Empleado) map.get("empleado");
            DTO_Usuario usuario = (DTO_Usuario) map.get("usuario");
            final DTO_Input inp = new DTO_Input(usuario);
            if(usuario != null) {
                if (usuario.getCodigo() == null) {
                    inp.setVerbo(Constantes.V_REGISTER);
                    final DTO_Output out = usuarioService.execute(inp);
                    if (out.getErrorCode() == Constantes.OK) {
                        usuario = getUsuario(usuario);
                        empleado.setUsuario(usuario.getCodigo());
                    }
                } else {
                    usuario = getUsuario(usuario);
                    final Map<String, String> map2 = new HashMap<String, String>();
                    map2.put("nonPass", "nonPass");
                    map2.put("oldPass", null);
                    inp.setMapa(map2);
                    inp.setVerbo("chgpass");
                    final DTO_Output out = usuarioService.execute(inp);
                    if (out.getErrorCode() == Constantes.OK) {
                        logger.info("El password fue cambiado correctamente");
                    } else {
                        logger.error("Error al cambiar el password");
                        return output;
                    }
                }
            }
            List<DTO_Empleado> empTmp = empleadoMapper.getEmpleados(empleado);
            if (empTmp == null || empTmp.isEmpty()) {
                empleadoMapper.insertEmpleado(empleado);
            } else {
                empleadoMapper.updateEmpleado(empleado);
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_DELETE.equals(input.getVerbo())) {
            empleadoMapper.eliminaEmpleado((DTO_Empleado) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    private DTO_Usuario getUsuario(final DTO_Usuario user) {
        final DTO_Input inp = new DTO_Input(user);
        inp.setVerbo(Constantes.V_GET);
        final DTO_Output out = usuarioService.execute(inp);
        if(out.getErrorCode() == Constantes.OK) {
            return (DTO_Usuario) out.getObject();
        } else {
            return null;
        }
    }

    public BusinessService getUsuarioService()
    {
        return usuarioService;
    }

    public void setUsuarioService(final BusinessService usuarioService)
    {
        this.usuarioService = usuarioService;
    }

    public EmpleadoMapper getDao () {
        return empleadoMapper;
    }

    public void setDao (final EmpleadoMapper empleadoMapper) {
        this.empleadoMapper = empleadoMapper;
    }

}
