package dataaccess;

import model.UserData;

public class UserDAOMemory implements UserDAO {

    @Override
    public void createUser(UserData userData) throws DataAccessException {

    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }
}
