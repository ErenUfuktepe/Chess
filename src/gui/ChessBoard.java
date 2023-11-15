package gui;

import main.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

            // Formatting one digit keys to two digit -> (1 => 01)
            square.setKey(String.format("%02d", key));

            if (!isBlack) {
                square.setBackground(Color.WHITE);
            }
            else {
                square.setBackground(Color.DARK_GRAY);
            }

            square.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(square.getKey());
                }
            });

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

    public void build() {
        this.board.buildWhitePlayer()
                .buildBlackPlayer();

        this.board.placeWhitePlayerPieces();
        this.board.placeBlackPlayerPieces();

        this.board.getWhitePlayer().getPieces().stream()
                .forEach(piece -> setIconByKey(piece.getPosition().getKey(), piece.getIconUrl()));

        this.board.getBlackPlayer().getPieces().stream()
                .forEach(piece -> setIconByKey(piece.getPosition().getKey(), piece.getIconUrl()));
    }




}
