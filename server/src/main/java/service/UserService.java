package service;

import dataaccess.DataAccessException;
import requests.RegisterRequest;
import results.RegisterResult;
import model.UserData;
import model.AuthData;
import dataaccess.UserDAO;
import dataaccess.AuthDAO;
import service.exception.AlreadyTakenException;

public class UserService {

    UserDAO userDAO;
    AuthDAO authDAO;
    AuthService authService;

    public UserService (UserDAO userDAO, AuthDAO authDAO) {
        authService = new AuthService(authDAO);
    }

    public RegisterResult register(RegisterRequest request) throws AlreadyTakenException, DataAccessException {
        UserData checkUserData = userDAO.getUser(request.username());

        if (checkUserData != null) {
            throw new AlreadyTakenException("Error: username already taken");
        }

        UserData userData = new UserData(request.username(), request.password(), request.email());
        userDAO.createUser(userData);
        AuthData authData = authService.createAuth(request.username());

        return new RegisterResult(authData.username(), authData.authToken());
    }

}
