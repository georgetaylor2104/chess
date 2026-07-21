package handler;

import com.google.gson.Gson;
import io.javalin.http.Handler;
import io.javalin.http.Context;
import requests.RegisterRequest;
import results.RegisterResult;
import service.UserService;

public class UserHandler {

    private final Gson gson = new Gson();
    private final UserService userService = new UserService();


    public void register(Context ctx) {
        RegisterRequest request = gson.fromJson(ctx.body(), RegisterRequest.class);
        RegisterResult result = userService.register(request);

    }

    public void login (Context ctx) {

    }

    public void logout (Context ctx) {

    }
}
