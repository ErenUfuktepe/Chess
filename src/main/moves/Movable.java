package main.moves;

import main.Position;

import java.util.List;

public interface Movable {
    List<Position> getPossiblePositions(Position currentPosition);
}
