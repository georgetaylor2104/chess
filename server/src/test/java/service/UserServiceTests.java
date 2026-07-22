package service;

import dataaccess.AuthDAOMemory;
import dataaccess.DataAccessException;
import dataaccess.UserDAOMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
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
}
