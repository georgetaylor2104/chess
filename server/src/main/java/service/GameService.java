package service;

import io.javalin.http.UnauthorizedResponse;
import dataaccess.GameDAO;
import dataaccess.DataAccessException;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.ListGamesRequest;
import results.CreateGameResult;
import results.ListGamesResult;
import service.exception.AlreadyTakenException;
import service.exception.GameNotFoundException;

public class GameService {
    GameDAO gameDAO;
    AuthService authService;
    private int gameIDNum = 1000;

    public GameService (GameDAO givenGameDAO, AuthService givenAuthService) {
        gameDAO = givenGameDAO;
        authService = givenAuthService;
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

    public void joinGame(JoinGameRequest request) throws UnauthorizedResponse, AlreadyTakenException, GameNotFoundException, DataAccessException {
        authService.verifyAuthToken(request.authToken());
        if (!gameDAO.contains(request.gameID())) {
            throw new GameNotFoundException("Error: game not found");
        }

        String username = authService.authDAO.getUsername(request.authToken());
        gameDAO.updateGame(request.playerColor(), request.gameID(), username);

    }

    public void clear() {
        gameDAO.clearGames();
    }
}
