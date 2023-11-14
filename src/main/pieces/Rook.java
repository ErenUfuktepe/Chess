package main.pieces;

import main.Position;
import main.Rule;
import main.enums.Color;
import main.enums.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(Color color) {
        super(PieceType.ROOK, color);
    }
}
