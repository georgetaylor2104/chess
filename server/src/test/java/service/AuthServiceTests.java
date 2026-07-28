package service;

import dataaccess.AuthDAOMemory;
import dataaccess.DataAccessException;
import io.javalin.http.UnauthorizedResponse;
import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthServiceTests {
    AuthDAOMemory aDAOMem;
    AuthService authService;


    @BeforeEach
    public void setUp() {
        aDAOMem = new AuthDAOMemory();
        authService = new AuthService(aDAOMem);

    }

    @Test
    public void createAuthPositiveTest() throws DataAccessException {
        
        String username = "badstudent67";
        AuthData actual = authService.createAuth(username);
        Assertions.assertEquals(username, actual.username());
        Assertions.assertNotNull(actual.authToken());
        Assertions.assertFalse(actual.authToken().isBlank());
    }

    @Test
    public void createAuthNegativeTest() {
        String username = null;
        Assertions.assertThrows(DataAccessException.class, () -> {authService.createAuth(username);});
    }

    @Test
    public void logoutAuthPositiveTest() throws DataAccessException, UnauthorizedResponse {
        AuthData authData = authService.createAuth("george");
        String authToken = authData.authToken();
        authService.logoutAuth(authToken);
        Assertions.assertFalse(aDAOMem.verifyAuth(authToken));
    }

    @Test
    public void logoutAuthNegativeTest() throws DataAccessException, UnauthorizedResponse {
        AuthData authData = authService.createAuth("george");
        String authToken = authData.authToken();
        Assertions.assertThrows(UnauthorizedResponse.class, () -> {authService.logoutAuth("123456");});
    }

}
