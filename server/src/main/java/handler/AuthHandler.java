package handler;

import dataaccess.DataAccessException;
import model.AuthData;
import service.AuthService;

public class AuthHandler {
    AuthService authService;

    public AuthHandler(AuthService givenAuthService) {
        authService = givenAuthService;
    }

    public AuthData createAuth(String username) throws DataAccessException {
        return authService.createAuth(username);
    }

    public AuthData getAuth(String username) {
        return authService.getAuth(username);
    }

    public void logoutAuth(String authToken) {

    }
}
