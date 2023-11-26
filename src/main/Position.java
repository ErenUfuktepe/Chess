package main;

import main.pieces.Piece;

public class Position {
    private int x;
    private int y;
    private Piece piece;

    public Position() {

    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public Position setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Position setY(int y) {
        this.y = y;
        return this;
    }

    public Piece getPiece() {
        return piece;
    }

    public Position setPiece(Piece piece) {
        this.piece = piece;
        return this;
    }

    public String getKey(){
        return String.valueOf(this.x) + String.valueOf(this.y);
    }

    public boolean isMovable() {
        return !(this.x > 7 || this.x < 0 || this.y > 7 || this.y < 0);
    }
}
