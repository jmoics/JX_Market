package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.DTO_Usuario;

public interface UsuarioMapper
{

    public DTO_Usuario getUsuarioXUsername(DTO_Usuario usuario);

    public void eliminaUsuario(DTO_Usuario usuario);

    public Integer insertUsuario(DTO_Usuario p);
    
    public void updateUsuario(DTO_Usuario p);

    public List<DTO_Usuario> getUsuarios(DTO_Usuario usuario);

    public void cambiaPassword(DTO_Usuario usuario);
}
