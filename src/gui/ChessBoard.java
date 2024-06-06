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
    private static final JPanel panel = new JPanel();
    private final List<Square> squares = new ArrayList<>();
    private Player player1;
    private Player player2;
    private Map<String, Piece> pieceMap = new HashMap<>();

    public ChessBoard(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void build() {
        setupMainWindow();
        // Placing players pieces to board.
        Stream.concat(player1.getPieces().stream(), player2.getPieces().stream())
                        .forEach(piece -> placePieceToSquare(piece));
        // Creating a key(position) - piece map.
        this.pieceMap = getPieceMap();
        disablePiecesBasedOnTurn();
        disableEmptySquare();
    }

    private void setupMainWindow() {
        setupChessBoard();
        this.panel.setLayout(null);
        this.add(this.panel);
        this.setSize(415, 440);
        this.setBackground(Color.BLACK);
        this.setTitle("Chess");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void setupChessBoard() {
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
    }

    private Square getSquare(String key) {
        return this.squares.parallelStream()
                .filter(square -> square.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Square not found for the key" + key + "."));
    }

    private void disablePiecesBasedOnTurn() {
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
        disableEmptySquare();
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
            .filter(a -> a.hasPiece())
            .collect(Collectors.toMap(sq -> sq.getKey(), sq -> sq.getPiece()));
    }

    private void refreshPieceMap() {
        this.pieceMap = getPieceMap();
    }

    private void refreshBackgroundColors() {
        // Reset all the squares background color to their original color.
        squares.parallelStream().forEach(square -> square.reset());
    }

    private ActionListener squareAction() {
        return event -> {
            boolean isPlayed = false;
            disablePiecesBasedOnTurn();
            // Square that is clicked to move a Piece to new square.
            Square actionSquare = ((Square) event.getSource());
            // Square that is clicked to move a Piece.
            Square activeSquare = squares.parallelStream()
                    .filter(square -> (square.isActive()))
                    .findFirst()
                    .orElse(null);

            // Move the piece
            if(actionSquare.getBackground().equals(Color.GREEN)
                    || actionSquare.getBackground().equals(Color.RED)) {

                if(activeSquare != null) {
                    activeSquare.setActive(false);
                }

                // Move empty space
                if(!actionSquare.hasPiece()){
                    activeSquare.getPiece().movePiece(actionSquare.getKey());
                }
                else if(actionSquare.getBackground().equals(Color.RED)) {
                    activeSquare.getPiece().takes(actionSquare.getPiece());
                }
                else if(actionSquare.getBackground().equals(Color.GREEN)) {
                    ((King) activeSquare.getPiece()).doCastling((Rook) actionSquare.getPiece());
                }

                getSquare(actionSquare.getKey()).setPiece(activeSquare.getPiece());
                getSquare(activeSquare.getKey()).setPiece(null);
                refreshBackgroundColors();
                refreshPieceMap();
                switchPlayer();
            }
            // Get possible moves
            else if (actionSquare.hasPiece()) {
                refreshBackgroundColors();
                // Get the possible positions for the active piece.
                List<Position> possibleMoves = actionSquare.getPiece().getMoves(this.pieceMap);
                // Change possible positions background color.
                possibleMoves.stream().forEach(position -> {
                    Square possibleSquare = getSquare(position.getKey());
                    // If there is opponents piece, set the background color to red.
                    Color background = possibleSquare.hasPiece() && !possibleSquare.getPiece().getColor().equals(actionSquare.getPiece().getColor())
                            ? Color.RED
                            : Color.GREEN;
                    possibleSquare.makeMovable(background);
                });
                actionSquare.setActive(true);
            }
        };
    }
}
