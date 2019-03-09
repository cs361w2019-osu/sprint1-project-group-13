package cs361.battleships.models;

import cs361.battleships.models.ships.Destroyer;
import cs361.battleships.models.ships.Minesweeper;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void testPlacementAdjacentToEdge() {
        var board = new Board();
        var ship = new Minesweeper(new Square(3,9), false);
        assertTrue(board.placeShip(ship));
    }

    @Test
    public void testPlacementOverEdge() {
        var board = new Board();
        var ship = new Minesweeper(new Square(3,9), true);
        assertFalse(board.placeShip(ship));
    }

    @Test
    public void testPlacementGoodCorner() {
        var board = new Board();
        var ship = new Minesweeper(new Square(0,9), false);
        assertTrue(board.placeShip(ship));
    }

    @Test
    public void testPlacementBadCorner() {
        var board = new Board();
        var ship = new Minesweeper(new Square(9,9), false);
        assertFalse(board.placeShip(ship));
    }

    @Test
    public void testPlacementOverlapping() {
        var board = new Board();
        var minesweeper = new Minesweeper(new Square(0,1), false);
        assertTrue(board.placeShip(minesweeper));

        var destroyer = new Minesweeper(new Square(0,0), true);
        destroyer.origin = new Square(0, 0);
        assertFalse(board.placeShip(destroyer));

        destroyer.origin = new Square(0, 1);
        assertFalse(board.placeShip(destroyer));

        destroyer.origin = new Square(0, 2);
        assertTrue(board.placeShip(destroyer));
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

    @Test
    public void captainTest(){
        var board = new Board();
        var minesweeper = new Minesweeper(new Square(0,0), false);
        var destroyer = new Destroyer(new Square(0,1), false);

        board.placeShip(minesweeper);
        assertTrue(board.attack(new Square(0,0)));
        assertFalse(board.attack(new Square(0,0)));

        board.placeShip(destroyer);
        assertTrue(board.attack(new Square(1,1)));
        assertTrue(board.attack(new Square(1,1)));
        assertFalse(board.attack(new Square(1,1)));
    }

    @Test
    public void testSimpleSonar() {
        var board = new Board();
        var minesweeper = new Minesweeper(new Square(5,4), false);
        board.placeShip(minesweeper);
        board.attack(new Square(5, 4));
        board.attack(new Square(6, 4));
        assertTrue(board.sonar(new Square(5, 5)));
    }

    @Test
    public void testSonarOnEmptyBoard() {
        Board board = new Board();
        assertFalse(board.sonar(new Square(5, 5)));
    }

    @Test
    public void testSonarNoSunkShips() {
        var board = new Board();
        var minesweeper = new Minesweeper(new Square(5,4), false);
        var destroyer = new Destroyer(new Square(0,3), true);

        board.placeShip(minesweeper);
        board.placeShip(destroyer);
        board.attack(new Square(0, 3));
        board.attack(new Square(0, 4));

        // last destroyer square not hit, sonar not available
        assertFalse(board.sonar(new Square(5, 5)));
    }

    @Test
    public void testSonarUseMoreThanTwo() {
        var board = new Board();
        var minesweeper = new Minesweeper(new Square(5,4), false);

        board.placeShip(minesweeper);
        board.attack(new Square(5, 4));
        board.attack(new Square(6, 4));

        assertTrue(board.sonar(new Square(5, 5)));
        assertTrue(board.sonar(new Square(5, 6)));
        assertFalse(board.sonar(new Square(5, 7)));
    }

    @Test
    public void testSonarSameSquare() {
        var board = new Board();
        var minesweeper = new Minesweeper(new Square(5,4), false);

        board.placeShip(minesweeper);
        board.attack(new Square(5, 4));
        board.attack(new Square(6, 4));

        assertTrue(board.sonar(new Square(5, 5)));
        assertFalse(board.sonar(new Square(5, 5)));
    }
}
