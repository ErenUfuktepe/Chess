package main;

import main.enums.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    private Position position = new Position();
    private List<Position> possibleMoves = new ArrayList<>();
    private Color color;

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
}
