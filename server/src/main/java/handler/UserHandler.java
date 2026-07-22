package handler;

import com.google.gson.Gson;
import dataaccess.AuthDAOMemory;
import dataaccess.DataAccessException;
import dataaccess.UserDAOMemory;
import io.javalin.http.Handler;
import io.javalin.http.Context;
import requests.RegisterRequest;
import results.RegisterResult;
import service.UserService;

public class UserHandler {



    private final Gson gson = new Gson();
    private final UserService userService = new UserService(new UserDAOMemory(), new AuthDAOMemory());


    public void register(Context ctx) throws DataAccessException {
        RegisterRequest request = gson.fromJson(ctx.body(), RegisterRequest.class);
        RegisterResult result = userService.register(request);
        String json = gson.toJson(result);
        ctx.status(200);
        ctx.result(json);


    }

    public void login (Context ctx) {

    }

    public void logout (Context ctx) {

    }
}
