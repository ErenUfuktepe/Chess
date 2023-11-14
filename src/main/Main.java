package main;

public class Main {

    public static void main(String[] args) {
        Board board = new Board()
                .buildWhitePlayer()
                .buildBlackPlayer();

        board.placeWhitePlayerPieces();
        board.placeBlackPlayerPieces();

        board.getWhitePlayer().getPieces().stream()
                .forEach(piece -> {
                    System.out.println(piece.getClass());
                    System.out.println(piece.getPosition().getX());
                    System.out.println(piece.getPosition().getY());
                });


    }

}
