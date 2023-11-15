package main.pieces;

import main.Position;
import main.enums.Color;
import main.enums.PieceType;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    private PieceType pieceType;
    private Position position = new Position();
    private Color color;
    private String iconUrl;

    public Piece(PieceType pieceType, Color color){
        this.color = color;
        this.pieceType = pieceType;
    }

    public Position getPosition() {
        return position;
    }

    public Piece setPosition(int x, int y) {
        this.position.setX(x)
                .setY(y);
        return this;
    }

    public Color getColor() {
        return color;
    }

    public Piece setColor(Color color) {
        this.color = color;
        return this;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public boolean canMoveBackwards() {
        return true;
    }

    public List<Position> getPossibleMoves() {
        List<Position> possibleMoves = new ArrayList<>();
        switch (this.pieceType) {
            case PAWN:
                possibleMoves.addAll(moveForwards());
                possibleMoves.addAll(moveCrossRightTop());
                possibleMoves.addAll(moveCrossLeftTop());
                return possibleMoves;
            case KING:
            case QUEEN:
                possibleMoves.addAll(moveForwards());
                possibleMoves.addAll(moveBackwards());
                possibleMoves.addAll(moveCrossRightTop());
                possibleMoves.addAll(moveCrossRightBottom());
                possibleMoves.addAll(moveCrossLeftTop());
                possibleMoves.addAll(moveCrossLeftBottom());
                possibleMoves.addAll(moveLeft());
                possibleMoves.addAll(moveRight());
                return possibleMoves;
            case ROOK:
                possibleMoves.addAll(moveForwards());
                possibleMoves.addAll(moveBackwards());
                possibleMoves.addAll(moveLeft());
                possibleMoves.addAll(moveRight());
                return possibleMoves;
            case BISHOP:
                possibleMoves.addAll(moveCrossRightTop());
                possibleMoves.addAll(moveCrossRightBottom());
                possibleMoves.addAll(moveCrossLeftTop());
                possibleMoves.addAll(moveCrossLeftBottom());
                return possibleMoves;
            case KNIGHT:
                return possibleMoves;
            default:
                throw new UnsupportedOperationException("Unknown piece.");
        }
    }

    private List<Position> moveForwards() {
        List<Position> moves = new ArrayList<>();
        boolean isMovable = (this.position.getY() + 1) < 8;

        if (!isMovable) {
            return moves;
        }

        // Pawn and King can only move one box at a time.
        if (this.pieceType.equals(PieceType.PAWN) || this.pieceType.equals(PieceType.KING)) {
            moves.add(new Position(this.position.getX(), this.position.getY() + 1));
            isMovable = (this.position.getY() + 2) < 8;
            // Pawn can move two box if it is it's first move.
            if(this.pieceType.equals(PieceType.PAWN) && ((Pawn) this).isFirstMove() && isMovable) {
                moves.add(new Position(this.position.getX(), this.position.getY() + 2));
            }
            return moves;
        }

        for (int nextPosition = this.position.getY(); nextPosition < 8; nextPosition++) {
            moves.add(new Position(this.position.getX(), nextPosition));
        }
        return moves;
    }

    private List<Position> moveBackwards() {
        List<Position> moves = new ArrayList<>();
        boolean isMovable = (this.position.getY() - 1) >= 0;

        if (!isMovable) {
            return moves;
        }

        // King can move one box at a time.
        if (this.pieceType.equals(PieceType.KING)) {
            moves.add(new Position(this.position.getX(), this.position.getY() - 1));
            return moves;
        }

        for (int nextPosition = this.position.getY() ;nextPosition >= 0; nextPosition--) {
            moves.add(new Position(this.position.getX(), nextPosition));
        }
        return moves;
    }

    private List<Position> moveRight() {
        List<Position> moves = new ArrayList<>();
        boolean isMovable = (this.position.getX() + 1) < 8;

        if (!isMovable) {
            return moves;
        }

        // King can move one box at a time.
        if (this.pieceType.equals(PieceType.KING)) {
            moves.add(new Position(this.position.getX() + 1, this.position.getY()));
            return moves;
        }

        for (int nextPosition = this.position.getY() ;nextPosition < 8; nextPosition++) {
            moves.add(new Position(nextPosition, this.position.getY()));
        }
        return moves;
    }

    private List<Position> moveLeft() {
        List<Position> moves = new ArrayList<>();
        boolean isMovable = (this.position.getX() + 1) >= 0;

        if (!isMovable) {
            return moves;
        }

        // King can move one box at a time.
        if (this.pieceType.equals(PieceType.KING)) {
            moves.add(new Position(this.position.getX() - 1, this.position.getY()));
            return moves;
        }

        for (int nextPosition = this.position.getX() ;nextPosition >= 0; nextPosition--) {
            moves.add(new Position(nextPosition, this.position.getY()));
        }
        return moves;
    }

    private List<Position> moveCrossRightTop() {
        List<Position> moves = new ArrayList<>();
        boolean isMovable = (this.position.getX() + 1 < 8 && this.position.getY() + 1 < 8);

        if (!isMovable) {
            return moves;
        }

        if(this.pieceType.equals(PieceType.KING) || this.pieceType.equals(PieceType.PAWN) ) {
            moves.add(new Position(this.position.getX() + 1, this.position.getY() + 1));
            return moves;
        }

        for (int nextPosition = 1; nextPosition < 8; nextPosition++) {
            isMovable = (this.position.getX() + nextPosition < 8 && this.position.getY() + nextPosition < 8);
            if (!isMovable) {
                break;
            }
            moves.add(new Position(this.position.getX() + nextPosition, this.position.getY() + nextPosition));
        }
        return moves;
    }

    private List<Position> moveCrossLeftBottom() {
        List<Position> moves = new ArrayList<>();
        boolean isMovable = (this.position.getX() - 1 >= 0 && this.position.getY() - 1 >= 0);

        if (!isMovable) {
            return moves;
        }

        if(this.pieceType.equals(PieceType.KING)) {
            moves.add(new Position(this.position.getX() - 1, this.position.getY() - 1));
            return moves;
        }

        for (int nextPosition = 1; nextPosition < 8; nextPosition++) {
            isMovable = (this.position.getX() - nextPosition >= 0 && this.position.getY() - nextPosition >= 0);
            if (!isMovable) {
                break;
            }
            moves.add(new Position(this.position.getX() - nextPosition, this.position.getY() - nextPosition));
        }
        return moves;
    }

    private List<Position> moveCrossLeftTop() {
        List<Position> moves = new ArrayList<>();
        boolean isMovable = (this.position.getX() - 1 >= 0 && this.position.getY() + 1 < 8);

        if (!isMovable) {
            return moves;
        }

        if(this.pieceType.equals(PieceType.KING)) {
            moves.add(new Position(this.position.getX() - 1, this.position.getY() + 1));
            return moves;
        }

        for (int nextPosition = 1; nextPosition < 8; nextPosition++) {
            isMovable = (this.position.getX() - nextPosition >= 0 && this.position.getY() + nextPosition < 8);
            if (!isMovable) {
                break;
            }
            moves.add(new Position(this.position.getX() - nextPosition, this.position.getY() + nextPosition));
        }
        return moves;
    }


    private List<Position> moveCrossRightBottom() {
        List<Position> moves = new ArrayList<>();
        boolean isMovable = (this.position.getX() + 1 < 8 && this.position.getY() - 1 >= 0);

        if (!isMovable) {
            return moves;
        }

        if(this.pieceType.equals(PieceType.KING)) {
            moves.add(new Position(this.position.getX() + 1, this.position.getY() - 1));
            return moves;
        }

        for (int nextPosition = 1; nextPosition < 8; nextPosition++) {
            isMovable = (this.position.getX() + nextPosition < 8 && this.position.getY() - nextPosition >= 0);
            if (!isMovable) {
                break;
            }
            moves.add(new Position(this.position.getX() + nextPosition, this.position.getY() - nextPosition));
        }
        return moves;
    }

    @Override
    public String toString() {
        System.out.println("==============================");
        System.out.println(this.pieceType);
        System.out.println("X: " + this.position.getX() + "\n Y: " + this.position.getY());
        System.out.println("==============================");
        for (Position position : getPossibleMoves()) {
            System.out.println("X: " + position.getX() + "\n Y: " + position.getY());
        }
        return "";
    }

}
