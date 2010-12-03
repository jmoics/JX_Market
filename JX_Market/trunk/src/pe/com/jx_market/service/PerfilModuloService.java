package pe.com.jx_market.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pe.com.jx_market.dao.ModuloDAO;
import pe.com.jx_market.dao.PerfilDAO;
import pe.com.jx_market.dao.PerfilModuloDAO;
import pe.com.jx_market.domain.DTO_Modulo;
import pe.com.jx_market.domain.DTO_Perfil;
import pe.com.jx_market.domain.DTO_PerfilModulo;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

/**
 * Servicio de Administración de Recursos por Perfil
 * @author diego
 *
 */
public class PerfilModuloService implements BusinessService {

    private PerfilModuloDAO dao;
    private PerfilDAO perfilDAO;
    private ModuloDAO moduloDAO;

    /**El DTO_Input debe contener un Verbo, el cual es un String en el cual se específica la acción a realizar
     * ya sea consulta, registro o eliminación ("list", "register" o "delete" respectivamente)
     */
    @Override
    public DTO_Output execute(final DTO_Input input) {

        final DTO_Output output = new DTO_Output();
        if(Constantes.V_LIST.equals(input.getVerbo())) {
            // obtener los perfiles
            final Map<String, Object> map = input.getMapa();
            final DTO_Perfil perfil = (DTO_Perfil) map.get("perfil");
            final DTO_Modulo modulo = (DTO_Modulo) map.get("modulo");
            
            final List<DTO_Perfil> perfiles = perfilDAO.getPerfiles(perfil);
            final HashMap <DTO_Perfil, Set<Integer>> perfXMod = new HashMap<DTO_Perfil, Set<Integer>>();
            for(final DTO_Perfil perf : perfiles) {
                perfXMod.put(perf, dao.listaPorPerfil(perf));
            }
            output.setLista(moduloDAO.getModulos(modulo));
            output.setMapa(perfXMod);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if(Constantes.V_REGISTER.equals(input.getVerbo())) {
            final Map<DTO_Perfil, Set<DTO_PerfilModulo>> mapa = input.getMapa();
            final Iterator<DTO_Perfil> perfilIterator = mapa.keySet().iterator();
            while(perfilIterator.hasNext()) {
                final DTO_Perfil perfil = perfilIterator.next();
                dao.eliminaPorPerfil(perfil);
                final Set <DTO_PerfilModulo> recursos =  mapa.get(perfil);
                final Iterator <DTO_PerfilModulo> itBlock = recursos.iterator();
                while(itBlock.hasNext()) {
                    final DTO_PerfilModulo perfMod = itBlock.next();
                    System.out.println("Agrego: " + perfMod.getPerfil() + " cod: " + perfMod.getModulo());
                    dao.agregaPorPerfil(perfMod);
                }
            }
            output.setErrorCode(Constantes.OK);
            return output;
        }
        
        throw new RuntimeException("Verbo incorrecto");
    }

    public ModuloDAO getModuloDAO() {
        return moduloDAO;
    }
    
    public void setModuloDAO(final ModuloDAO moduloDAO)
    {
        this.moduloDAO = moduloDAO;
    }

    public PerfilModuloDAO getDao()
    {
        return dao;
    }

    public void setDao(final PerfilModuloDAO dao)
    {
        this.dao = dao;
    }

    public PerfilDAO getPerfilDAO() {
        return perfilDAO;
    }

    public void setPerfilDAO(final PerfilDAO perfilDAO) {
        this.perfilDAO = perfilDAO;
    }
    
}
