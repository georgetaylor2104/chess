package handler;

import dataaccess.*;
import io.javalin.http.Context;

public class ClearHandler {
    UserHandler userHandler;
    GameHandler gameHandler;

    public ClearHandler(UserHandler givenUserHandler, GameHandler givenGameHandler) {
        userHandler = givenUserHandler;
        gameHandler = givenGameHandler;
    }

    public void delete(Context ctx) {
        userHandler.clear();
        gameHandler.clear();

        ctx.status(200);
        ctx.result("");
    }
}
