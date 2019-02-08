package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class BoardTest {

    @Test
    public void testPlacementOutOfBounds() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 11, 'C', true));
    }

    @Test
    public void testPlacementOverEdge() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 10, 'C', true));
    }

    @Test
    public void testPlacementAdjacentToEdge() {
        Board board = new Board();
        assert(board.placeShip(new Ship("MINESWEEPER"), 10, 'C', false));
    }

    @Test
    public void testPlacementGoodCorner() {
        Board board = new Board();
        assert(board.placeShip(new Ship("MINESWEEPER"), 9, 'A', true));
    }

    @Test
    public void testPlacementBadCorner() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 10, 'J', false));
    }

    @Test
    public void testPlacementOverlapping() {
        Board board = new Board();
        assert(board.placeShip(new Ship("DESTROYER"), 1, 'B', true));
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 3, 'A', false));
    }

    @Test
    public void testAttacksRecorded() {
        Board board = new Board();
        board.attack(1, 'A');
        board.attack(1, 'B');
        board.attack(1, 'C');
        assert(board.getAttacks().size() == 3);
        assert(board.getAttacks().get(0).getResult() == AttackStatus.MISS);
    }

    @Test
    public void testAttackHit() {
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 10, 'I', false);
        var result = board.attack(10, 'I');
        assert(result.getResult() == AttackStatus.HIT);
    }

    @Test
    public void testAttackMiss() {
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 10, 'I', false);
        var result = board.attack(10, 'H');
        assert(result.getResult() == AttackStatus.MISS);
    }

    @Test
    public void testAttackRepeat() {
        Board board = new Board();
        board.attack(10, 'H');
        var result = board.attack(10, 'H');
        assert(result.getResult() == AttackStatus.INVALID);
    }

    @Test
    public void testAttackSunk() {
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 10, 'A', false);
        board.placeShip(new Ship("DESTROYER"), 10, 'C', false);
        var result = board.attack(10, 'A');
        assert(result.getResult() == AttackStatus.HIT);
        result = board.attack(10, 'B');
        assert(result.getResult() == AttackStatus.SUNK);
    }

    @Test
    public void testAttackSurrender() {
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 10, 'A', false);
        var result = board.attack(10, 'A');
        assert(result.getResult() == AttackStatus.HIT);
        result = board.attack(10, 'B');
        assert(result.getResult() == AttackStatus.SURRENDER);
    }

}
