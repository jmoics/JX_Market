package pe.com.jx_market.dao;

import java.util.List;

import pe.com.jx_market.domain.DTO_Perfil;

public interface PerfilDAO
{
    /**
     * @param perfil
     * @return
     */
    public List<DTO_Perfil> getPerfiles(DTO_Perfil perfil);

    /**
     * @param perfil
     * @return
     */
    public DTO_Perfil getPerfilXCodigo(DTO_Perfil perfil);

    /**
     * @param perfil
     * @return
     */
    public Integer insertPerfil(DTO_Perfil perfil);

    /**
     * @param perfil
     * @return
     */
    public boolean deletePerfil(DTO_Perfil perfil);
}
