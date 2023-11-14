package main.enums;

public enum PieceType {
    PAWN("PAWN"),
    BISHOP("BISHOP"),
    KNIGHT("KNIGHT"),
    ROOK("ROOK"),
    QUEEN("QUEEN"),
    KING("KING");

    private String piece;

    private PieceType(String piece){
        this.piece = piece;
    }

    public String getPiece(){
        return this.piece;
    }
}
