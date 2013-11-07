package pe.com.jx_market.service;

import java.util.Set;

import pe.com.jx_market.dao.PerfilModuloDAO;
import pe.com.jx_market.domain.DTO_Empleado;
import pe.com.jx_market.domain.DTO_Perfil;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

/**
 * Servicio de Autorizacion de recursos por perfil
 *
 * @author americati
 *
 */
public class AutorizacionService
    implements BusinessService
{

    private PerfilModuloDAO dao;

    /**
     * El DTO_Input contendrá un objeto DTO_Contacto y un objeto con un recurso
     * especifico o varios, devolviendo en el DTO_Output OK si tiene
     * autorizacion para acceder a ese recurso y AUTH_ERROR si no la tiene.
     *
     * @param Objeto estándar de entrada
     * @return Objeto estándar de salida
     */
    @Override
    public DTO_Output execute(final DTO_Input input)
    {

        final DTO_Output output = new DTO_Output();
        final DTO_Empleado empleado = (DTO_Empleado) input.getMapa().get("empleado");
        final DTO_Perfil perfil = new DTO_Perfil();
        perfil.setCodigo(empleado.getPerfil());
        String[] modulos;
        if (input.getMapa().containsKey("modulo")) {
            final String modulo = (String) input.getMapa().get("modulo");
            modulos = new String[1];
            modulos[0] = modulo;
        } else {
            modulos = (String[]) input.getMapa().get("modulo-array");
        }
        final Set<String> modulosDelPerfil = dao.listaModulosPorPerfil(perfil);
        // debemos validar que todos los recursos solicitados estan en el array
        for (int z = 0; z < modulos.length; z++) {
            if (!modulosDelPerfil.contains(modulos[z])) {
                output.setErrorCode(Constantes.AUTH_ERROR);
                return output;
            }
        }
        output.setErrorCode(Constantes.OK);
        return output;
    }

    public PerfilModuloDAO getDao()
    {
        return dao;
    }

    public void setDao(final PerfilModuloDAO dao)
    {
        this.dao = dao;
    }

}
