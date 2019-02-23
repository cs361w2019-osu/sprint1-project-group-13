package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class GameTest {

    @Test
    public void testPlaceTurns() {
        Game game = new Game();
        assert(game.opponentsBoard.ships.size() == 0);
        game.placeShip(2, new Square(0, 0), false);
        assert(game.opponentsBoard.ships.size() == 1);
        game.placeShip(3, new Square(0, 1), false);
        assert(game.opponentsBoard.ships.size() == 2);
    }
    @Test
    public void testSimpleAttack() {
        Game game = new Game();
        game.placeShip(2, new Square(0, 0), false);
        assert(game.attack((new Square(0, 0))));
    }
    // TODO attack turns

}
