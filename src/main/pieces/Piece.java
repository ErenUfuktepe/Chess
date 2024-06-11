package main.pieces;

import main.moves.Position;
import main.moves.Movable;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Piece {
    private Position position = new Position();
    private Color color;
    private boolean isFirstMove = true;
    private boolean isTaken = false;
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

    private Piece setPosition(String key) {
        if(key == null) {
            this.position = null;
            this.isTaken = true;
            return this;
        }
        this.position.setX(Character.digit(key.charAt(0), 10))
                .setY(Character.digit(key.charAt(1), 10));
        return this;
    }

    public Piece movePiece(String key) {
        this.isFirstMove = false;
        return setPosition(key);
    }

    public Piece takes(Piece takenPiece) {
        this.movePiece(takenPiece.getPosition().getKey());
        takenPiece.setPosition(null);
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
        String color = this.color.equals(Color.WHITE) ? "white" : "black";
        return "../resources/images/" + color + "/" + this.getClass().getSimpleName().toLowerCase() + ".png";
    }

    public List<Position> getMoves(Map<String, Piece> pieceMap) {
        List<Position> allMoves = this.getPossibleMoves(pieceMap);
        return allMoves;
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

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public Piece setIsFirstMove(boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
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

    public boolean isTaken(){
        return this.isTaken;
    }
}
