package dataaccess;

import chess.ChessGame;
import model.GameData;
import service.exception.AlreadyTakenException;

import java.util.Collection;

public interface GameDAO {

    void createGame(String gameName, Integer gameID);

    Collection<GameData> listGames();

    GameData getGame(Integer gameID);

    void updateGame(ChessGame.TeamColor playerColor, Integer gameID, String username) throws AlreadyTakenException, DataAccessException;

    void clearGames();

    boolean contains(Integer gameID);
}
