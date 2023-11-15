package main.pieces;

import main.enums.Color;
import main.enums.PieceType;

public class Rook extends Piece {

    public Rook(Color color) {
        super(PieceType.ROOK, color);
        if(this.getColor().equals(Color.WHITE)) {
            this.setIconUrl("../resources/images/rook_white.png");
        }
        else {
            this.setIconUrl("../resources/images/rook_black.png");
        }
    }
}
