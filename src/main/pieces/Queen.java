package main.pieces;

import main.Position;
import main.moves.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Queen extends Piece {

    public Queen(Color color) {
        super(color);
        this.addMovable(new MoveForward())
                .addMovable(new MoveBackward())
                .addMovable(new MoveLeft())
                .addMovable(new MoveRight())
                .addMovable(new MoveLeftBackward())
                .addMovable(new MoveLeftForward())
                .addMovable(new MoveRightBackward())
                .addMovable(new MoveRightForward());
    }

    @Override
    public List<Position> getPossibleMoves(Map<String, Piece> pieceMap) {
        List<Position> possibleMoves = new ArrayList<>();

        for (Movable movable : this.getMovable()) {
            AtomicBoolean isBlocked = new AtomicBoolean(false);
            List<Position> positionList = movable.getPossiblePositions(this.getPosition());
            positionList.stream()
                    .filter(position -> this.isMovable(pieceMap, position, isBlocked))
                    .forEach(position -> possibleMoves.add(position));
        }
        return possibleMoves;
    }
}
