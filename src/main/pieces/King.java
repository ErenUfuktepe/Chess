package main.pieces;

import main.moves.Position;
import main.moves.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class King extends Piece {
    private boolean isChecked = false;

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

    public List<Position> getCastlingPositions(Map<String, Piece> pieceMap) {
        // TODO : Add the remaining castling conditions
        //        1) Can't do castling if it is check.
        //        1) Can't do castling if king moves to check position.
        List<Position> castlingMoves = new ArrayList<>();

        int yAxis = this.getColor().equals(Color.WHITE) ? 0 : 7;
        String leftRookCoordinate = "0" + yAxis;
        String rightRookCoordinate = "7" + yAxis;

        if (!this.isFirstMove()) {
            return castlingMoves;
        }

        // Check left castling.
        if (pieceMap.get(leftRookCoordinate) != null
                && pieceMap.get(leftRookCoordinate).getClass().equals(Rook.class)
                && pieceMap.get(leftRookCoordinate).isFirstMove()) {
            for (int xAxis = 1; xAxis <= 3; xAxis++) {
                String key = xAxis + String.valueOf(yAxis);
                if (pieceMap.get(key) != null) {
                    break;
                }
                else if (xAxis == 3) {
                    castlingMoves.add(new Position(0, yAxis));
                }
            }
        }

        // Check right castling.
        if (pieceMap.get(rightRookCoordinate) != null
                && pieceMap.get(rightRookCoordinate).getClass().equals(Rook.class)
                && pieceMap.get(rightRookCoordinate).isFirstMove()) {
            for (int xAxis = 5; xAxis <= 6; xAxis++) {
                String key = xAxis + String.valueOf(yAxis);
                if (pieceMap.get(key) != null) {
                    break;
                }
                else if (xAxis == 6) {
                    castlingMoves.add(new Position(7, yAxis));
                }
            }
        }
        return castlingMoves;
    }

    public void doCastling(Rook rook) {
        int yAxis = this.getColor().equals(Color.WHITE) ? 0 : 7;
        this.setPosition(rook.getPosition().getX() == 0 ? 2 : 6, yAxis)
                .setIsFirstMove(false);
        rook.setPosition(rook.getPosition().getX() == 0 ? 3 : 5, yAxis)
                .setIsFirstMove(false);
    }

    public boolean isChecked(){
        return this.isChecked;
    }

    public King setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
        return this;
    }
}
