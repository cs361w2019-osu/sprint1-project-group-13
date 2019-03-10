package cs361.battleships.models.ships;

import cs361.battleships.models.Square;

import java.util.ArrayList;
import java.util.List;

public class Submarine extends Ship {

    public Submarine() {}

    public Submarine(Square origin, boolean vertical, boolean submerged) {
        this.origin = origin;
        this.vertical = vertical;
        this.submerged = submerged;
        //this.submerged = ???;
    }

    public List<Square> squares() {
        var list = new ArrayList<Square>();
        list.add(new Square(origin.col, origin.row));
        if (vertical) {
            list.add(new Square(origin.col, origin.row + 1));
            list.add(new Square(origin.col, origin.row + 2));
            list.add(new Square(origin.col, origin.row + 3));
            list.add(new Square(origin.col - 1, origin.row + 2));
        } else {
            list.add(new Square(origin.col + 1, origin.row));
            list.add(new Square(origin.col + 2, origin.row));
            list.add(new Square(origin.col + 3, origin.row));
            list.add(new Square(origin.col + 2, origin.row - 1));
        }
        return list;
    }

    public Square getCaptainsQuarters() {
        if (vertical) {
            return new Square(origin.col, origin.row + 3);
        } else {
            return new Square(origin.col + 3, origin.row);
        }
    }

    public boolean isCaptainsReinforced() {
        return true;
    }

}
