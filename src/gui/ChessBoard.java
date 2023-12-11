package gui;

import main.Position;
import main.pieces.King;
import main.pieces.Piece;
import main.pieces.Rook;
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
        disableEmptySquare();
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

    private void setupSquares() {
        int xAxis = 0, yAxis = 0, key = 7;
        boolean makeDark = true;

        for (int index = 0; index < 64; index++, key += 10, xAxis += 50) {
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
        disableEmptySquare();
    }

    private void placePiecesToSquares(Piece piece) {
        Square square = this.squares.stream()
                .filter(sq -> sq.getKey().equals(piece.getPosition().getKey()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Square not found for the given key."));
        square.setPiece(piece);
    }

    private void disableEmptySquare() {
        this.squares.parallelStream()
            .filter(square -> !square.hasPiece())
            .forEach(square -> square.setEnabled(false));
    }

    private ActionListener squareAction() {
        return event -> {
            boolean isPlayed = false;

            Square activeSquare = ((Square) event.getSource());
            Square previousActiveSquare = squares.parallelStream()
                    .filter(square -> (square.isActive()))
                    .findFirst()
                    .orElse(null);


            // Move the piece
            if (activeSquare.getBackground().equals(Color.GREEN)
                    || activeSquare.getBackground().equals(Color.RED)) {
                isPlayed = true;
                if (!activeSquare.hasPiece() || activeSquare.getBackground().equals(Color.RED)) {
                    previousActiveSquare.getPiece().setPosition(activeSquare.getKey());
                }
                else if (activeSquare.getBackground().equals(Color.GREEN)) {
                    ((King) previousActiveSquare.getPiece()).doCastling((Rook) activeSquare.getPiece());
                }
                squares.parallelStream().forEach(square -> square.reset());
                squares.parallelStream()
                    .forEach(square -> {
                        if (square.isMoved()) {
                            getSquare(square.getPiece().getPosition().getKey())
                                    .setPiece(square.getPiece());
                            square.setPiece(null);
                        }
                    });
                switchPlayer();
            }
            // Get possible moves
            else if (activeSquare.hasPiece()) {
                squares.parallelStream().forEach(square -> square.reset());

                Map<String, Piece> pieceMap = squares.parallelStream()
                        .filter(a -> a.hasPiece())
                        .collect(Collectors.toMap(sq -> sq.getKey(), sq -> sq.getPiece()));
                List<Position> possibleMoves = activeSquare.getPiece().getMoves(pieceMap);

                Color color = player1.isTurn() ? player1.getColor() : player2.getColor();

                possibleMoves.stream().forEach(position -> {
                    Square possibleSquare = getSquare(position.getKey());
                    Color background = possibleSquare.hasPiece() && !possibleSquare.getPiece().getColor().equals(color) ? Color.RED : Color.GREEN;
                    possibleSquare.makeMovable(background);
                });
            }

            if (!isPlayed) {
                activeSquare.setActive(true);
                if (previousActiveSquare != null) {
                    previousActiveSquare.setActive(false);
                }
            }
        };
    }
}
