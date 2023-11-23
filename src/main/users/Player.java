package main.users;

import main.enums.Color;
import main.factories.PieceFactory;
import main.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private boolean isPlayerTurn;
    private List<Piece> pieces = new ArrayList<>();
    private Color color;
    private static final PieceFactory pieceFactory = new PieceFactory();

    public Player(Color color) {
        pieces.addAll(pieceFactory.buildPiecesForPlayer(color));
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public Player setPieces(List<Piece> pieces) {
        this.pieces = pieces;
        return this;
    }

    public Color getColor() {
        return color;
    }

    public Player setColor(Color color) {
        this.color = color;
        return this;
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public Player setPlayerTurn(boolean playerTurn) {
        isPlayerTurn = playerTurn;
        return this;
    }
}
