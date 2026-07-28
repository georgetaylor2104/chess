package handler;

import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import requests.LoginRequest;
import requests.LogoutRequest;
import requests.RegisterRequest;
import results.ExceptionResult;
import results.LoginResult;
import results.RegisterResult;
import service.AuthService;
import service.UserService;
import service.exception.AlreadyTakenException;


public class UserHandler {



    private final Gson gson = new Gson();
    private final UserService userService;

    public UserHandler (UserDAO userDAO, AuthService authService) {
        userService = new UserService(userDAO, authService);
    }



    public void register(Context ctx)  {
        RegisterRequest request = gson.fromJson(ctx.body(), RegisterRequest.class);

        if (request.username() == null || request.password() == null || request.email() == null) {
            ctx.status(400);
            ctx.result(gson.toJson(new ExceptionResult(new BadRequestResponse("Error: bad request").getMessage())));
            return;
        }

        try {
            RegisterResult result = userService.register(request);
            String json = gson.toJson(result);
            ctx.status(200);
            ctx.result(json);
        } catch (AlreadyTakenException e) {
            ctx.status(403);
            String json = gson.toJson(new ExceptionResult(e.getMessage()));
            ctx.result(json);
        } catch (DataAccessException e) {
            ctx.status(400);
            String json = gson.toJson(new ExceptionResult(e.getMessage()));
            ctx.result(json);
        }




    }

    public void login (Context ctx) {
        LoginRequest request = gson.fromJson(ctx.body(), LoginRequest.class);

        if (request.username() == null || request.password() == null) {
            ctx.status(400);
            ctx.result(gson.toJson(new ExceptionResult(new BadRequestResponse("Error: bad request").getMessage())));
            return;
        }

        try {
            LoginResult result = userService.login(request);
            String json = gson.toJson(result);
            ctx.status(200);
            ctx.result(json);

        } catch (DataAccessException e) {
            ctx.status(400);
            String json = gson.toJson(new ExceptionResult(e.getMessage()));
            ctx.result(json);

        }  catch (UnauthorizedResponse e) {
            ctx.status(401);
            String json = gson.toJson(new ExceptionResult(e.getMessage()));
            ctx.result(json);
        }

    }

    public void clear() {
        userService.clear();
    }

    public void logout (Context ctx) {
        String authToken = ctx.header("authorization");
        LogoutRequest request = new LogoutRequest(authToken);

        if (request.authToken() == null) {
            ctx.status(400);
            ctx.result(gson.toJson(new ExceptionResult(new BadRequestResponse("Error: bad request").getMessage())));
            return;
        }

        try {
            userService.logout(request);
            ctx.status(200);
            ctx.result("");
        } catch (DataAccessException e) {
            ctx.status(400);
            String json = gson.toJson(new ExceptionResult(e.getMessage()));
            ctx.result(json);
        } catch (UnauthorizedResponse e) {
            ctx.status(401);
            String json = gson.toJson(new ExceptionResult(e.getMessage()));
            ctx.result(json);
        }
    }
}
