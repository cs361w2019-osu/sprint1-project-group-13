package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class ShipTest {
    @Test
    public void testSetLocation() {
        Ship ship = new Ship("MINESWEEPER");
        assert(ship.setLocation(4,'B', false));
    }

    @Test
    public void testInvalid() {
        Ship ship = new Ship("DESTROYER");
        assertFalse(ship.setLocation(20,'O', false));
    }
}
