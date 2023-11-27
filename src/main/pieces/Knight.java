package main.pieces;

import main.Position;
import main.enums.Color;
import main.moves.MoveL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color);
        this.addMovable(new MoveL());
    }

    @Override
    public List<Position> getPossibleMoves(Map<String, Piece> pieceMap) {
        List<Position> possibleMoves = new ArrayList<>();
        this.getMovable().parallelStream()
                .forEach(movable -> possibleMoves.addAll(movable.getPossiblePositions(this.getPosition())));

        return possibleMoves.parallelStream()
                .filter(position -> isMovable(position, pieceMap))
                .collect(Collectors.toList());
    }

}