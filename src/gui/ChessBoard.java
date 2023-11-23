package gui;

import main.Board;
import main.Position;
import main.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ChessBoard extends JFrame {
    private static final JPanel panel = new JPanel();
    private final List<Square> squares = new ArrayList<>();
    private final Board board = new Board();

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

            square.addActionListener(buttonAction());

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
        this.board.buildWhitePlayer()
                .buildBlackPlayer();

        this.board.placeWhitePlayerPieces();
        this.board.placeBlackPlayerPieces();

        this.board.getWhitePlayer().getPieces().stream()
                .forEach(piece -> mapPiecesWithSquares(piece));

        this.board.getBlackPlayer().getPieces().stream()
                .forEach(piece -> mapPiecesWithSquares(piece));
    }


    private ActionListener buttonAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Square square = ((Square) e.getSource());

                Optional<Square> previousActiveButton = squares.stream()
                        .filter(sq -> (sq.isActive() && !sq.getKey().equals(square.getKey())))
                        .findFirst();

                if (!square.hasPiece()) {
                } else {
                    Map<String, main.enums.Color> test = squares.stream().filter(a-> a.hasPiece())
                            .collect(Collectors.toMap(sq -> sq.getKey(), sq -> sq.getPiece().getColor()));
                    System.out.println(test);
                    square.getPiece().compareBoardWithPossibleMoves(test);
                }


                if (square.getBackground().equals(Color.GREEN)) {
                    square.setPiece(previousActiveButton.get().getPiece());
                    square.getPiece().move(square.getKey());
                    movePiece(square);
                    squares.stream().filter(sq -> sq.getBackground().equals(Color.GREEN))
                            .forEach(sq -> sq.restBackGround());
                    return;
                }

                square.setActive(true);
                square.setPossibleMoves(((Square) e.getSource()).getPiece().getPossibleMoves());
                square.setConditionalMoves(((Square) e.getSource()).getPiece().getConditionalMoves());
                square.setEnabled(false);
                square.setBackground(Color.YELLOW);

                if (previousActiveButton.isPresent()) {
                    previousActiveButton.get()
                            .setActive(false)
                            .restBackGround()
                            .getPossibleMoves().stream()
                                .forEach(move -> getSquareByKey(move.getKey()).restBackGround());
                }

                for (Position position : square.getPossibleMoves()) {
                    Square movable = getSquareByKey(position.getKey());
                    movable.setBackground(Color.GREEN);
                    movable.setEnabled(true);
                }

                for (Position position : square.getConditionalMoves()) {
                    Square movable = getSquareByKey(position.getKey());
                    if (!movable.hasPiece()) {
                        movable.setBackground(Color.GREEN);
                        movable.setEnabled(true);
                    }
                }



            }
        };
    }

    private void movePiece(Square square) {
        Square activeSquare = this.squares.stream()
                .filter(sq -> sq.isActive())
                .findFirst().get();
        activeSquare.restBackGround();
        activeSquare.setPiece(null);
    }




}
