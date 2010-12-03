package pe.com.jx_market.dao;

import java.util.Set;

import pe.com.jx_market.domain.DTO_Perfil;
import pe.com.jx_market.domain.DTO_PerfilModulo;

public interface PerfilModuloDAO {

    // obtiene los recursos asociados a un perfil
    public Set<Integer> listaPorPerfil(DTO_Perfil perfil);

    // elimina todos los recursos asociados a un perfil
    public void eliminaPorPerfil(DTO_Perfil perfil);

    // asocia un recurso a un perfil
    public void agregaPorPerfil(DTO_PerfilModulo perfMod);
}
