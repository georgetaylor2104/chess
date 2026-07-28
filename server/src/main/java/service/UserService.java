package service;

import dataaccess.DataAccessException;
import io.javalin.http.UnauthorizedResponse;
import requests.LoginRequest;
import requests.LogoutRequest;
import requests.RegisterRequest;
import results.LoginResult;
import results.RegisterResult;
import model.UserData;
import model.AuthData;
import dataaccess.UserDAO;
import service.exception.AlreadyTakenException;

public class UserService {

    UserDAO userDAO;
    AuthService authService;

    public UserService (UserDAO givenUserDAO, AuthService givenAuthService) {
        userDAO = givenUserDAO;
        authService = givenAuthService;
    }

    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        UserData checkUserData = userDAO.getUser(request.username());

        if (checkUserData != null) {
            throw new AlreadyTakenException("Error: already taken");
        }

        UserData userData = new UserData(request.username(), request.password(), request.email());
        userDAO.createUser(userData);
        AuthData authData = authService.createAuth(request.username());

        return new RegisterResult(authData.username(), authData.authToken());
    }

    public LoginResult login(LoginRequest request) throws DataAccessException, UnauthorizedResponse {
        UserData userData = userDAO.getUser(request.username());

        if (userData == null || !userData.password().equals(request.password())) {
            throw new UnauthorizedResponse("Error: unauthorized");
        }

        AuthData authData = authService.createAuth(request.username());
        return new LoginResult(authData.username(), authData.authToken());
    }

    public void logout(LogoutRequest request) throws DataAccessException, UnauthorizedResponse {
        String authToken = request.authToken();
        authService.logoutAuth(authToken);
    }

    public void clear() {
        userDAO.clear();
        authService.clear();
    }
}
