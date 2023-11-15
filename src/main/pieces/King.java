package main.pieces;

import main.enums.Color;
import main.enums.PieceType;

public class King extends Piece {

    public King(Color color) {
        super(PieceType.KING, color);
        if(this.getColor().equals(Color.WHITE)) {
            this.setIconUrl("../resources/images/king_white.png");
        }
        else {
            this.setIconUrl("../resources/images/king_black.png");
        }
    }
}
