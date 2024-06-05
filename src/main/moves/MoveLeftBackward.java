package main.moves;

import java.util.ArrayList;
import java.util.List;

public class MoveLeftBackward implements Movable {
    @Override
    public Position getPossiblePosition(Position currentPosition) {
        Position possiblePosition = new Position(currentPosition.getX() - 1, currentPosition.getY() - 1);
        return possiblePosition.isMovable() ? possiblePosition : null;
    }
    @Override
    public List<Position> getPossiblePositions(Position currentPosition) {
        List<Position> possibleMoves = new ArrayList<>();
        Position possiblePosition = new Position(currentPosition.getX() - 1, currentPosition.getY() - 1);
        if (possiblePosition.isMovable()) {
            possibleMoves.add(possiblePosition);
            return getPossiblePositions(possiblePosition, possibleMoves);
        }
        return possibleMoves;
    }

    @Override
    public boolean isDiagonal() {
        return true;
    }

    private List<Position> getPossiblePositions(Position currentPosition, List<Position> possiblePositions) {
        Position possiblePosition = new Position(currentPosition.getX() - 1, currentPosition.getY() - 1);
        if (possiblePosition.isMovable()) {
            possiblePositions.add(possiblePosition);
            return getPossiblePositions(possiblePosition, possiblePositions);
        }
        return possiblePositions;
    }
}
