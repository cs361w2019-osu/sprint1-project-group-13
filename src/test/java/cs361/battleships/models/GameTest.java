package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class GameTest {

    @Test
    public void testTurn() {
        Game game = new Game();
        assert(game.opponentsBoard.getShips().size() == 0);
        game.placeShip(new Ship("MINESWEEPER"), 1, 'A', false);
        assert(game.opponentsBoard.getShips().size() == 1);
        game.placeShip(new Ship("DESTROYER"), 2, 'A', false);
        assert(game.opponentsBoard.getShips().size() == 2);
    }

}
