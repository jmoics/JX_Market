package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.DTO_User;

public interface UserMapper
{

    public DTO_User getUser4Username(DTO_User user);

    public void eliminaUser(DTO_User user);

    public Integer insertUser(DTO_User p);

    public void updateUser(DTO_User p);

    public List<DTO_User> getUsers(DTO_User user);

    public void cambiaPassword(DTO_User user);
}
