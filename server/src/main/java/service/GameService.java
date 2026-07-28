package service;

import dataaccess.AuthDAO;
import io.javalin.http.UnauthorizedResponse;
import model.AuthData;
import model.GameData;
import dataaccess.GameDAO;
import dataaccess.DataAccessException;
import requests.CreateGameRequest;
import requests.ListGamesRequest;
import results.CreateGameResult;
import results.ListGamesResult;

import java.util.random.RandomGenerator;

public class GameService {
    GameDAO gameDAO;
    AuthService authService;
    private int gameIDNum = 1000;


    public GameService (GameDAO givenGameDAO, AuthDAO authDAO) {
        gameDAO = givenGameDAO;
        authService = new AuthService(authDAO);
    }


    public CreateGameResult createGame(CreateGameRequest request) throws UnauthorizedResponse {
        authService.verifyAuthToken(request.authToken());
        int gameID = gameIDNum;
        gameIDNum++;
        gameDAO.createGame(request.gameName(), gameID);
        return new CreateGameResult(gameID);
    }

    public ListGamesResult listGames(ListGamesRequest request) throws UnauthorizedResponse {
        authService.verifyAuthToken(request.authToken());
        return new ListGamesResult(gameDAO.listGames());
    }

    public void clear() {
        gameDAO.clearGames();
    }
}
