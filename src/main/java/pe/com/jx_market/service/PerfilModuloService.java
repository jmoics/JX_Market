package pe.com.jx_market.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Modulo;
import pe.com.jx_market.domain.DTO_Perfil;
import pe.com.jx_market.domain.DTO_PerfilModulo;
import pe.com.jx_market.persistence.ModuloMapper;
import pe.com.jx_market.persistence.PerfilMapper;
import pe.com.jx_market.persistence.PerfilModuloMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * Servicio de Administración de Recursos por Perfil
 * @author diego
 *
 */
@Service
public class PerfilModuloService implements BusinessService 
{
    @Autowired
    private PerfilModuloMapper perfilModuloMapper;
    @Autowired
    private PerfilMapper perfilMapper;
    @Autowired
    private ModuloMapper moduloMapper;

    /**El ServiceInput debe contener un Verbo, el cual es un String en el cual se específica la acción a realizar
     * ya sea consulta, registro o eliminación ("list", "register" o "delete" respectivamente)
     */
    @Override
    @Transactional
    public ServiceOutput execute(final ServiceInput input) {

        final ServiceOutput output = new ServiceOutput();
        if(Constantes.V_LIST.equals(input.getAccion())) {
            // obtener los perfiles
            final Map<String, Object> map = input.getMapa();
            final DTO_Perfil perfil = (DTO_Perfil) map.get("perfil");
            final DTO_Modulo modulo = (DTO_Modulo) map.get("modulo");

            final List<DTO_Perfil> perfiles = perfilMapper.getPerfiles(perfil);
            final HashMap <DTO_Perfil, Set<Integer>> perfXMod = new HashMap<DTO_Perfil, Set<Integer>>();
            for(final DTO_Perfil perf : perfiles) {
                perfXMod.put(perf, perfilModuloMapper.getModulosPorPerfil(perf));
            }
            output.setLista(moduloMapper.getModulos(modulo));
            output.setMapa(perfXMod);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if(Constantes.V_REGISTER.equals(input.getAccion())) {
            final Map<DTO_Perfil, Set<DTO_PerfilModulo>> mapa = input.getMapa();
            final Iterator<DTO_Perfil> perfilIterator = mapa.keySet().iterator();
            while(perfilIterator.hasNext()) {
                final DTO_Perfil perfil = perfilIterator.next();
                perfilModuloMapper.deleteModuloPerfil(perfil);
                final Set <DTO_PerfilModulo> recursos =  mapa.get(perfil);
                final Iterator <DTO_PerfilModulo> itBlock = recursos.iterator();
                while(itBlock.hasNext()) {
                    final DTO_PerfilModulo perfMod = itBlock.next();
                    System.out.println("Agrego: " + perfMod.getPerfil() + " cod: " + perfMod.getModulo());
                    perfilModuloMapper.insertModuloPerfil(perfMod);
                }
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if(Constantes.V_REGISTERPM.equals(input.getAccion())) {
            final DTO_PerfilModulo perfMod = (DTO_PerfilModulo) input.getObject();
            perfilModuloMapper.insertModuloPerfil(perfMod);
            output.setErrorCode(Constantes.OK);
            return output;
        }

        throw new RuntimeException("Verbo incorrecto");
    }

    public ModuloMapper getModuloDAO() {
        return moduloMapper;
    }

    public void setModuloDAO(final ModuloMapper moduloMapper)
    {
        this.moduloMapper = moduloMapper;
    }

    public PerfilModuloMapper getDao()
    {
        return perfilModuloMapper;
    }

    public void setDao(final PerfilModuloMapper perfilModuloMapper)
    {
        this.perfilModuloMapper = perfilModuloMapper;
    }

    public PerfilMapper getPerfilDAO() {
        return perfilMapper;
    }

    public void setPerfilDAO(final PerfilMapper perfilMapper) {
        this.perfilMapper = perfilMapper;
    }

}
