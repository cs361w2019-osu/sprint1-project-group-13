package cs361.battleships.models.ships;

import cs361.battleships.models.Square;

import java.util.ArrayList;
import java.util.List;

public class Minesweeper extends Ship {

    public Minesweeper() {}

    public Minesweeper(Square origin, boolean vertical) {
        this.origin = origin;
        this.vertical = vertical;
        this.submerged = false;
    }

    public List<Square> squares() {
        var list = new ArrayList<Square>();
        list.add(new Square(origin.col, origin.row));
        if (vertical) {
            list.add(new Square(origin.col, origin.row + 1));
        } else {
            list.add(new Square(origin.col + 1, origin.row));
        }
        return list;
    }

    public Square getCaptainsQuarters() {
        return new Square(origin.col, origin.row);
    }

    public boolean isCaptainsReinforced() {
        return false;
    }

}
