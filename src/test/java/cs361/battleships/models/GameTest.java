package cs361.battleships.models;

import cs361.battleships.models.ships.Destroyer;
import cs361.battleships.models.ships.Minesweeper;
import cs361.battleships.models.ships.Ship;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class GameTest {

    @Test
    public void testPlaceTurns() {
        var game = new Game();
        assertEquals(0, game.playersBoard.ships.size());
        assertEquals(0, game.opponentsBoard.ships.size());

        Ship ally = new Minesweeper(new Square(0, 0), false);
        Ship enemy = new Minesweeper(null, false);
        game.placeShip(ally, enemy);
        assertEquals(1, game.playersBoard.ships.size());
        assertEquals(1, game.opponentsBoard.ships.size());

        ally = new Destroyer(new Square(0, 1), false);
        enemy = new Destroyer(null, false);
        game.placeShip(ally, enemy);
        assertEquals(2, game.playersBoard.ships.size());
        assertEquals(2, game.opponentsBoard.ships.size());
    }

    @Test
    public void testAttackTurns() {
        Game game = new Game();
        assertEquals(0, game.playersBoard.attacks.size());
        assertEquals(0, game.opponentsBoard.attacks.size());

        game.attack((new Square(0, 0)));
        assertEquals(1, game.playersBoard.attacks.size());
        assertEquals(1, game.opponentsBoard.attacks.size());
    }

}
