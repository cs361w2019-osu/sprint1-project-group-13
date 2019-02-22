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
        assert(board.placeShip(new Ship(3, new Square(0,3), false)));
        assertFalse(board.placeShip(new Ship(3, new Square(0,3), true)));
        assertFalse(board.placeShip(new Ship(3, new Square(1,3), true)));
        assertFalse(board.placeShip(new Ship(3, new Square(2,3), true)));

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

    @Test
    public void testSimpleSonar() {
        Board board = new Board();
        board.placeShip(new Ship(3, new Square(5,4), false));
        board.attack(new Square(5, 4));
        board.attack(new Square(6, 4));
        board.attack(new Square(7, 4));
        assert(board.sonar(new Square(5, 5)));
    }

    @Test
    public void testSonarOnEmptyBoard() {
        Board board = new Board();
        assertFalse(board.sonar(new Square(5, 5)));
    }

    @Test
    public void testSonarNoSunkShips() {
        Board board = new Board();
        board.placeShip(new Ship(3, new Square(5,4), false));
        board.placeShip(new Ship(3, new Square(0,3), true));
        board.attack(new Square(5, 4));
        board.attack(new Square(6, 4));
        assertFalse(board.sonar(new Square(5, 5)));
    }

    @Test
    public void testSonarUseMoreThanTwo() {
        Board board = new Board();
        board.placeShip(new Ship(3, new Square(5,4), false));
        board.attack(new Square(5, 4));
        board.attack(new Square(6, 4));
        board.attack(new Square(7, 4));

        assert(board.sonar(new Square(5, 5)));
        assert(board.sonar(new Square(5, 6)));
        assertFalse(board.sonar(new Square(5, 7)));
    }

    @Test
    public void testSonarSameSquare() {
        Board board = new Board();
        board.placeShip(new Ship(3, new Square(5,4), false));
        board.attack(new Square(5, 4));
        board.attack(new Square(6, 4));
        board.attack(new Square(7, 4));

        assert(board.sonar(new Square(5, 5)));
        assertFalse(board.sonar(new Square(5, 5)));
    }
}
