package main.pieces;

import main.Position;
import main.moves.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class King extends Piece {

    public King(Color color) {
        super(color);
        this.addMovable(new MoveForward())
                .addMovable(new MoveBackward())
                .addMovable(new MoveLeft())
                .addMovable(new MoveRight())
                .addMovable(new MoveLeftBackward())
                .addMovable(new MoveLeftForward())
                .addMovable(new MoveRightBackward())
                .addMovable(new MoveRightForward());
    }

    @Override
    public List<Position> getPossibleMoves(Map<String, Piece> pieceMap) {
        List<Position> possibleMoves = new ArrayList<>();
        this.getMovable().parallelStream()
                .filter(movable -> isMovable(movable, pieceMap))
                .forEach(movable -> possibleMoves.add(movable.getPossiblePosition(this.getPosition())));
        possibleMoves.addAll(getCastlingPositions(pieceMap));
        return possibleMoves;
    }

    @Override
    protected boolean isMovable(Movable movable, Map<String, Piece> pieceMap) {
        Position possibleMove = movable.getPossiblePosition(this.getPosition());
        if (possibleMove == null) {
            return false;
        }
        if (pieceMap.get(possibleMove.getKey()) == null) {
            return true;
        }
        else {
            boolean isSameColor = pieceMap.get(possibleMove.getKey()).getColor().equals(this.getColor());
            if (!isSameColor && movable.isDiagonal()) {
                return true;
            }
            return false;
        }
    }

    private boolean isCastlingRookConditionMet(Map<String, Piece> pieceMap) {
        String yAxis = this.getColor().equals(Color.WHITE) ? "0" : "7";
        String leftRookCoordinate = "0" + yAxis;
        String rightRookCoordinate = "7" + yAxis;

        boolean isLeftRookConditionMet = pieceMap.get(leftRookCoordinate) != null
                && pieceMap.get(leftRookCoordinate).getClass().equals(Rook.class)
                && pieceMap.get(leftRookCoordinate).isFirstMove();

        boolean isRightRookConditionMet = pieceMap.get(rightRookCoordinate) != null
                && pieceMap.get(rightRookCoordinate).getClass().equals(Rook.class)
                && pieceMap.get(rightRookCoordinate).isFirstMove();

        return isLeftRookConditionMet || isRightRookConditionMet;
    }

    public List<Position> getCastlingPositions(Map<String, Piece> pieceMap) {
        List<Position> castlingMoves = new ArrayList<>();
        boolean isCastling = true;

        if (!this.isFirstMove()) {
            return castlingMoves;
        }

        if (!isCastlingRookConditionMet(pieceMap)) {
            return castlingMoves;
        }

        for (int key = 1; key < 8; key++) {
            // Make decision if we are in king position or end of the board.
            boolean makeDecision = (key == 4 || key == 7);
            String positionKey = this.getColor().equals(Color.WHITE) ? key + "0" : key + "7";

            if (!makeDecision && !(isCastling && pieceMap.get(positionKey) == null)) {
                // If there is a piece between king and rook don't check all the locations.
                key = key < 4 ? 3 : 7;
                isCastling = false;
            }

            if (makeDecision) {
                if (isCastling) {
                    int xAxis = key == 4 ? 0 : 7;
                    int yAxis = this.getColor().equals(Color.WHITE) ? 0 : 7;
                    castlingMoves.add(new Position(xAxis, yAxis)); // Rook postion
                }
                isCastling = true;
            }
        }
        return castlingMoves;
    }

    public void doCastling(Rook rook) {
        int yAxis = this.getColor().equals(Color.WHITE) ? 0 : 7;
        this.setPosition(rook.getPosition().getX() == 0 ? 2 : 6, yAxis);
        rook.setPosition(rook.getPosition().getX() == 0 ? 3 : 5, yAxis);
    }

    @Override
    public Piece move(String key) {
        if (this.isFirstMove()) {
            this.setFirstMove(false);
        }
        this.getPosition().setX(Character.digit(key.charAt(0), 10))
                .setY(Character.digit(key.charAt(1), 10));

        return this;
    }
}
