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
        AuthData authData = new AuthData(token, username);
        authDAO.createAuth(authData);
        return authData;
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
