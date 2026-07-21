package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.*;
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

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .post("/user", ctx -> new UserHandler().register(ctx))

                .exception(AlreadyTakenException.class, this::alreadyTakenHandler);


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
