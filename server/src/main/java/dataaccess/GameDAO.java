package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public interface GameDAO {

    void createGame(String gameName, Integer gameID);

    ChessGame getGame();

    Collection<GameData> listGames();

    void updateGame();

    void clearGames();
    boolean contains(Integer gameID);
}
