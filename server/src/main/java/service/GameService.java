package service;

import model.AuthData;
import model.GameData;
import dataaccess.GameDAO;
import dataaccess.DataAccessException;

public class GameService {
    GameDAO gameDAO;

    public GameService (GameDAO givenGameDAO) {
        gameDAO = givenGameDAO;
    }

    public void clear() {
        gameDAO.clearGames();
    }
}
