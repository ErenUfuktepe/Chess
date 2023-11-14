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
    public boolean canMoveBackwards() {
        return true;
    }

    public List<Position> getPossibleMoves() {
        List<Position> possibleMoves = new ArrayList<>();
        switch (this.pieceType) {
            case PAWN:
                possibleMoves.addAll(moveForwards());
                possibleMoves.addAll(moveCrossRightTop());
                return possibleMoves;
            case KING:
            case QUEEN:
                possibleMoves.addAll(moveForwards());
                possibleMoves.addAll(moveBackwards());
                possibleMoves.addAll(moveCrossRightTop());
                possibleMoves.addAll(moveCrossRightBottom());
                possibleMoves.addAll(moveCrossLeftTop());
                possibleMoves.addAll(moveCrossLeftBottom());
                return possibleMoves;
            case ROOK:
                possibleMoves.addAll(moveForwards());
                possibleMoves.addAll(moveBackwards());
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
        moves.add(new Position(this.position.getX(), this.position.getY() + 1));
        boolean isMovable = (this.position.getY() + 1) < 8;

        // Pawn and King can only move one box at a time.
        if ((this.pieceType.equals(PieceType.PAWN) || this.pieceType.equals(PieceType.KING) && isMovable)) {
            isMovable = (this.position.getY() + 2) < 8;
            // Pawn can move two box if it is it's first move.
            if(this.pieceType.equals(PieceType.PAWN) && ((Pawn) this).isFirstMove() && isMovable) {
                moves.add(new Position(this.position.getX(), this.position.getY() + 2));
            }
            return moves;
        }

        int nextPosition = this.position.getY() + 2;
        for ( ;nextPosition < 8; nextPosition++) {
            moves.add(new Position(this.position.getX(), this.position.getY() +nextPosition));
        }

        return moves;
    }

    private List<Position> moveBackwards() {
        List<Position> moves = new ArrayList<>();
        boolean isMovable = (this.position.getY() - 1) >= 0;

        // King can move one box at a time.
        if (this.pieceType.equals(PieceType.KING) && isMovable) {
            moves.add(new Position(this.position.getX(), this.position.getY() - 1));
            return moves;
        }

        for (int nextPosition = this.position.getY() ;nextPosition >= 0; nextPosition--) {
            isMovable = (this.position.getY() - 1) >= 0;
            if (!isMovable) {
                break;
            }
            moves.add(new Position(this.position.getX(), this.position.getY() - nextPosition));
        }

        return moves;
    }

    private List<Position> moveCrossRightTop() {
        List<Position> moves = new ArrayList<>();

        for (int nextPosition = 1; nextPosition < 8; nextPosition++) {
            boolean isMovable = (this.position.getX() + nextPosition > 8 && this.position.getY() + nextPosition > 8);
            if (!isMovable) {
                break;
            }
            else if(this.pieceType.equals(PieceType.KING) || this.pieceType.equals(PieceType.PAWN) ) {
                moves.add(new Position(this.position.getX() + nextPosition, this.position.getY() + nextPosition));
                break;
            }
            moves.add(new Position(this.position.getX() + nextPosition, this.position.getY() + nextPosition));
        }
        return moves;
    }

    private List<Position> moveCrossLeftBottom() {
        List<Position> moves = new ArrayList<>();

        for (int nextPosition = 1; nextPosition < 8; nextPosition++) {
            boolean isMovable = (this.position.getX() - nextPosition >= 0 && this.position.getY() - nextPosition >= 0);
            if (!isMovable) {
                break;
            }
            else if(this.pieceType.equals(PieceType.KING)) {
                moves.add(new Position(this.position.getX() - nextPosition, this.position.getY() - nextPosition));
                break;
            }
            moves.add(new Position(this.position.getX() - nextPosition, this.position.getY() - nextPosition));
        }
        return moves;
    }

    private List<Position> moveCrossLeftTop() {
        List<Position> moves = new ArrayList<>();

        for (int nextPosition = 1; nextPosition < 8; nextPosition++) {
            boolean isMovable = (this.position.getX() - nextPosition >= 0 && this.position.getY() + nextPosition < 8);
            if (!isMovable) {
                break;
            }
            else if(this.pieceType.equals(PieceType.KING)) {
                moves.add(new Position(this.position.getX() - nextPosition, this.position.getY() + nextPosition));
                break;
            }
            moves.add(new Position(this.position.getX() - nextPosition, this.position.getY() + nextPosition));
        }
        return moves;
    }


    private List<Position> moveCrossRightBottom() {
        List<Position> moves = new ArrayList<>();

        for (int nextPosition = 1; nextPosition < 8; nextPosition++) {
            boolean isMovable = (this.position.getX() + nextPosition < 8 && this.position.getY() - nextPosition >= 0);
            if (!isMovable) {
                break;
            }
            else if(this.pieceType.equals(PieceType.KING)) {
                moves.add(new Position(this.position.getX() + nextPosition, this.position.getY() - nextPosition));
                break;
            }
            moves.add(new Position(this.position.getX() + nextPosition, this.position.getY() - nextPosition));
        }
        return moves;
    }
}
