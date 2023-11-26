package gui;

import main.Position;
import main.pieces.Piece;
import main.player.BlackPlayer;
import main.player.Player;
import main.player.WhitePlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ChessBoard extends JFrame {
    private static final JPanel panel = new JPanel();
    private final List<Square> squares = new ArrayList<>();
    private Player player1 = new WhitePlayer();
    private Player player2 = new BlackPlayer();

    public ChessBoard() {
        int xAxis = 0;
        int yAxis = 0;
        boolean isBlack = false;
        int key = 7;

        for(int index = 0; index < 64; index++) {
            Square square = new Square();

            if (index != 0 && index % 8 == 0) {
                yAxis = yAxis + 50;
                xAxis = 0;
                key = key - 81;
                isBlack = !isBlack;
            }

            square.addActionListener(squareAction());

            // Formatting one digit keys to two digit -> (1 => 01)
            square.setKey(String.format("%02d", key));

            if (!isBlack) {
                square.setColor(Color.WHITE);
            }
            else {
                square.setColor(Color.DARK_GRAY);
            }

            isBlack = !isBlack;
            square.setBounds(xAxis, yAxis, 50, 50);
            xAxis = xAxis + 50;
            squares.add(square);
            panel.add(square);
            key = key + 10;
        }

        panel.setLayout(null);
        add(panel);

        setSize(415, 440);
        setBackground(Color.BLACK);
        setTitle("Chess");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private Square getSquareByKey(String key) {
        return squares.stream().filter(square -> square.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Square not found for the given key."));
    }

    public void mapPiecesWithSquares(Piece piece) {
        Square square = getSquareByKey(piece.getPosition().getKey());
        square.setPiece(piece);
    }

    public void build() {
        this.player1.setupPlayer();
        this.player2.setupPlayer();

        this.player1.getPieces().stream()
                .forEach(piece -> mapPiecesWithSquares(piece));
        this.player2.getPieces().stream()
                .forEach(piece -> mapPiecesWithSquares(piece));
    }


    private ActionListener squareAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Square activeSquare = ((Square) e.getSource());

                if (activeSquare.hasPiece() && !activeSquare.getBackground().equals(Color.RED)) {
                    Map<String, Piece> pieceMap = squares.parallelStream()
                            .filter(a-> a.hasPiece())
                            .collect(Collectors.toMap(sq -> sq.getKey(), sq -> sq.getPiece()));
                    List<Position> test = activeSquare.getPiece().compareBoardWithPossibleMoves(pieceMap);
                    cleanBoard();
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
                }
                else {
                    activeSquare.setActive(true);
                }

                if (previousActiveSquare != null) {
                    previousActiveSquare.reset();
                }
            }
        };
    }

    private void cleanBoard() {
        squares.parallelStream().forEach(square -> square.restBackGround());
    }

    private void setMovables(List<Position> square) {
        square.stream()
                .forEach(position -> getSquareByKey(position.getKey()).setMovable());
    }
}
