package pe.com.jx_market.dao;

import java.util.List;

import pe.com.jx_market.domain.DTO_Usuario;

public interface UsuarioDAO
{

    public DTO_Usuario getUsuarioPorUsername(DTO_Usuario usuario);

    public boolean eliminaUsuario(DTO_Usuario usuario);

    public boolean insertUsuario(DTO_Usuario p);
    
    public boolean updateUsuario(DTO_Usuario p);

    public List<DTO_Usuario> getUsuarios(DTO_Usuario usuario);

    public boolean cambiaPassword(DTO_Usuario usuario);
}
