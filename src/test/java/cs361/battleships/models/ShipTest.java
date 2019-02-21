package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class ShipTest {

    @Test
    public void testMinesweeper() {
        var ship = new Ship(2, new Square(0, 0), false);
        assert(ship.getCaptainsQuarters().equals(new Square(0, 0)));
    }

    @Test
    public void testDestroyer() {
        var ship = new Ship(3, new Square(0, 0), false);
        assert(ship.getCaptainsQuarters().equals(new Square(0, 1)));

    }

    @Test
    public void testBattleship() {
        var ship = new Ship(4, new Square(0, 0), false);
        assert(ship.getCaptainsQuarters().equals(new Square(0, 2)));
    }

    @Test
    public void testBattleshipVertical() {
        var ship = new Ship(4, new Square(0, 0), true);
        assert(ship.getCaptainsQuarters().equals(new Square(2, 0)));
    }

    @Test
    public void testReinforced() {
        var minesweeper = new Ship(2, new Square(0, 0), true);
        var destroyer = new Ship(3, new Square(0, 0), true);
        var battleship = new Ship(4, new Square(0, 0), true);
        assertFalse(minesweeper.isCaptainsReinforced());
        assert(destroyer.isCaptainsReinforced());
        assert(battleship.isCaptainsReinforced());
    }

}
