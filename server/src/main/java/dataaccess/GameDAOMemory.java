package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class GameDAOMemory implements GameDAO{

    private final HashMap<Integer, GameData> gameMap = new HashMap<>();

    @Override
    public void createGame() {

    }

    @Override
    public ChessGame getGame() {
        return null;
    }

    @Override
    public Collection<ChessGame> listGames() {
        return null;
    }

    @Override
    public void updateGame() {

    }

    @Override
    public void clearGames() {
        gameMap.clear();
    }
}
