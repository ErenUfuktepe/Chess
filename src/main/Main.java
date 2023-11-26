package main;

import gui.ChessBoard;
import main.enums.Color;
import main.pieces.Pawn;
import main.player.Player;
import main.player.WhitePlayer;

public class Main {

    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.build();

/*
        Pawn piece = new Pawn(Color.WHITE);
        //King piece = new King(Color.WHITE);

        Position position = new Position(3,3);
        piece.getMovable().getPossiblePositions(position)
                .stream()
                .forEach(a -> System.out.println(a.getKey()));

                */
    }

}
