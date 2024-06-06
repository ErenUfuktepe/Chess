package main.player;

import main.factories.PieceFactory;
import main.pieces.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Piece> pieces = new ArrayList<>();
    private boolean isTurn;
    private static final PieceFactory pieceFactory = new PieceFactory();

    public Player(Color color) {
        setPieces(pieceFactory.createPiecesForPlayer(color));
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
}
