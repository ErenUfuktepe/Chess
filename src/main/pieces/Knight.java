package main.pieces;

import main.enums.Color;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color);
    }
}

// 7 x x x x x x x x
// 6 x x x x x x x x
// 5 x x K x K x x x
// 4 x K x x x K x x
// 3 x x x K x x x x
// 2 x K x x x K x x
// 1 x x K x K x x x
// 0 x x x x x x x x
// # 0 1 2 3 4 5 6 7


//(+1 | -1, -2 +2 -1 +1 )
//(3,3) -> (2,1),
//        (2,5),
//        (1,2),
//        (1,4),
//        (4,1),
//        (4,5),
//        (5,2),
//        (5,4)