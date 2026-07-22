package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.HashSet;

public class AuthDAOMemory implements AuthDAO{

    //final private HashMap<String,String> authMap = new HashMap<>();
    final private HashMap<String, String> authMap = new HashMap<>();

    @Override
    public AuthData getAuth(String username) {
        if (authMap.containsKey(username)) {
            return new AuthData(authMap.get(username), username);
        }
        return null;
    }

    @Override
    public void createAuth(AuthData authData) throws DataAccessException {
        if (!authMap.containsKey(authData.username())) {
            authMap.put(authData.username(), authData.authToken());
        }
        else {
            throw new DataAccessException("AuthData already exists");
        }
    }

    @Override
    public void deleteAuth() {

    }
}
