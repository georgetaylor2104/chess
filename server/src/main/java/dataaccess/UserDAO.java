package dataaccess;

import model.UserData;

public interface UserDAO {

    void createUser() throws DataAccessException;

    UserData getUser();

}
