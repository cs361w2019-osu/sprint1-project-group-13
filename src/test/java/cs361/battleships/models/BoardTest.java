package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void testPlacementAdjacentToEdge() {
        Board board = new Board();
        assert(board.placeShip(new Ship(2, new Square(3,9), false)));
    }

    @Test
    public void testPlacementOverEdge() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship(2, new Square(3,9), true)));
    }

    @Test
    public void testPlacementGoodCorner() {
        Board board = new Board();
        assert(board.placeShip(new Ship(2, new Square(0,9), false)));
    }

    @Test
    public void testPlacementBadCorner() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship(2, new Square(9,9), false)));
    }

    @Test
    public void testPlacementOverlapping() {
        Board board = new Board();
        assert(board.placeShip(new Ship(3, new Square(0,1), false)));
        assertFalse(board.placeShip(new Ship(3, new Square(0,0), true)));
        assertFalse(board.placeShip(new Ship(3, new Square(1,0), true)));
        assertFalse(board.placeShip(new Ship(3, new Square(2,0), true)));
        assert(board.placeShip(new Ship(3, new Square(3,0), false)));
    }

    /*@Test  OLD TEST
    public void testAttackRepeat() {
        Board board = new Board();
        board.placeShip(new Ship(3,new Square(0,0),true));
        board.attack(new Square(0, 1));
        board.attack(new Square(0, 1));
        assertTrue(board.ships.get(0).sunk);
    }
*/
    @Test
    public void testAttackRepeatNotCap(){
        Board board = new Board();
        board.attack(new Square(0, 0));
        assertFalse(board.attack(new Square(0,0)));
    }

    // TODO captains quarters attack allowed
    // TODO sonar allowed and disallowed

}
