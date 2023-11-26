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
    private List<Movable> movable = new ArrayList<>();

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

    public List<Position> getPossibleMoves() {
        return new ArrayList<>();
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

    protected void checkConditionalMoves() {
        return;
    }

    public List<Position> compareBoardWithPossibleMoves(Map<String, Piece> pieceMap) {
        throw new UnsupportedOperationException();
    }



    protected boolean isMovable(Map<String, Piece> pieceMap, Position position, AtomicBoolean isBlocked) {
        if (isBlocked.get()) {
            return isBlocked.get();
        }
        if (pieceMap.get(position.getKey()) == null) {
            return isBlocked.get();
        }
        else if (pieceMap.get(position.getKey()) != null) {
            Color oppositeColor = this.getColor().equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
            isBlocked.set(true);
            if (pieceMap.get(position.getKey()).getColor().equals(oppositeColor)) {
                return false;
            }
        }
        return isBlocked.get();
    }

    public List<Movable> getMovable() {
        return movable;
    }

    public Piece addMovable(Movable movable) {
        this.movable.add(movable);
        return this;
    }
}
