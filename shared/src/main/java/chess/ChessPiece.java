package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }


    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {

        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;

    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        ChessGame.TeamColor color = piece.getTeamColor();
        PieceMovesCalculator calculator;
        if (piece.getPieceType() == PieceType.BISHOP) {
            calculator = new BishopMoves();
            return calculator.pieceMoves(board, myPosition, color);
        }
        else if (piece.getPieceType() == PieceType.KING) {
            calculator = new KingMoves();
            return  calculator.pieceMoves(board, myPosition,color);
        }
        else if (piece.getPieceType() == PieceType.KNIGHT) {
            calculator = new KnightMoves();
            return  calculator.pieceMoves(board, myPosition, color);
        }
        else if (piece.getPieceType() == PieceType.PAWN) {
            calculator = new PawnMoves();
            return  calculator.pieceMoves(board, myPosition, color);
        }
        else if (piece.getPieceType() == PieceType.QUEEN) {
            calculator = new QueenMoves();
            return  calculator.pieceMoves(board, myPosition, color);
        }
        else if (piece.getPieceType() == PieceType.ROOK) {
            calculator = new RookMoves();
            return  calculator.pieceMoves(board, myPosition, color);
        }
        else {
            return List.of();
        }
    }

    @Override
    public String toString() {
        if (type == null) { return "null";}
        else {
            return "ChessPiece{" +
                    "pieceColor=" + pieceColor +
                    ", type=" + type +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}

record Pair(int firstItem, int secondItem){}

interface PieceMovesCalculator {
    Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color);

    default boolean spaceClearOrTakeable (ChessBoard board, ChessPosition movePosition, ChessGame.TeamColor color) {
        if (board.isInBounds(movePosition)) {
            if (board.getPiece(movePosition) == null) {
                return true;
            } else return board.getPiece(movePosition).getTeamColor() != color;
        }
        else {
            return false;
        }
    }
}

class BishopMoves implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        return List.of();
    }
}

class KingMoves implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        List<ChessMove> moveList = new ArrayList<>();
        List<Pair> movePairs = List.of(new Pair(0, 1), new Pair(1, 0), new Pair(-1, 0), new Pair(0, -1), new Pair(1,1), new Pair(1,-1), new Pair(-1,1), new Pair(-1,-1));

        for (Pair pair : movePairs) {
            ChessPosition positionToTry = new ChessPosition(myPosition.getRow()+pair.firstItem(), myPosition.getColumn()+ pair.secondItem());
            if (board.isInBounds(positionToTry) && spaceClearOrTakeable(board, positionToTry, color)) {
                moveList.add(new ChessMove(myPosition, positionToTry, null));
            }
        }

        return moveList;
    }
}

class KnightMoves implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        List<ChessMove> moveList = new ArrayList<>();
        List<Pair> movePairs = List.of(new Pair(1,2), new Pair(-1,2), new Pair(1,-2), new Pair(-1,-2), new Pair(2,1), new Pair(2,-1), new Pair(-2,1), new Pair(-2,-1));

        for (Pair pair : movePairs) {
            ChessPosition positionToTry = new ChessPosition(myPosition.getRow()+pair.firstItem(), myPosition.getColumn()+pair.secondItem());
            if (board.isInBounds(positionToTry) && spaceClearOrTakeable(board, positionToTry, color)) {
                moveList.add(new ChessMove(myPosition, positionToTry, null));
            }
        }

        return moveList;
    }
}

class PawnMoves implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        return List.of();
    }
}

class QueenMoves implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        return List.of();
    }
}

class RookMoves implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        return List.of();
    }
}
