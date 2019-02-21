package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void testPlacementAdjacentToEdge() {
        Board board = new Board();
        assert(board.placeShip(new Ship(2, new Square(9,3), false)));
    }

    @Test
    public void testPlacementOverEdge() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship(2, new Square(9,3), true)));
    }

    @Test
    public void testPlacementGoodCorner() {
        Board board = new Board();
        assert(board.placeShip(new Ship(2, new Square(9,0), false)));
    }

    @Test
    public void testPlacementBadCorner() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship(2, new Square(9,9), false)));
    }

    @Test
    public void testPlacementOverlapping() {
        Board board = new Board();
        assert(board.placeShip(new Ship(3, new Square(1,0), false)));
        assertFalse(board.placeShip(new Ship(3, new Square(0,0), true)));
        assertFalse(board.placeShip(new Ship(3, new Square(0,1), true)));
        assertFalse(board.placeShip(new Ship(3, new Square(0,2), true)));
        assert(board.placeShip(new Ship(3, new Square(0,3), false)));
    }

    @Test
    public void testAttackRepeat() {
        Board board = new Board();
        board.attack(new Square(0, 0));
        assertFalse(board.attack(new Square(0, 0)));
        assert(board.attack(new Square(0, 1)));
    }

    // TODO captains quarters attack allowed
    // TODO sonar allowed and disallowed

}
