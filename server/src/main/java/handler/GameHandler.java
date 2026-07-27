package handler;

import com.google.gson.Gson;
import dataaccess.GameDAO;
import io.javalin.http.Handler;
import io.javalin.http.Context;
import service.GameService;

public class GameHandler {

    GameService gameService;

    public GameHandler(GameDAO gameDAO) {
         gameService = new GameService(gameDAO);
    }

    public void listGames(Context ctx) {

    }

    public void createGames(Context ctx) {

    }

    public void joinGame(Context ctx) {
        
    }

    public void clear() {
        gameService.clear();
    }
}
