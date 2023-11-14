package main.pieces;

import main.Position;
import main.Rule;
import main.enums.Color;
import main.enums.PieceType;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(Color color) {
        super(PieceType.KING, color);
    }
}
