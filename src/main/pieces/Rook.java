package main.pieces;

import main.moves.Position;
import main.moves.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Rook extends Piece {

    public Rook(Color color) {
        super(color);
        this.addMovable(new MoveForward())
                .addMovable(new MoveBackward())
                .addMovable(new MoveLeft())
                .addMovable(new MoveRight());
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
