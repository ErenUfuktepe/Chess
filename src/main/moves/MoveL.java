package main.moves;

import main.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoveL implements Movable{
    @Override
    public List<Position> getPossiblePositions(Position currentPosition) {
        List<Position> possibleMoves = new ArrayList<>();
        List<List<Integer>> rules = new ArrayList<>(Arrays.asList(
                Arrays.asList(1,2),
                Arrays.asList(1,-2),
                Arrays.asList(-1,2),
                Arrays.asList(-1,-2),
                Arrays.asList(2,1),
                Arrays.asList(2,-1),
                Arrays.asList(-2,1),
                Arrays.asList(-2,-1)
        ));

        rules.parallelStream().forEach(rule -> {
            Position position = new Position(currentPosition.getX() + rule.get(0), currentPosition.getY() + rule.get(1));
            if (position.isMovable()) {
                possibleMoves.add(position);
            }
        });
        return possibleMoves;
    }
}
