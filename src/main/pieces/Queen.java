package main.pieces;

import main.Position;
import main.enums.Color;
import main.enums.PieceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Queen extends Piece {

    public Queen(Color color) {
        super(PieceType.QUEEN, color);
        if(this.getColor().equals(Color.WHITE)) {
            this.setIconUrl("../resources/images/queen_white.png");
        }
        else {
            this.setIconUrl("../resources/images/queen_black.png");
        }
    }


    @Override
    public List<Position> compareBoardWithPossibleMoves(Map<String, Piece> pieceMap) {
        List<Position> possibleMoves = new ArrayList<>(this.getPossibleMoves());
        AtomicBoolean blocked = new AtomicBoolean(false);

        // Check right forward move
        this.getPossibleMoves().stream()
                .filter(position -> position.getX() > this.getPosition().getX() && position.getY() > this.getPosition().getY())
                .filter(position -> isMovable(pieceMap, position, blocked))
                .forEach(move -> possibleMoves.remove(move));

        // Check left forward move
        blocked.set(false);
        this.getPossibleMoves().stream()
                .filter(position -> position.getX() < this.getPosition().getX() && position.getY() > this.getPosition().getY())
                .filter(position -> isMovable(pieceMap, position, blocked))
                .forEach(move -> possibleMoves.remove(move));

        // Check right backward move
        blocked.set(false);
        this.getPossibleMoves().stream()
                .filter(position -> position.getX() > this.getPosition().getX() && position.getY() < this.getPosition().getY())
                .filter(position -> isMovable(pieceMap, position, blocked))
                .forEach(move -> possibleMoves.remove(move));

        // Check left backward move
        blocked.set(false);
        this.getPossibleMoves().stream()
                .filter(position -> position.getX() < this.getPosition().getX() && position.getY() < this.getPosition().getY())
                .filter(position -> isMovable(pieceMap, position, blocked))
                .forEach(move -> possibleMoves.remove(move));


        // Check right move
        this.getPossibleMoves().stream()
                .filter(position -> position.getX() > this.getPosition().getX() && position.getY() == this.getPosition().getY())
                .filter(position -> isMovable(pieceMap, position, blocked))
                .forEach(move -> possibleMoves.remove(move));

        // Check forward move
        blocked.set(false);
        this.getPossibleMoves().stream()
                .filter(position -> position.getY() > this.getPosition().getY() && position.getX() == this.getPosition().getX())
                .filter(position -> isMovable(pieceMap, position, blocked))
                .forEach(move -> possibleMoves.remove(move));

        // Check left move
        blocked.set(false);
        this.getPossibleMoves().stream()
                .filter(position -> position.getX() < this.getPosition().getX() && position.getY() == this.getPosition().getY())
                .filter(position -> isMovable(pieceMap, position, blocked))
                .forEach(move -> possibleMoves.remove(move));

        // Check backward move
        blocked.set(false);
        this.getPossibleMoves().stream()
                .filter(position -> position.getY() < this.getPosition().getY() && position.getX() == this.getPosition().getX())
                .filter(position -> isMovable(pieceMap, position, blocked))
                .forEach(move -> possibleMoves.remove(move));

        return possibleMoves;
    }

    private boolean isMovable(Map<String, Piece> pieceMap, Position position, AtomicBoolean isBlocked) {
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

    @Override
    public Piece move(String key) {
        if (this.isFirstMove()) {
            this.setFirstMove(false);
        }
        this.getPosition().setX(Character.digit(key.charAt(0), 10))
                .setY(Character.digit(key.charAt(1), 10));

        this.setPossibleMoves();
        return this;
    }
}
