package gui;

import main.Board;
import main.Position;
import main.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ChessBoard extends JFrame {
    private static final JPanel panel = new JPanel();
    private final List<Square> squares = new ArrayList<>();
    private final Board board = new Board();
    private Square activePiece;

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

            square.addMouseListener(test(square));

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

    // Todo : NoSuchelementexception
    private void setIconByKey(String key, String url) {
        this.squares.stream()
                .filter(square -> square.getKey().equals(key))
                .findFirst()
                .get()
                .setIcon(url);
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

    private MouseListener test(Square square) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Optional<Square> previousActiveButton = squares.stream()
                        .filter(sq -> (sq.isActive() && !sq.getKey().equals(square.getKey())))
                        .findFirst();

                if (previousActiveButton.isPresent()) {
                    System.out.println(previousActiveButton.get().getKey());
                    previousActiveButton.get()
                            .setActive(false)
                            .getPossibleMoves().stream().forEach(move -> getSquareByKey(move.getKey()).restBackGround());
                }

                for (Position position : square.getPossibleMoves()) {
                    getSquareByKey(position.getKey()).setBackground(Color.YELLOW);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }


}
