package main.moves;

import java.util.List;

public interface Movable {
    Position getPossiblePosition(Position currentPosition);
    List<Position> getPossiblePositions(Position currentPosition);
    boolean isDiagonal();
}
