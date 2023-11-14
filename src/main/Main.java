package main;

import main.pieces.Piece;

public class Main {

    public static void main(String[] args) {
        Board board = new Board()
                .buildWhitePlayer()
                .buildBlackPlayer();

        board.placeWhitePlayerPieces();
        board.placeBlackPlayerPieces();

        for(Piece piece : board.getWhitePlayer().getPieces()) {
            piece.toString();
        }
    }

}
