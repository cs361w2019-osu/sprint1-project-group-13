package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class SquareTest {

    @Test
    public void testWithinBounds() {
        var sq = new Square(9, 3);
        assert(sq.isAllowed());
    }

    @Test
    public void testOutOfBounds() {
        var sq = new Square(10, 3);
        assertFalse(sq.isAllowed());
    }

}
