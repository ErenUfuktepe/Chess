package main.pieces;

import main.Position;
import main.Rule;
import main.enums.Color;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece implements Rule {

    public Rook(Color color) {
        super(color);
    }

    @Override
    public List<Position> getPossiblePositions() {
        List<Position> possiblePositions = new ArrayList<>();

        // Checking X axis move - Right
        for (int index = this.getPosition().getX() + 1; index < 8; index++) {
            possiblePositions.add(new Position(index, this.getPosition().getY()));
        }
        // Checking X axis move - Left
        for (int index = this.getPosition().getX() - 1; index <= 0; index--) {
            possiblePositions.add(new Position(index, this.getPosition().getY()));
        }

        // Checking Y axis move - Top
        for (int index = this.getPosition().getY() + 1; index < 8; index++) {
            possiblePositions.add(new Position(this.getPosition().getX(), index));
        }
        // Checking Y axis move - Bottom
        for (int index = this.getPosition().getX() - 1; index <= 0; index--) {
            possiblePositions.add(new Position(this.getPosition().getX(), index));
        }

        return possiblePositions;
    }
}
