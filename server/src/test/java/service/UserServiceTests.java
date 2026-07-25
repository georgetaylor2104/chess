package service;

import dataaccess.AuthDAOMemory;
import dataaccess.DataAccessException;
import dataaccess.UserDAOMemory;
import io.javalin.http.UnauthorizedResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.LoginRequest;
import requests.RegisterRequest;
import results.LoginResult;
import results.RegisterResult;
import service.exception.AlreadyTakenException;

public class UserServiceTests {

    UserDAOMemory userDAOMemory;
    AuthDAOMemory authDAOMemory;
    UserService userService;

    @BeforeEach
    public void setUp() {
        userDAOMemory = new UserDAOMemory();
        authDAOMemory = new AuthDAOMemory();
        userService = new UserService(userDAOMemory, authDAOMemory);
    }

    @Test
    public void registerPositiveTest() throws AlreadyTakenException, DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest("coolUsername", "strongPassword", "cleverEmail@email.com");
        RegisterResult actual = userService.register(registerRequest);
        Assertions.assertEquals(registerRequest.username(), actual.username());
        Assertions.assertNotNull(actual.authToken());
        Assertions.assertFalse(actual.authToken().isBlank());
    }

    @Test
    public void registerNegativeTest() throws DataAccessException{
        RegisterRequest registerRequest = new RegisterRequest("coolUsername", "strongPassword", "cleverEmail@email.com");
        RegisterResult first = userService.register(registerRequest);
        Assertions.assertThrows(AlreadyTakenException.class, () -> {userService.register(registerRequest);});
    }

    @Test
    public void loginPositiveTest() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest("rootbear67", "discreteinnuendo", "dumbfrickin@email.com");
        LoginRequest loginRequest = new LoginRequest("rootbear67", "discreteinnuendo");
        RegisterResult registerResult = userService.register(registerRequest);
        LoginResult loginResult = userService.login(loginRequest);

        Assertions.assertEquals(registerResult.username(), loginResult.username());
        Assertions.assertNotNull(loginResult.authToken());
        Assertions.assertFalse(loginResult.authToken().isBlank());
    }

    @Test
    public void loginNegativeTest() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest("rootbear67", "discreteinnuendo", "dumbfrickin@email.com");
        LoginRequest loginRequest = new LoginRequest("rootbear67", "discretewrongpassword");
        RegisterResult registerSetUp = userService.register(registerRequest);
        Assertions.assertThrows(UnauthorizedResponse.class, () -> {userService.login(loginRequest);});


    }
}
