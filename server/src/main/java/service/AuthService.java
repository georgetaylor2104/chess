package service;

import dataaccess.DataAccessException;
import model.AuthData;
import dataaccess.AuthDAO;
import java.util.UUID;

public class AuthService {

    AuthDAO authDAO;

    public AuthService (AuthDAO givenAuthDAO) {
        authDAO = givenAuthDAO;
    }

    public AuthData createAuth(String username) throws DataAccessException {
        String token = generateToken();
        if (username == null) {
            throw new DataAccessException("Error: username cannot be null");
        }
        AuthData authData = new AuthData(token, username);
        authDAO.createAuth(authData);
        return authData;
    }

    public AuthData getAuth(String username) {
        return authDAO.getAuth(username);
    }

    public void logoutAuth(String authToken) {
        authDAO.deleteAuth(authToken);
    }

    public boolean verifyAuthToken(String authToken) {
        return true;
    }

    private static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void clear() {
        authDAO.clear();
    }

}
