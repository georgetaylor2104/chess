package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    TeamColor teamTurn = TeamColor.WHITE;
    ChessBoard gameBoard = new ChessBoard();

    public ChessGame() {
        gameBoard.resetBoard();
    }

    public ChessGame(ChessGame other) {
        this.teamTurn = other.teamTurn;
        this.gameBoard = new ChessBoard(other.gameBoard);
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Sets which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets all valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        // get the piece type from starting position
        // get the pieces moves
        // for each move make a deep copy of the board
        // then call isInCheck() for each move
        // if true then remove that move from the set
        // else the move stays
        // return the set of valid moves

        ChessPiece piece = gameBoard.getPiece(startPosition);
        TeamColor color = piece.getTeamColor();


        throw new RuntimeException("Not implemented");
    }

    private void movePiece(ChessMove move) {
        ChessPiece piece = gameBoard.getPiece(move.getStartPosition());
        gameBoard.addPiece(move.getEndPosition(), piece);
        gameBoard.addPiece(move.getStartPosition(), null);

    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition piecePosition = move.getStartPosition();
        TeamColor color = gameBoard.getPiece(piecePosition).getTeamColor();
        Collection<ChessMove> validMovesList = validMoves(piecePosition);

        if (validMovesList.contains(move) && getTeamTurn() == color) {
            movePiece(move);
        }
        else {
            throw new InvalidMoveException();
        }
    }

    private ChessPosition kingPosition(TeamColor color) {
        ChessPosition pos;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                pos = new ChessPosition(r+1, c+1);
                ChessPiece piece = gameBoard.getPiece(pos);
                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == color) {
                    return pos;
                }
            }
        }
        throw new RuntimeException("King piece not found");
    }

    private Collection<ChessPosition> getEnemyTeamPositions (TeamColor teamColor) {
        Set<ChessPosition> enemyPositions = new HashSet<>();

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                ChessPosition pos = new ChessPosition(r+1, c+1);
                ChessPiece piece = gameBoard.getPiece(pos);
                if (piece != null && piece.getTeamColor() != teamColor) {
                    enemyPositions.add(pos);
                }
            }
        }

        return enemyPositions;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPos = kingPosition(teamColor);
        Collection<ChessPosition> enemyPositions = getEnemyTeamPositions(teamColor);

        for (ChessPosition enemyPos : enemyPositions) {
            ChessPiece piece = gameBoard.getPiece(enemyPos);
            Collection<ChessMove> moves = piece.pieceMoves(gameBoard, enemyPos);
            for (ChessMove move : moves) {
                if (move.getEndPosition().equals(kingPos)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        // if there are no valid moves and current position is also in check
        ChessPosition kingPos = kingPosition(teamColor);
        return (validMoves(kingPos).isEmpty() && isInCheck(teamColor));

    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        // if there are no valid moves and current position is not in check
        ChessPosition kingPos = kingPosition(teamColor);
        return (validMoves(kingPos).isEmpty() && !isInCheck(teamColor));
    }

    /**
     * Sets this game's chessboard to a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        gameBoard = board;

    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return gameBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return getTeamTurn() == chessGame.getTeamTurn() && Objects.equals(gameBoard, chessGame.gameBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTeamTurn(), gameBoard);
    }
}
