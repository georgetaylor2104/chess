package handler;

import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.http.Context;
import requests.DeleteRequest;
import service.AuthService;
import service.UserService;
import service.GameService;

public class ClearHandler {
    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;

    public ClearHandler (UserDAO givenUserDAO, AuthDAO givenAuthDAO, GameDAO givenGameDAO) {
        userDAO = givenUserDAO;
        authDAO = givenAuthDAO;
        gameDAO = givenGameDAO;
    }

    public void delete(Context ctx) {
        UserService userService = new UserService(userDAO, authDAO);
        AuthService authService = new AuthService(authDAO);
        GameService gameService = new GameService(gameDAO);

        userService.clear();
        gameService.clear();
        authService.clear();

        ctx.status(200);
        ctx.result("");
    }
}
