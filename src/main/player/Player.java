package main.player;

import main.factories.PieceFactory;
import main.pieces.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private List<Piece> pieces = new ArrayList<>();
    private boolean isTurn;
    private Color color;
    private static final PieceFactory pieceFactory = new PieceFactory();

    public Player(Color color) {
        this.color = color;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setupPlayer() {
        this.isTurn = color.equals(Color.WHITE) ? true : false;
        setPieces(pieceFactory.createPiecesForPlayer(this.color));
    }
}
