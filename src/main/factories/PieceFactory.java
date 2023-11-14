package main.factories;

import main.Piece;
import main.enums.Color;
import main.enums.PieceType;
import main.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class PieceFactory {
    private static final int NUMBER_OF_KING = 1;
    private static final int NUMBER_OF_QUEEN = 1;
    private static final int NUMBER_OF_BISHOP = 2;
    private static final int NUMBER_OF_KNIGHT = 2;
    private static final int NUMBER_OF_ROOK = 2;
    private static final int NUMBER_OF_PAWN = 8;

    public Piece createPiece(PieceType piece, Color color) {
        switch (piece) {
            case PAWN:
                return new Pawn(color);
            case BISHOP:
                return new Bishop(color);
            case ROOK:
                return new Rook(color);
            case KNIGHT:
                return new Knight(color);
            case QUEEN:
                return new Queen(color);
            case KING:
                return new King(color);
            default:
                throw new UnsupportedOperationException("Unsupported piece " + piece + ".");
        }
    }

    public List<Piece> buildPiecesForPlayer(Color color) {
        List<Piece> pieces = new ArrayList<>();

        // Create Pawns.
        for (int index = 0; index < NUMBER_OF_PAWN; index++) {
            pieces.add(new Pawn(color));
        }

        // Create Rooks.
        for (int index = 0; index < NUMBER_OF_ROOK; index++) {
            pieces.add(new Rook(color));
        }

        // Create Knights.
        for (int index = 0; index < NUMBER_OF_KNIGHT; index++) {
            pieces.add(new Knight(color));
        }

        // Create Bishops.
        for (int index = 0; index < NUMBER_OF_BISHOP; index++) {
            pieces.add(new Bishop(color));
        }

        // Create the Queen.
        for (int index = 0; index < NUMBER_OF_QUEEN; index++) {
            pieces.add(new Queen(color));
        }

        // Create the King.
        for (int index = 0; index < NUMBER_OF_KING; index++) {
            pieces.add(new King(color));
        }

        return pieces;
    }



}
