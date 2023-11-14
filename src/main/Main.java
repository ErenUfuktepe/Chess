package main;

public class Main {

    public static void main(String[] args) {
        Board board = new Board()
                .buildWhitePlayer()
                .buildBlackPlayer();

        board.placeWhitePlayerPieces();
        board.placeBlackPlayerPieces();

    }

}