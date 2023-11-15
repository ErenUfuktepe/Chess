package main.pieces;

import main.enums.Color;
import main.enums.PieceType;

public class Knight extends Piece {

    public Knight(Color color) {
        super(PieceType.KNIGHT, color);
        if(this.getColor().equals(Color.WHITE)) {
            this.setIconUrl("../resources/images/knight_white.png");
        }
        else {
            this.setIconUrl("../resources/images/knight_black.png");
        }
    }
}