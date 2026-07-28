package dataaccess;

import io.javalin.http.UnauthorizedResponse;
import model.AuthData;

import java.util.HashMap;

public class AuthDAOMemory implements AuthDAO {

    final private HashMap<String, String> authMap = new HashMap<>();

    @Override
    public String getUsername(String authToken) {
        return authMap.get(authToken);
    }

    @Override
    public void createAuth(AuthData authData) {
        authMap.put(authData.authToken(), authData.username());
    }

    @Override
    public boolean verifyAuth(String authToken) {
        return authMap.containsKey(authToken);
    }

    @Override
    public void deleteAuth(String authToken) {
        if (!authMap.containsKey(authToken)) {
            throw new UnauthorizedResponse("Error: invalid authorization");
        }
        else {
            authMap.remove(authToken);
        }
    }

    @Override
    public void clear() {
        authMap.clear();
    }
}
