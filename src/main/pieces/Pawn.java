package main.pieces;

import main.Position;
import main.Rule;
import main.enums.Color;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece implements Rule {
    private boolean isFirstMove = true;

    public Pawn(Color color){
        super(color);
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public Pawn setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
        return this;
    }

    @Override
    public List<Position> getPossiblePositions() {
        List<Position> possiblePositions = new ArrayList<>();

        // Move one box in Y axis.
        possiblePositions.add(new Position(this.getPosition().getX(), this.getPosition().getY() + 1));
        // Cross take moves.
        possiblePositions.add(new Position(this.getPosition().getX() - 1, this.getPosition().getY() + 1));
        possiblePositions.add(new Position(this.getPosition().getX() + 1, this.getPosition().getY() + 1));

        if (this.isFirstMove) {
            possiblePositions.add(new Position(this.getPosition().getX(), this.getPosition().getY() + 2));
        }

        return possiblePositions;
    }

    @Override
    public boolean canMoveBackwards() {
        return false;
    }
}
