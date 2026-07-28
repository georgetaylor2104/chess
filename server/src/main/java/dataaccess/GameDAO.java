package dataaccess;

import chess.ChessGame;
import model.GameData;
import service.exception.AlreadyTakenException;

import java.util.Collection;

public interface GameDAO {

    void createGame(String gameName, Integer gameID);

    ChessGame getGame();

    Collection<GameData> listGames();

    void updateGame(ChessGame.TeamColor playerColor, Integer gameID, String username) throws AlreadyTakenException, DataAccessException;

    void clearGames();
    boolean contains(Integer gameID);
}
