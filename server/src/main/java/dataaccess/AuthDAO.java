package dataaccess;

import model.AuthData;

public interface AuthDAO {

    void createAuth(AuthData authData);

    AuthData getAuth(AuthData authData);

    void deleteAuth();
}
