package main.pieces;

import main.Position;
import main.enums.Color;
import main.moves.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pawn extends Piece {

    public Pawn(Color color){
        super(color);
        if (color.equals(Color.WHITE)) {
            this.addMovable(new MoveForward())
                    .addMovable(new MoveRightForward())
                    .addMovable(new MoveLeftForward());
        }
        else {
            this.addMovable(new MoveBackward())
                    .addMovable(new MoveRightBackward())
                    .addMovable(new MoveLeftBackward());
        }
    }

    @Override
    public List<Position> getPossibleMoves() {
        List<Position> possibleMoves = new ArrayList<>();
        this.getMovable().parallelStream()
                .forEach(movable -> possibleMoves.add(movable.getPossiblePosition(this.getPosition())));
        return possibleMoves;
    }

    @Override
    public List<Position> getPossibleMoves(Map<String, Piece> pieceMap) {
        List<Position> possibleMoves = new ArrayList<>();
        this.getMovable().parallelStream()
                .filter(movable -> isMovable(movable, pieceMap))
                .forEach(movable -> possibleMoves.add(movable.getPossiblePosition(this.getPosition())));
        return possibleMoves;
    }

    @Override
    protected boolean isMovable(Movable movable, Map<String, Piece> pieceMap) {
        Position possibleMove = movable.getPossiblePosition(this.getPosition());
        if (possibleMove == null) {
            return false;
        }

        if (pieceMap.get(possibleMove.getKey()) == null) {
            if (movable.isDiagonal()) {
                return false;
            }
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

    @Override
    public Piece move(String key) {
        if (this.isFirstMove()) {
            this.setFirstMove(false);
        }

        if (Character.digit(key.charAt(1), 10) == 0 || Character.digit(key.charAt(1), 10) == 7) {
            changeThePiece();
        }

        this.getPosition().setX(Character.digit(key.charAt(0), 10))
                .setY(Character.digit(key.charAt(1), 10));

        return this;
    }

    private void changeThePiece() {
        // Todo : Change the piece
        System.out.println("Victory will be ours!");
    }

}
