package server;

import com.google.gson.Gson;
import dataaccess.AuthDAOMemory;
import dataaccess.DataAccessException;
import dataaccess.GameDAOMemory;
import dataaccess.UserDAOMemory;
import io.javalin.*;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import handler.*;
import service.exception.*;

public class Server {

    private final Javalin javalin;

    private void alreadyTakenHandler(AlreadyTakenException ex, Context ctx) {
        ctx.status(403);
        ctx.result(ex.getMessage());
    }

    private void dataAccessExHandler(DataAccessException ex, Context ctx) {
        ctx.status(400);
        ctx.result(ex.getMessage());
    }

    private void badRequestHandler(BadRequestResponse ex, Context ctx) {
        ctx.status(400);
        ctx.result(ex.getMessage());
    }

    private void ExceptionHandler (Exception ex, Context ctx) {
        ctx.status(500);
        ctx.result(ex.getMessage());
    }

    public Server() {
        UserDAOMemory userDAOMemory = new UserDAOMemory();
        AuthDAOMemory authDAOMemory = new AuthDAOMemory();
        GameDAOMemory gameDAOMemory = new GameDAOMemory();
        UserHandler userHandler = new UserHandler(userDAOMemory, authDAOMemory);
        ClearHandler clearHandler = new ClearHandler(userDAOMemory, authDAOMemory, gameDAOMemory);

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .post("/user", userHandler::register)
                .post("session", userHandler::login)
                .delete("/db", clearHandler::delete)

                .exception(BadRequestResponse.class, this::badRequestHandler)
                .exception(DataAccessException.class, this::dataAccessExHandler)
                .exception(AlreadyTakenException.class, this::alreadyTakenHandler);

                //.exception(Exception.class, this::ExceptionHandler);


        // Register your endpoints and exception handlers here.

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
