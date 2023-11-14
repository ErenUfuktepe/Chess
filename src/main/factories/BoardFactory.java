package main.factories;

import main.Board;
import main.users.Player;
import main.enums.Color;

import java.util.ArrayList;
import java.util.List;

public class BoardFactory {
    private static final PieceFactory pieceFactory = new PieceFactory();

    public List<Player> buildPlayers() {
        List<Player> players = new ArrayList<>();

        Player whitePlayer = new Player(Color.WHITE)
                .setPieces(pieceFactory.buildPiecesForPlayer(Color.WHITE));
        players.add(whitePlayer);

        Player blackPlayer = new Player(Color.BLACK)
                .setPieces(pieceFactory.buildPiecesForPlayer(Color.BLACK));
        players.add(blackPlayer);

        return players;
    }

    public Board buildBoard() {
        Board board = new Board();
        return board;
    }
}
