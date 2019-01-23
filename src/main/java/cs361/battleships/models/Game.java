package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static cs361.battleships.models.AttackStatus.*;

public class Game {

    @JsonProperty protected Board playersBoard = new Board();
    @JsonProperty protected Board opponentsBoard = new Board();

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
        boolean successful = playersBoard.placeShip(ship, x, y, isVertical);
        if (!successful)
            return false;

        boolean opponentPlacedSuccessfully;
        do {
            // AI places random ships, so it might try and place overlapping ships
            // let it try until it gets it right
            opponentPlacedSuccessfully = opponentsBoard.placeShip(ship.dup(), randRow(), randCol(), randVertical());
        } while (!opponentPlacedSuccessfully);

        return true;
    }

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean attack(int x, char  y) {
        Result playerAttack = opponentsBoard.attack(x, y);
        if (playerAttack.getResult() == INVALID) {
            return false;
        }

        Result opponentAttackResult;
        do {
            // AI does random attacks, so it might attack the same spot twice
            // let it try until it gets it right
            opponentAttackResult = playersBoard.attack(randRow(), randCol());
        } while(opponentAttackResult.getResult() == INVALID);

        return true;
    }

    private char randCol() {
        Random r = new Random(); //makes random work
        int myRand = r.nextInt(10); //grabs a value from 0-9
        char myAlph;
        if(myRand == 0) { //Turns the random variable into its col char counterpart
            myAlph = 'A';
        }
        else if(myRand == 1) {
            myAlph = 'B';
        }
        else if(myRand == 2) {
            myAlph = 'C';
        }
        else if(myRand == 3) {
            myAlph = 'D';
        }
        else if(myRand == 4) {
            myAlph = 'E';
        }
        else if(myRand == 5) {
            myAlph = 'F';
        }
        else if(myRand == 6) {
            myAlph = 'G';
        }
        else if(myRand == 7) {
            myAlph = 'H';
        }
        else if(myRand == 8) {
            myAlph = 'I';
        }
        else {
            myAlph = 'J';
        }

        //Should be good now
        return myAlph; //returns the random char from a-j
    }

    private int randRow() {
        Random r = new Random(); //Makes a random string of values
        return r.nextInt(10); // grabs a number from 0-9
        //Should work now
    }

    private boolean randVertical() {
        Random r = new Random();  //Makes rand work
        int tempBool = r.nextInt(2); //generates a number from 0-1
        if (tempBool == 1) { // If the number is one returns true
            return true;
        } else { //otherwise returns false
            return false;
        }
    }
}
