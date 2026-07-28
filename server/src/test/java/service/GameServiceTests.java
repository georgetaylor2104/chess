package service;

import dataaccess.AuthDAOMemory;
import dataaccess.GameDAOMemory;
import dataaccess.UserDAOMemory;
import io.javalin.http.UnauthorizedResponse;
import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.CreateGameRequest;
import results.CreateGameResult;


public class GameServiceTests {

    GameDAOMemory gameDAOMemory;
    AuthDAOMemory authDAOMemory;
    GameService gameService;

    @BeforeEach
    public void setUp() {
        gameDAOMemory = new GameDAOMemory();
        authDAOMemory = new AuthDAOMemory();
        gameService = new GameService(gameDAOMemory, authDAOMemory);
    }

    @Test
    public void creatGamePositiveTest() {
        AuthData authData = new AuthData("1234", "george");
        authDAOMemory.createAuth(authData);
        CreateGameRequest request = new CreateGameRequest(authData.authToken(), "George's game");
        CreateGameResult result = gameService.createGame(request);
        Assertions.assertTrue(gameDAOMemory.contains(result.gameID()));
    }

    @Test
    public void createGameNegativeTest() {
        AuthData authData = new AuthData("1234", "george");
        authDAOMemory.createAuth(authData);
        CreateGameRequest request = new CreateGameRequest("5678", "George's game");
        Assertions.assertThrows(UnauthorizedResponse.class, () -> {gameService.createGame(request);});
    }

    @Test
    public void listGamesPositiveTest() {
        AuthData authData = new AuthData("1234", "george");
        authDAOMemory.createAuth(authData);
        CreateGameRequest createRequest1 = new CreateGameRequest(authData.authToken(), "game1");
        CreateGameRequest createRequest2 = new CreateGameRequest(authData.authToken(), "game2");

    }

    @Test
    public void listGamesNegativeTest() {

    }
}
