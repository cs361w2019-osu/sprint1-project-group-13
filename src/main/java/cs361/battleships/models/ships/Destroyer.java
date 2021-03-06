package cs361.battleships.models.ships;

import cs361.battleships.models.Square;

import java.util.ArrayList;
import java.util.List;

public class Destroyer extends Ship {

    public Destroyer() {}

    public Destroyer(Square origin, boolean vertical) {
        this.origin = origin;
        this.vertical = vertical;
        this.submerged = false;
    }

    public List<Square> squares() {
        var list = new ArrayList<Square>();
        list.add(new Square(origin.col, origin.row));
        if (vertical) {
            list.add(new Square(origin.col, origin.row + 1));
            list.add(new Square(origin.col, origin.row + 2));
        } else {
            list.add(new Square(origin.col + 1, origin.row));
            list.add(new Square(origin.col + 2, origin.row));
        }
        return list;
    }

    public Square getCaptainsQuarters() {
        if (vertical) {
            return new Square(origin.col, origin.row + 1);
        } else {
            return new Square(origin.col + 1, origin.row);
        }
    }

    public boolean isCaptainsReinforced() {
        return true;
    }

}
