package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class GameDAOMemory implements GameDAO{

    private final HashMap<Integer, GameData> gameMap = new HashMap<>();

    @Override
    public void createGame(String gameName, Integer gameID) {
        GameData gameData = new GameData(gameID, null, null, gameName, new ChessGame());
        gameMap.put(gameID, gameData);
    }

    @Override
    public ChessGame getGame() {
        return null;
    }

    @Override
    public Collection<GameData> listGames() {
//        List<GameData> gameList = new ArrayList<>();
//        gameList.addAll(gameMap.values());
        return gameMap.values();
    }

    @Override
    public void updateGame() {
    }

    @Override
    public boolean contains(Integer gameID) {
        return gameMap.containsKey(gameID);
    }

    @Override
    public void clearGames() {
        gameMap.clear();
    }
}
