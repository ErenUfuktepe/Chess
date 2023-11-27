package main.pieces;

import main.Position;
import main.enums.Color;
import main.moves.Movable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Piece {
    private Position position = new Position();
    private Color color;
    private List<Position> conditionalMoves = new ArrayList<>();
    private boolean isFirstMove = true;
    private List<Movable> movables = new ArrayList<>();

    public Piece(Color color){
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public Piece setPosition(int x, int y) {
        this.position.setX(x)
                .setY(y);
        return this;
    }

    public Color getColor() {
        return color;
    }

    public Piece setColor(Color color) {
        this.color = color;
        return this;
    }

    public String getIconUrl() {
        return "../resources/images/" + this.getColor().toString().toLowerCase() + "/" + this.getClass().getSimpleName().toLowerCase() + ".png";
    }

    public List<Position> getPossibleMoves(Map<String, Piece> pieceMap) {
        List<Position> possibleMoves = new ArrayList<>();
        this.movables.parallelStream()
                .forEach(movable -> possibleMoves.addAll(movable.getPossiblePositions(this.position)));
        return possibleMoves;
    }

    public List<Position> getPossibleMoves() {
        List<Position> possibleMoves = new ArrayList<>();
        this.movables.parallelStream()
                .forEach(movable -> possibleMoves.addAll(movable.getPossiblePositions(this.position)));
        return possibleMoves;
    }

    public List<Position> getConditionalMoves() {
        return conditionalMoves;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public Piece setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
        return this;
    }

    public Piece addConditionalMoves(Position conditionalPosition) {
        this.conditionalMoves.add(conditionalPosition);
        return this;
    }

    public List<Position> getAllMoves() {
        List<Position> allMoves = new ArrayList<>();
        allMoves.addAll(this.conditionalMoves);
        return allMoves;
    }


    // Todo: can change the return type
    public Piece move(String key) {
        this.position.setX(Character.digit(key.charAt(0), 10))
                .setY(Character.digit(key.charAt(1), 10));
        return this;
    }

    protected boolean isMovable(Movable movable, Map<String, Piece> pieceMap) {
        Position possibleMove = movable.getPossiblePosition(this.getPosition());
        if (possibleMove == null) {
            return false;
        }

        if (pieceMap.get(possibleMove.getKey()) == null) {
            if (movable.isDiagonal()) {
                return false;
            }
            return true;
        }
        else {
            boolean isSameColor = pieceMap.get(possibleMove.getKey()).getColor().equals(this.getColor());
            if (!isSameColor && movable.isDiagonal()) {
                return true;
            }
            return false;
        }
    }

    protected boolean isMovable(Map<String, Piece> pieceMap, Position position, AtomicBoolean isBlocked) {
        if (isBlocked.get()) {
            return false;
        }
        if (pieceMap.get(position.getKey()) == null) {
            return true;
        }
        else if (pieceMap.get(position.getKey()) != null) {
            isBlocked.set(true);
            if (!pieceMap.get(position.getKey()).getColor().equals(this.getColor())) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    protected boolean isMovable(Position possiblePosition, Map<String, Piece> pieceMap) {
        if (pieceMap.get(possiblePosition.getKey()) == null) {
            return true;
        }
        return !pieceMap.get(possiblePosition.getKey()).getColor().equals(this.getColor()) ? true : false;
    }

    public List<Movable> getMovable() {
        return movables;
    }

    public Piece addMovable(Movable movable) {
        this.movables.add(movable);
        return this;
    }
}
