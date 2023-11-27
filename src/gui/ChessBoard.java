package gui;

import main.Position;
import main.pieces.Piece;
import main.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ChessBoard extends JFrame {
    private static final JPanel panel = new JPanel();
    private final List<Square> squares = new ArrayList<>();
    private Player player1;
    private Player player2;

    public ChessBoard(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        this.player1.setupPlayer();
        this.player2.setupPlayer();

        setupSquares();
        setup();

        this.player1.getPieces().stream()
                .forEach(piece -> placePiecesToSquares(piece));
        this.player2.getPieces().stream()
                .forEach(piece -> placePiecesToSquares(piece));

        disablePiecesBasedOnTurn();
    }

    private void setupSquares() {
        int xAxis = 0, yAxis = 0, key = 7;
        boolean makeDark = true;

        for (int index = 0; index < 64; index++, key+=10, xAxis+=50) {
            // New line.
            if (index != 0 && index % 8 == 0) {
                yAxis = yAxis + 50;
                xAxis = 0;
                key = key - 81;
            }
            else {
                makeDark = !makeDark;
            }

            Square square = new Square();
            square.addActionListener(squareAction());
            square.setBounds(xAxis, yAxis, 50, 50);
            // Formatting one digit keys to two digit -> (1 => 01)
            square.setKey(String.format("%02d", key));

            Color color = makeDark ? Color.DARK_GRAY : Color.WHITE;
            square.setColor(color);

            squares.add(square);
            panel.add(square);
        }
    }

    private void setup() {
        this.panel.setLayout(null);
        add(this.panel);
        setSize(415, 440);
        setBackground(Color.BLACK);
        setTitle("Chess");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private Square getSquare(String key) {
        return this.squares.parallelStream()
                .filter(square -> square.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Square not found for the key" + key + "."));
    }

    private void disablePiecesBasedOnTurn() {
        player1.getPieces().parallelStream()
                .forEach(piece -> getSquare(piece.getPosition().getKey()).setEnabled(player1.isTurn()));
        player2.getPieces().parallelStream()
                .forEach(piece -> getSquare(piece.getPosition().getKey()).setEnabled(player2.isTurn()));
    }

    private void switchPlayer() {
        boolean player1Turn = player1.isTurn(), player2Turn = player2.isTurn();
        this.player1.setTurn(player2Turn);
        this.player2.setTurn(player1Turn);
        disablePiecesBasedOnTurn();
    }

    private Square getSquareByKey(String key) {
        return squares.stream().filter(square -> square.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Square not found for the given key."));
    }

    private void placePiecesToSquares(Piece piece) {
        Square square = this.squares.stream()
                .filter(sq -> sq.getKey().equals(piece.getPosition().getKey()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Square not found for the given key."));
        square.setPiece(piece);
    }

    private ActionListener squareAction() {
        return event -> {
            Square activeSquare = ((Square) event.getSource());

            if (activeSquare.hasPiece() && !activeSquare.getBackground().equals(Color.RED)) {
                Map<String, Piece> pieceMap = squares.parallelStream()
                        .filter(a-> a.hasPiece())
                        .collect(Collectors.toMap(sq -> sq.getKey(), sq -> sq.getPiece()));
                List<Position> test = activeSquare.getPiece().getPossibleMoves(pieceMap);
                cleanBoard();
                disablePiecesBasedOnTurn();
                setMovables(test);
            }

            Square previousActiveSquare = squares.stream()
                    .filter(square -> (square.isActive() && !square.getKey().equals(activeSquare.getKey())))
                    .findFirst()
                    .orElse(null);

            // Moving a piece to a possible position.
            if (previousActiveSquare != null
                    && (activeSquare.getBackground().equals(Color.GREEN) || activeSquare.getBackground().equals(Color.RED) )) {
                activeSquare.setPiece(previousActiveSquare.getPiece());
                activeSquare.getPiece().move(activeSquare.getKey());
                previousActiveSquare.setPiece(null);
                cleanBoard();
                switchPlayer();
            }
            else {
                activeSquare.setActive(true);
            }

            if (previousActiveSquare != null) {
                previousActiveSquare.reset();
            }
        };
    }

    private void cleanBoard() {
        squares.parallelStream().forEach(square -> square.restBackGround());
    }

    private void setMovables(List<Position> square) {
        square.stream()
                .filter(position -> position != null)
                .forEach(position -> getSquareByKey(position.getKey()).setMovable());
    }
}
