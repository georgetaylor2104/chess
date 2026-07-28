package service;

import dataaccess.AuthDAOMemory;
import dataaccess.DataAccessException;
import dataaccess.GameDAOMemory;
import io.javalin.http.UnauthorizedResponse;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.CreateGameRequest;
import requests.ListGamesRequest;
import results.CreateGameResult;
import java.util.Collection;

public class GameServiceTests {

    GameDAOMemory gameDAOMemory;
    AuthService authService;
    GameService gameService;

    @BeforeEach
    public void setUp() {
        gameDAOMemory = new GameDAOMemory();
        authService = new AuthService(new AuthDAOMemory());
        gameService = new GameService(gameDAOMemory, authService);
    }

    @Test
    public void creatGamePositiveTest() throws DataAccessException {
        AuthData authData = new AuthData("1234", "george");
        authService.authDAO.createAuth(authData);
        CreateGameRequest request = new CreateGameRequest(authData.authToken(), "George's game");
        CreateGameResult result = gameService.createGame(request);
        Assertions.assertTrue(gameDAOMemory.contains(result.gameID()));
    }

    @Test
    public void createGameNegativeTest() throws DataAccessException{
        AuthData authData = new AuthData("1234", "george");
        authService.authDAO.createAuth(authData);
        CreateGameRequest request = new CreateGameRequest("5678", "George's game");
        Assertions.assertThrows(UnauthorizedResponse.class, () -> {gameService.createGame(request);});
    }

    @Test
    public void listGamesPositiveTest() throws DataAccessException {
        AuthData authData = new AuthData("1234", "george");
        authService.authDAO.createAuth(authData);
        CreateGameRequest createRequest1 = new CreateGameRequest(authData.authToken(), "game1");
        CreateGameRequest createRequest2 = new CreateGameRequest(authData.authToken(), "game2");
        gameService.createGame(createRequest1);
        gameService.createGame(createRequest2);
        ListGamesRequest request = new ListGamesRequest(authData.authToken());
        Collection<GameData> list = gameService.listGames(request).games();
        Assertions.assertEquals(2, list.size());
    }

    @Test
    public void listGamesNegativeTest() throws DataAccessException{
        AuthData authData = new AuthData("1234", "george");
        authService.authDAO.createAuth(authData);
        CreateGameRequest createRequest1 = new CreateGameRequest(authData.authToken(), "game1");
        CreateGameRequest createRequest2 = new CreateGameRequest(authData.authToken(), "game2");
        gameService.createGame(createRequest1);
        gameService.createGame(createRequest2);
        ListGamesRequest request = new ListGamesRequest("5678");
        Assertions.assertThrows(UnauthorizedResponse.class, () -> {gameService.listGames(request);});
    }
}
