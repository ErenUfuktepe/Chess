package gui;

import main.moves.Position;
import main.pieces.King;
import main.pieces.Piece;
import main.pieces.Rook;
import main.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChessBoard extends JFrame {

    private static final String TITLE = "Chess";
    private static final int WIDTH = 415;
    private static final int HEIGHT = 440;
    private static final JPanel panel = new JPanel();
    private final List<Square> squares = new ArrayList<>();
    private final Player player1;
    private final Player player2;
    private Map<String, Piece> pieceMap = new HashMap<>();

    public ChessBoard(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        setupSquare();
    }

    public void build() {
        panel.setLayout(null);
        this.add(panel);
        this.setSize(WIDTH, HEIGHT);
        this.setBackground(Color.BLACK);
        this.setTitle(TITLE);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        disablePiecesBasedOnTurn();
    }

    private void setupSquare() {
        int xAxis = 0, yAxis = 0, key = 7;
        boolean makeDark = true;

        for (int index = 0; index < 64; index++, key += 10, xAxis += 50) {
            makeDark = !makeDark;
            // After every eight square update yAxis
            if (index != 0 && index % 8 == 0) {
                yAxis = yAxis + 50;
                xAxis = 0;
                key = key - 81;
                makeDark = !makeDark;
            }
            Square square = new Square(xAxis, yAxis, makeDark ? Color.DARK_GRAY : Color.WHITE);
            square.addActionListener(squareAction());
            // Formatting one digit keys to two digit -> (1 => 01)
            square.setKey(String.format("%02d", key));
            squares.add(square);
            panel.add(square);
        }
        // Placing players pieces to board.
        Stream.concat(player1.getPieces().stream(), player2.getPieces().stream())
                .forEach(this::placePieceToSquare);
        // Creating a key(position) - piece map.
        this.pieceMap = getPieceMap();
    }

    private Square getSquare(String key) {
        return this.squares.parallelStream()
                .filter(square -> square.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Square not found for the key" + key + "."));
    }

    private void disablePiecesBasedOnTurn() {
        disableEmptySquare();
        player1.getPieces().parallelStream()
                .filter(piece -> !piece.isTaken())
                .forEach(piece -> getSquare(piece.getPosition().getKey()).setEnabled(player1.isTurn()));
        player2.getPieces().parallelStream()
                .filter(piece -> !piece.isTaken())
                .forEach(piece -> getSquare(piece.getPosition().getKey()).setEnabled(player2.isTurn()));
    }

    private void switchPlayer() {
        this.player1.setTurn(!player1.isTurn());
        this.player2.setTurn(!player2.isTurn());
        disablePiecesBasedOnTurn();
    }

    private void placePieceToSquare(Piece piece) {
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

    private Map<String, Piece> getPieceMap() {
        return squares.parallelStream()
            .filter(Square::hasPiece)
            .collect(Collectors.toMap(Square::getKey, Square::getPiece));
    }

    private void refreshChessBoard(Square activeSquare) {
        // Reset all the squares background color to their original color.
        squares.parallelStream().forEach(square -> {
            // Deactivate the previous square.
            if (square.isActive()) {
                square.setActive(false);
            }
            if (square.hasPiece() && !square.getPiece().getPosition().getKey().equals(square.getKey())) {
                String key = square.getPiece().getPosition().getKey();
                getSquare(key).setPiece(square.getPiece());
                square.setPiece(null);
            }
            square.reset();
        });
        // Set the new active square.
        if (activeSquare != null) {
            activeSquare.setActive(true);
        }
    }

    private ActionListener squareAction() throws RuntimeException {
        return event -> {
            Square activeSquare = ((Square) event.getSource());

            // Get possible moves for the piece.
            if (activeSquare.getBackground().equals(activeSquare.getColor())) {
                disableEmptySquare();
                refreshChessBoard(activeSquare);
                List<Position> possibleMoves = activeSquare.getPiece().getMoves(this.pieceMap);
                possibleMoves.parallelStream().forEach(position -> {
                    Square possibleSquare = getSquare(position.getKey());
                    // If there is opponents piece, set the background color to red.
                    Color background = possibleSquare.hasPiece() && !possibleSquare.getPiece().getColor().equals(activeSquare.getPiece().getColor())
                            ? Color.RED
                            : Color.GREEN;
                    possibleSquare.makeMovable(background);
                });
                return;
            }

            Square activeSquareWithPiece = this.squares.parallelStream()
                    .filter(Square::isActive)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No active Square found."));

            // Move to empty square.
            if (!activeSquare.hasPiece()) {
                activeSquareWithPiece.getPiece().movePiece(activeSquare.getKey());
            }
            // Takes opponents piece.
            else if (activeSquare.getBackground().equals(Color.RED)) {
                activeSquareWithPiece.getPiece().takes(activeSquare.getPiece());
            }
            // Castling.
            else if (activeSquare.getBackground().equals(Color.GREEN)) {
                ((King) activeSquareWithPiece.getPiece()).doCastling((Rook) activeSquare.getPiece());
            }

            refreshChessBoard(null);
            this.pieceMap = getPieceMap();
            switchPlayer();
        };
    }
}
