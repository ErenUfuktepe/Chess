package main;

import gui.ChessBoard;
import main.enums.Color;
import main.pieces.Pawn;
import main.player.BlackPlayer;
import main.player.Player;
import main.player.WhitePlayer;

public class Main {

    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard(new WhitePlayer(), new BlackPlayer());
    }

}
