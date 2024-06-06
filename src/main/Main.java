package main;

import gui.ChessBoard;
import main.player.Player;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Player player1 = new Player(Color.WHITE);
        Player player2 = new Player(Color.BLACK);
        player1.setTurn(true);

        ChessBoard chessBoard = new ChessBoard(player1, player2);
        chessBoard.build();
    }

}
