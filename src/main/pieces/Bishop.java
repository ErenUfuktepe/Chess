package main.pieces;

import main.enums.Color;
import main.enums.PieceType;

public class Bishop extends Piece {

    public Bishop(Color color){
        super(PieceType.BISHOP, color);
        if(this.getColor().equals(Color.WHITE)) {
            this.setIconUrl("../resources/images/bishop_white.png");
        }
        else {
            this.setIconUrl("../resources/images/bishop_black.png");
        }
    }
}
