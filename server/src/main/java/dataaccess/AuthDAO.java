package dataaccess;

import model.AuthData;

public interface AuthDAO {

    void createAuth(AuthData authData) throws DataAccessException;

    AuthData getAuth(String username);

    void deleteAuth(String authToken);

    void clear();
}
