package dataaccess;

import io.javalin.http.UnauthorizedResponse;
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
    public boolean verifyAuth(String authToken) {
        boolean verified = false;
        for (String username : authMap.keySet()) {
            if (authMap.get(username).equals(authToken)) {
                verified = true;
            }
        }
        return verified;
    }

    @Override
    public void deleteAuth(String authToken) {
        boolean removed = false;
        for (String username : authMap.keySet()) {
            if (authMap.get(username).equals(authToken)) {
                authMap.remove(username);
                removed = true;
            }
        }

        if (!removed) {
            throw new UnauthorizedResponse("Error: invalid authorization");
        }
    }

    @Override
    public void clear() {
        authMap.clear();
    }
}
