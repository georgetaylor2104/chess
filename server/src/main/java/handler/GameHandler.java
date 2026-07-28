package handler;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Handler;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import requests.CreateGameRequest;
import requests.CreateGameRequestBody;
import requests.ListGamesRequest;
import results.CreateGameResult;
import results.ExceptionResult;
import results.ListGamesResult;
import service.GameService;

public class GameHandler {

    GameService gameService;
    private final Gson gson = new Gson();

    public GameHandler(GameDAO gameDAO, AuthDAO authDAO) {
         gameService = new GameService(gameDAO, authDAO);
    }

    public void listGames(Context ctx) {
        String authToken = ctx.header("authorization");
        ListGamesRequest request = new ListGamesRequest(authToken);

        if (request.authToken() == null) {
            ctx.status(400);
            ctx.result(gson.toJson(new ExceptionResult(new BadRequestResponse("Error: bad request").getMessage())));
            return;
        }

        try {
            ListGamesResult result = gameService.listGames(request);
            String json = gson.toJson(result);
            ctx.status(200);
            ctx.result(json);
        } catch (UnauthorizedResponse e) {
            ctx.status(401);
            String json = gson.toJson(new ExceptionResult(e.getMessage()));
            ctx.result(json);
        }


    }

    public void createGame(Context ctx) {
        String authToken = ctx.header("authorization");
        CreateGameRequestBody requestBody = gson.fromJson(ctx.body(), CreateGameRequestBody.class);
        CreateGameRequest request = new CreateGameRequest(authToken, requestBody.gameName());

        if (request.authToken() == null || request.gameName() == null) {
            ctx.status(400);
            ctx.result(gson.toJson(new ExceptionResult(new BadRequestResponse("Error: bad request").getMessage())));
            return;
        }

        try {
            CreateGameResult result = gameService.createGame(request);
            String json = gson.toJson(result);
            ctx.status(200);
            ctx.result(json);
        } catch (UnauthorizedResponse e) {
            ctx.status(401);
            String json = gson.toJson(new ExceptionResult(e.getMessage()));
            ctx.result(json);
        }
    }

    public void joinGame(Context ctx) {
        
    }

    public void clear() {
        gameService.clear();
    }
}
