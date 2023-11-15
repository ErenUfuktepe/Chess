package main.pieces;

import main.enums.Color;
import main.enums.PieceType;

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
}
