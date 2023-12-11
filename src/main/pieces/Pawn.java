package main.pieces;

import main.Position;
import main.moves.*;

import java.awt.*;
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
        int rule = this.getColor().equals(Color.WHITE) ? 2: -2;

        this.getMovable().parallelStream()
                .filter(movable -> isMovable(movable, pieceMap))
                .forEach(movable -> possibleMoves.add(movable.getPossiblePosition(this.getPosition())));

        Position firstMove = new Position(this.getPosition().getX(), this.getPosition().getY() + rule);
        if (isFirstMove() && pieceMap.get(firstMove.getKey()) == null && possibleMoves.size() > 0) {
            possibleMoves.add(firstMove);
        }

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

    private void changeThePiece() {
        // Todo : Change the piece
        System.out.println("Victory will be ours!");
    }

}
