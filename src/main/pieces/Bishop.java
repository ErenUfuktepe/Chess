package main.pieces;

import main.Position;
import main.Rule;
import main.enums.Color;
import main.enums.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(Color color){
        super(PieceType.BISHOP, color);
    }
}
