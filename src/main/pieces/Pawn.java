package main.pieces;

import main.Position;
import main.enums.Color;
import main.enums.PieceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pawn extends Piece {
    private boolean isFirstMove = true;

    public Pawn(Color color){
        super(PieceType.PAWN, color);
        if(this.getColor().equals(Color.WHITE)) {
            this.setIconUrl("../resources/images/pawn_white.png");
        }
        else {
            this.setIconUrl("../resources/images/pawn_black.png");
        }
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public Pawn setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
        return this;
    }

    @Override
    public boolean canMoveBackwards() {
        return false;
    }

    @Override
    public void compareBoardWithPossibleMoves(Map<String, Color> pieceMap) {
        Color oppositeColor = this.getColor().equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
        List<Position> positionList = new ArrayList<>(this.getPossibleMoves());

        for (Position position : positionList) {
            if (pieceMap.get(position.getKey()) == null) {
                if (this.getPosition().getX() != position.getX()) {
                    this.getPossibleMoves().remove(position);
                }
            }
            else if (pieceMap.get(position.getKey()).equals(oppositeColor)) {
                if (!(this.getPosition().getX() != position.getX())) {
                    this.getPossibleMoves().remove(position);
                }
            }
        }
    }

    @Override
    public Piece move(String key) {
        if (isFirstMove) {
            this.isFirstMove = false;
        }
        this.getPosition().setX(Character.digit(key.charAt(0), 10))
                .setY(Character.digit(key.charAt(1), 10));
        this.setPossibleMoves();
        return this;
    }

    @Override
    public void checkConditionalMoves() {
        // Can move two squares.
        if (this.isFirstMove) {
            this.addConditionalMoves(possibleFirstMove());
        }
    }

    private Position possibleFirstMove() {
        // If it is white, it can move two square forward.
        // If it is black, it can move two square forward.
        int move = this.getColor().equals(Color.WHITE) ? 2 : -2;
        return new Position()
                .setX(this.getPosition().getX())
                .setY(this.getPosition().getY() + move);
    }





}
