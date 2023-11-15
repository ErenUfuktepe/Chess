package main.pieces;

import main.enums.Color;
import main.enums.PieceType;

public class Pawn extends Piece {
    private boolean isFirstMove = true;

    public Pawn(Color color){
        super(PieceType.PAWN, color);
        if(this.getColor().equals(Color.WHITE)) {
            this.setIconUrl("../resources/images/pawn_white.png");
        }
        else {
            this.setIconUrl("../resources/images/pawn_black.png");
        }
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public Pawn setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
        return this;
    }

    @Override
    public boolean canMoveBackwards() {
        return false;
    }
}
