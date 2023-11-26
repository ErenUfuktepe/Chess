package main.pieces;

import main.Position;
import main.enums.Color;
import main.moves.MoveBackward;
import main.moves.MoveForward;
import main.moves.MoveLeft;
import main.moves.MoveRight;

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
    public List<Position> compareBoardWithPossibleMoves(Map<String, Piece> pieceMap) {
        List<Position> possibleMoves = new ArrayList<>(this.getPossibleMoves());
        AtomicBoolean blocked = new AtomicBoolean(false);

        // Check right move
        this.getPossibleMoves().stream()
                .filter(position -> position.getX() > this.getPosition().getX() && position.getY() == this.getPosition().getY())
                .filter(position -> this.isMovable(pieceMap, position, blocked))
                .forEach(move -> possibleMoves.remove(move));

        // Check forward move
        blocked.set(false);
        this.getPossibleMoves().stream()
                .filter(position -> position.getY() > this.getPosition().getY() && position.getX() == this.getPosition().getX())
                .filter(position -> this.isMovable(pieceMap, position, blocked))
                .forEach(move -> possibleMoves.remove(move));

        // Check left move
        blocked.set(false);
        this.getPossibleMoves().stream()
                .filter(position -> position.getX() < this.getPosition().getX() && position.getY() == this.getPosition().getY())
                .filter(position -> this.isMovable(pieceMap, position, blocked))
                .forEach(move -> possibleMoves.remove(move));

        // Check backward move
        blocked.set(false);
        this.getPossibleMoves().stream()
                .filter(position -> position.getY() < this.getPosition().getY() && position.getX() == this.getPosition().getX())
                .filter(position -> this.isMovable(pieceMap, position, blocked))
                .forEach(move -> possibleMoves.remove(move));

        return possibleMoves;
    }



    @Override
    public Piece move(String key) {
        if (this.isFirstMove()) {
            this.setFirstMove(false);
        }
        this.getPosition().setX(Character.digit(key.charAt(0), 10))
                .setY(Character.digit(key.charAt(1), 10));

        return this;
    }

    @Override
    public void checkConditionalMoves() {
        // Can move two squares.
        if (this.isFirstMove()) {
            // Todo: Castling
        }
    }

}
