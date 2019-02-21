package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Random;

public class Game {

    @JsonProperty Board playersBoard = new Board();
    @JsonProperty Board opponentsBoard = new Board();

    /** Player places a ship. */
    public boolean placeShip(int size, Square sq, boolean vertical) {

        // Player ship placement either works or not
        Ship ally = new Ship(size, sq, vertical);
        if (!playersBoard.placeShip(ally)) return false;

        // AI places random ships, so it might try and place overlapping ships
        // Loop and try until it gets it right
        boolean enemyPlaced;
        do {
            Ship enemy = new Ship(size, randomSquare(), randVertical());
            enemyPlaced = opponentsBoard.placeShip(enemy);
        } while (!enemyPlaced);

        return true;
    }

    /** Player attacks the opponent. */
    public boolean attack(Square sq) {

        // Player attack either works or not
        // Return false if placement was bad
        if (!opponentsBoard.attack(sq)) return false;

        // AI does random attacks, so it might attack the same spot twice
        // let it try until it gets it right
        boolean enemyAttacked;
        do {
            enemyAttacked = playersBoard.attack(randomSquare());
        } while (!enemyAttacked);

        return true;
    }

    /** Player reveals area. */
    public boolean sonar(Square sq) {
        return opponentsBoard.sonar(sq);
    }

    private Square randomSquare() {
        Random r = new Random(); //Makes a random string of values
        return new Square(r.nextInt(10), r.nextInt(10)); // grabs numbers from 0-9
    }

    private boolean randVertical() {
        Random r = new Random();  //Makes rand work
        int tempBool = r.nextInt(2); //generates a number from 0-1
        return tempBool == 1;
    }
}
