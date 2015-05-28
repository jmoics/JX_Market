package pe.com.jx_market.persistence;

import java.util.List;

import pe.com.jx_market.domain.DTO_User;

public interface UserMapper
{

    /**
     * @param _user User that contain filters.
     * @return
     */
    public DTO_User getUser4Username(DTO_User _user);

    /**
     * @param _user User to be deleted
     */
    public void eliminaUser(DTO_User _user);

    /**
     * @param _user New User.
     * @return
     */
    public boolean insertUser(DTO_User _user);

    /**
     * @param _user User with modified attributes.
     * @return
     */
    public boolean updateUser(DTO_User _user);

    /**
     * @param _user User that contain filters.
     * @return
     */
    public List<DTO_User> getUsers(DTO_User _user);

    /**
     * @param _user
     */
    public void cambiaPassword(DTO_User _user);
}
