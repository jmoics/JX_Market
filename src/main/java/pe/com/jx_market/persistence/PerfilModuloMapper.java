package pe.com.jx_market.persistence;

import java.util.Set;

import pe.com.jx_market.domain.DTO_Perfil;
import pe.com.jx_market.domain.DTO_PerfilModulo;

public interface PerfilModuloMapper {

    // obtiene los id de los modulos asociados a un perfil
    public Set<Integer> getModulosPorPerfil(DTO_Perfil perfil);

    // obtiene el bean modulo asociados a un perfil
    public Set<String> getModuloStringPorPerfil(DTO_Perfil perfil);

    // elimina todos los recursos asociados a un perfil
    public void deleteModuloPerfil(DTO_Perfil perfil);

    // asocia un recurso a un perfil
    public void insertModuloPerfil(DTO_PerfilModulo perfMod);
}
