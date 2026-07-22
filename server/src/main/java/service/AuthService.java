package service;

import model.AuthData;
import dataaccess.AuthDAO;
import java.util.UUID;

public class AuthService {

    public AuthService (AuthDAO authDAO) {}

    public AuthData createAuth(String username) {
        return null;
    }

    public AuthData getAuth(String username) {
        return null;
    }

    public void deleteAuth(AuthData authData) {

    }

    public boolean verifyAuthToken(String authToken) {
        return true;
    }

    private static String generateToken() {
        return UUID.randomUUID().toString();
    }

}
