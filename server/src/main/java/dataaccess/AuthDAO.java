package dataaccess;

import model.AuthData;

public interface AuthDAO {

    void createAuth(AuthData authData) throws DataAccessException;

    String getUsername(String authToken);

    void deleteAuth(String authToken);

    boolean verifyAuth(String authToken);

    void clear();
}
