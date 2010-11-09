package pe.com.jx_market.dao;

import java.util.List;

import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.domain.DTO_Usuario;

public interface UsuarioDAO {

    public DTO_Usuario leeUsuario (String username, Integer empresa);

    public boolean eliminaUsuario (DTO_Usuario usuario);

    public boolean registraUsuario (DTO_Usuario p);

    public List<DTO_Usuario> getUsuarios (Integer empresa);

    public boolean cambiaPassword (DTO_Usuario usuario);
}
