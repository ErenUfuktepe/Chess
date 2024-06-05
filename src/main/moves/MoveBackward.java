package main.moves;

import java.util.ArrayList;
import java.util.List;

public class MoveBackward implements Movable {
    @Override
    public Position getPossiblePosition(Position currentPosition) {
        Position possiblePosition = new Position(currentPosition.getX(), currentPosition.getY() - 1);
        return possiblePosition.isMovable() ? possiblePosition : null;
    }

    @Override
    public List<Position> getPossiblePositions(Position currentPosition) {
        List<Position> possibleMoves = new ArrayList<>();
        Position possiblePosition = new Position(currentPosition.getX(), currentPosition.getY() - 1);
        if (possiblePosition.isMovable()) {
            possibleMoves.add(possiblePosition);
            return getPossiblePositions(possiblePosition, possibleMoves);
        }
        return possibleMoves;
    }

    @Override
    public boolean isDiagonal() {
        return false;
    }

    private List<Position> getPossiblePositions(Position currentPosition, List<Position> possiblePositions) {
        Position possiblePosition = new Position(currentPosition.getX(), currentPosition.getY() - 1);
        if (possiblePosition.isMovable()) {
            possiblePositions.add(possiblePosition);
            return getPossiblePositions(possiblePosition, possiblePositions);
        }
        return possiblePositions;
    }
}
