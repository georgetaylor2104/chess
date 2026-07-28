package dataaccess;

import chess.ChessGame;
import model.GameData;
import service.exception.AlreadyTakenException;
import java.util.Collection;
import java.util.HashMap;

public class GameDAOMemory implements GameDAO {

    private final HashMap<Integer, GameData> gameMap = new HashMap<>();

    @Override
    public void createGame(String gameName, Integer gameID) {
        GameData gameData = new GameData(gameID, null, null, gameName, new ChessGame());
        gameMap.put(gameID, gameData);
    }

    @Override
    public Collection<GameData> listGames() {
        return gameMap.values();
    }

    @Override
    public void updateGame(ChessGame.TeamColor playerColor, Integer gameID, String username) throws AlreadyTakenException, DataAccessException {

        switch (playerColor) {
            case ChessGame.TeamColor.WHITE:
                if (gameMap.get(gameID).whiteUsername() != null) {
                    throw new AlreadyTakenException("Error: playerColor already taken");
                }
                else {
                    GameData gameData = gameMap.get(gameID);
                    GameData updated = new GameData(gameID, username, gameData.blackUsername(), gameData.gameName(), gameData.game());
                    gameMap.put(gameID, updated);

                }
                break;

            case ChessGame.TeamColor.BLACK:
                if (gameMap.get(gameID).blackUsername() != null) {
                    throw new AlreadyTakenException("Error: playerColor already taken");
                }
                else {
                    GameData gameData = gameMap.get(gameID);
                    GameData updated = new GameData(gameID, gameData.whiteUsername(), username, gameData.gameName(), gameData.game());
                    gameMap.put(gameID, updated);
                }
                break;
        }
    }

    @Override
    public boolean contains(Integer gameID) {
        return gameMap.containsKey(gameID);
    }

    @Override
    public GameData getGame(Integer gameID) {
        return gameMap.get(gameID);
    }

    @Override
    public void clearGames() {
        gameMap.clear();
    }
}
