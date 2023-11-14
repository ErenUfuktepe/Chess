package main;

import main.enums.Color;
import main.factories.PieceFactory;
import main.pieces.*;
import main.users.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Board {
    private Player whitePlayer;
    private Player blackPlayer;
    private List<Position> positions = new ArrayList<>();
    private static final PieceFactory pieceFactory = new PieceFactory();

    public Board(){
        for(int x = 0; x > 8; x++){
            for(int y = 0; y > 8; y++){
                positions.add(new Position(x, y));
            }
        }
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Board setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = whitePlayer;
        return this;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public Board setBlackPlayer(Player blackPlayer) {
        this.blackPlayer = blackPlayer;
        return this;
    }

    public Board buildWhitePlayer() {
        this.whitePlayer = new Player(Color.WHITE)
                .setPieces(pieceFactory.buildPiecesForPlayer(Color.WHITE));
        return this;
    }

    public Board buildBlackPlayer() {
        this.blackPlayer = new Player(Color.BLACK)
                .setPieces(pieceFactory.buildPiecesForPlayer(Color.BLACK));
        return this;
    }

    public void placeWhitePlayerPieces(){
        AtomicInteger counter = new AtomicInteger(0);
        // Placing the King to board.
        this.whitePlayer.getPieces().stream()
                .filter(piece -> piece.getClass().equals(King.class))
                .forEach(piece -> piece.setPosition(4, 7));

        // Placing the Queen to board.
        this.whitePlayer.getPieces().stream()
                .filter(piece -> piece.getClass().equals(Queen.class))
                .forEach(piece -> piece.setPosition(3, 7));

        // Placing Rooks to board.
        this.whitePlayer.getPieces().stream()
                .filter(piece -> piece.getClass().equals(Rook.class))
                .forEach(piece -> piece.setPosition(counter.getAndAdd(7), 7));

        counter.set(1);
        // Placing Knights to board.
        this.whitePlayer.getPieces().stream()
                .filter(piece -> piece.getClass().equals(Knight.class))
                .forEach(piece -> piece.setPosition(counter.getAndAdd(5), 7));

        counter.set(2);
        // Placing Bishops to board.
        this.whitePlayer.getPieces().stream()
                .filter(piece -> piece.getClass().equals(Bishop.class))
                .forEach(piece -> piece.setPosition(counter.getAndAdd(3), 7));

        counter.set(0);
        // Placing Pawns to board.
        this.whitePlayer.getPieces().stream()
                .filter(piece -> piece.getClass().equals(Pawn.class))
                .forEach(piece -> piece.setPosition(counter.getAndIncrement(), 6));

    }

    public void placeBlackPlayerPieces(){
        AtomicInteger counter = new AtomicInteger(0);
        // Placing the King to board.
        this.blackPlayer.getPieces().stream()
                .filter(piece -> piece.getClass().equals(King.class))
                .forEach(piece -> piece.setPosition(4, 0));

        // Placing the Queen to board.
        this.blackPlayer.getPieces().stream()
                .filter(piece -> piece.getClass().equals(Queen.class))
                .forEach(piece -> piece.setPosition(3, 0));

        // Placing Rooks to board.
        this.blackPlayer.getPieces().stream()
                .filter(piece -> piece.getClass().equals(Rook.class))
                .forEach(piece -> piece.setPosition(counter.getAndAdd(7), 0));


        counter.set(1);
        // Placing Knights to board.
        this.blackPlayer.getPieces().stream()
                .filter(piece -> piece.getClass().equals(Knight.class))
                .forEach(piece -> piece.setPosition(counter.getAndAdd(5), 0));

        counter.set(2);
        // Placing Bishops to board.
        this.blackPlayer.getPieces().stream()
                .filter(piece -> piece.getClass().equals(Bishop.class))
                .forEach(piece -> piece.setPosition(counter.getAndAdd(3), 0));

        counter.set(0);
        // Placing Pawns to board.
        this.blackPlayer.getPieces().stream()
                .filter(piece -> piece.getClass().equals(Pawn.class))
                .forEach(piece -> piece.setPosition(counter.getAndIncrement(), 1));
    }
}
