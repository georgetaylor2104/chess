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
    public void createAuth(AuthData authData) {
        authMap.put(authData.username(), authData.authToken());
    }

    @Override
    public void deleteAuth() {

    }
}
