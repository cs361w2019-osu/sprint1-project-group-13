package cs361.battleships.models;

import cs361.battleships.models.ships.Battleship;
import cs361.battleships.models.ships.Destroyer;
import cs361.battleships.models.ships.Minesweeper;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class ShipTest {

    @Test
    public void testMinesweeper() {
        var ship = new Minesweeper(new Square(0, 0), false);
        assertTrue(ship.getCaptainsQuarters().equals(new Square(0, 0)));
    }

    @Test
    public void testDestroyer() {
        var ship = new Destroyer(new Square(0, 0), false);
        assertTrue(ship.getCaptainsQuarters().equals(new Square(1, 0)));

    }

    @Test
    public void testBattleship() {
        var ship = new Battleship(new Square(0, 0), false);
        assertTrue(ship.getCaptainsQuarters().equals(new Square(2, 0)));
    }

    @Test
    public void testBattleshipVertical() {
        var ship = new Battleship(new Square(0, 0), true);
        assertTrue(ship.getCaptainsQuarters().equals(new Square(0, 2)));
    }

    @Test
    public void testCaptains() {
        var minesweeper = new Minesweeper(new Square(0, 0), false);
        assertTrue(minesweeper.getCaptainsQuarters().equals(new Square(0, 0)));

        var destroyer = new Destroyer(new Square(0, 0), false);
        assertTrue(destroyer.getCaptainsQuarters().equals(new Square(1, 0)));

        var battleship = new Battleship(new Square(0, 0), false);
        assertTrue(battleship.getCaptainsQuarters().equals(new Square(2, 0)));
    }

    @Test
    public void testCaptainsVertical() {
        var minesweeper = new Minesweeper(new Square(0, 0), true);
        assertTrue(minesweeper.getCaptainsQuarters().equals(new Square(0, 0)));

        var destroyer = new Destroyer(new Square(0, 0), true);
        assertTrue(destroyer.getCaptainsQuarters().equals(new Square(0, 1)));

        var battleship = new Battleship(new Square(0, 0), true);
        assertTrue(battleship.getCaptainsQuarters().equals(new Square(0, 2)));
    }

    @Test
    public void testReinforced() {
        var minesweeper = new Minesweeper(new Square(0, 0), true);
        assertFalse(minesweeper.isCaptainsReinforced());

        var destroyer = new Destroyer(new Square(0, 0), true);
        assertTrue(destroyer.isCaptainsReinforced());

        var battleship = new Battleship(new Square(0, 0), true);
        assertTrue(battleship.isCaptainsReinforced());
    }

}
