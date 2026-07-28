package server;

import dataaccess.AuthDAOMemory;
import dataaccess.GameDAOMemory;
import dataaccess.UserDAOMemory;
import io.javalin.*;
import io.javalin.http.Context;
import handler.*;
import service.AuthService;
import service.exception.*;

public class Server {

    private final Javalin javalin;

    private void ExceptionHandler (Exception ex, Context ctx) {
        ctx.status(500);
        ctx.result(ex.getMessage());
    }

    public Server() {
        UserDAOMemory userDAO = new UserDAOMemory();
        AuthDAOMemory authDAO = new AuthDAOMemory();
        GameDAOMemory gameDAO = new GameDAOMemory();
        AuthService authService = new AuthService(authDAO);
        UserHandler userHandler = new UserHandler(userDAO, authService);
        GameHandler gameHandler = new GameHandler(gameDAO, authService);
        ClearHandler clearHandler = new ClearHandler(userHandler, gameHandler);

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .post("/user", userHandler::register)
                .post("/session", userHandler::login)
                .post("/game", gameHandler::createGame)
                .get("/game", gameHandler::listGames)
                .put("/game", gameHandler::joinGame)
                .delete("/session", userHandler::logout)
                .delete("/db", clearHandler::delete)

                .exception(Exception.class, this::ExceptionHandler);

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
