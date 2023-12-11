package main;

import gui.ChessBoard;
import main.player.BlackPlayer;
import main.player.WhitePlayer;

public class Main {

    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard(new WhitePlayer(), new BlackPlayer());
    }

}
