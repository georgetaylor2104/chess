package dataaccess;

import model.AuthData;

public interface AuthDAO {

    void createAuth();

    AuthData getAuth();

    void deleteAuth();
}
