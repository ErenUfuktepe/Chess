package main.factories;

import main.pieces.Piece;
import main.enums.Color;
import main.enums.PieceType;
import main.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PieceFactory {
    private static final int NUMBER_OF_BISHOP = 2;
    private static final int NUMBER_OF_KNIGHT = 2;
    private static final int NUMBER_OF_ROOK = 2;
    private static final int NUMBER_OF_PAWN = 8;

    private List<Piece> createPawns(Color color) {
        List<Piece> pawnList = new ArrayList<>();
        AtomicInteger counter = new AtomicInteger(0);

        for (int index = 0; index < NUMBER_OF_PAWN; index++) {
            pawnList.add(new Pawn(color));
        }

        int yAxis = color.equals(Color.WHITE) ? 1 : 6;
        pawnList.stream().forEach(piece -> piece.setPosition(counter.getAndIncrement(), yAxis));

        return pawnList;
    }

    private Piece createKing(Color color) {
        Piece king = new King(color);
        int yAxis = color.equals(Color.WHITE) ? 0 : 7;
        king.setPosition(4, yAxis);

        return king;
    }

    private Piece createQueen(Color color) {
        Piece queen = new Queen(color);
        int yAxis = color.equals(Color.WHITE) ? 0 : 7;
        queen.setPosition(3, yAxis);

        return queen;
    }

    private List<Piece> createRooks(Color color) {
        List<Piece> rookList = new ArrayList<>();
        AtomicInteger counter = new AtomicInteger(0);

        for (int index = 0; index < NUMBER_OF_ROOK; index++) {
            rookList.add(new Rook(color));
        }

        int yAxis = color.equals(Color.WHITE) ? 0 : 7;
        rookList.stream().forEach(piece -> piece.setPosition(counter.getAndAdd(7), yAxis));

        return rookList;
    }

    private List<Piece> createKnights(Color color) {
        List<Piece> knightList = new ArrayList<>();
        AtomicInteger counter = new AtomicInteger(1);

        for (int index = 0; index < NUMBER_OF_KNIGHT; index++) {
            knightList.add(new Knight(color));
        }

        int yAxis = color.equals(Color.WHITE) ? 0 : 7;
        knightList.stream().forEach(piece -> piece.setPosition(counter.getAndAdd(5), yAxis));

        return knightList;
    }

    private List<Piece> createBishops(Color color) {
        List<Piece> bishopList = new ArrayList<>();
        AtomicInteger counter = new AtomicInteger(2);

        for (int index = 0; index < NUMBER_OF_BISHOP; index++) {
            bishopList.add(new Bishop(color));
        }

        int yAxis = color.equals(Color.WHITE) ? 0 : 7;
        bishopList.stream().forEach(piece -> piece.setPosition(counter.getAndAdd(3), yAxis));

        return bishopList;
    }

    public List<Piece> createPiecesForPlayer(Color color) {
        List<Piece> pieces = new ArrayList<>();
        pieces.addAll(this.createPawns(color));
        pieces.addAll(this.createBishops(color));
        pieces.addAll(this.createKnights(color));
        pieces.addAll(this.createRooks(color));
        pieces.add(this.createKing(color));
        pieces.add(this.createQueen(color));
        return pieces;
    }
}
