package service;

import dataaccess.AuthDAOMemory;
import dataaccess.DataAccessException;
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
        //makes sure you can't create duplicate authdata for the same username
        String username = "badstudent67";
        try {
            AuthData existing = authService.createAuth(username);
        }
        catch (DataAccessException ex) {

        }
        Assertions.assertThrows(DataAccessException.class, () -> {authService.createAuth(username);});
    }


}
