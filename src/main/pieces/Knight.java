package main.pieces;

import main.enums.Color;
import main.moves.MoveL;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color);
        this.addMovable(new MoveL());
    }
}