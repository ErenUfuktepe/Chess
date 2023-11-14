package main;

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
}
