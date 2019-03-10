package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs361.battleships.models.ships.Battleship;
import cs361.battleships.models.ships.Destroyer;
import cs361.battleships.models.ships.Minesweeper;
import cs361.battleships.models.ships.Ship;

import java.util.ArrayList;
import java.util.List;

public class Board {

    @JsonProperty List<Ship> ships = new ArrayList<>();
    @JsonProperty List<Square> attacks = new ArrayList<>();
    @JsonProperty List<Square> sonars = new ArrayList<>();
    @JsonProperty Boolean canSonar = false;

    /** Add a ship to the board, if it won't collide with an edge or another ship. */
    public boolean placeShip(Ship ship) {

        // Build a list of taken squares
        var occupied = new ArrayList<Square>();
        for (var existing : ships) occupied.addAll(existing.squares());

        // Reject if any of ship's squares are out of bounds
        List<Square> proposed = ship.squares();
        for (var sq : proposed) {
            if (!sq.isAllowed()) {
                return false;
            }
        }

        // Reject if any of ship's squares are already occupied
        // TODO allow placement of submarine if submerged.
        if(!(ship.submerged)) {
            for (var taken : occupied) {
                for (var sq : proposed) {
                    if (taken.equals(sq)) {
                        return false;
                    }
                }
            }
        }
        ships.add(ship);
        return true;
    }

    /** Add attack to board, if valid. */
    public boolean attack(Square sq) {
        // Reject any attack overlapping with a previous one
        if (attacksAt(sq) == hitsAllowed(sq)) return false;

        attacks.add(sq);

        // Set any ships sunk if they are, to make the client simpler
        for (var ship : ships) if (isSunk(ship)) ship.sunk = true;

        updateCanSonar();
        return true;
    }

    /** Add sonar pulse to board, if valid. */
    public boolean sonar(Square sq) {

        if (!canSonar) return false;

        // Disallow sonar on same square
        if(isSameSonar(sq)) return false;

        sonars.add(sq);
        updateCanSonar();
        return true;
    }

    public boolean moveFleet(int x, int y) {
        return false;
    }

    private void updateCanSonar() {
        canSonar = lessThanTwoSonars() && isAnySunk();
    }

    private int attacksAt(Square sq) {
        int count = 0;
        for (var past : attacks) if (past.equals(sq)) count++;
        return count;
    }

    private boolean isSunk(Ship ship) {
        if ((attacksAt(ship.getCaptainsQuarters()) > 0) && !ship.isCaptainsReinforced()) return true;
        else if (attacksAt(ship.getCaptainsQuarters()) > 1) return true;
        for (var sq : ship.squares()) if (attacksAt(sq) == 0) return false;
        return true;
    }

    private boolean isAnySunk(){
        for(Integer i = 0; i < ships.size(); i++){
            if(ships.get(i).sunk == true){
                return true;
            }
        }
        return false;
    }

    private boolean isSameSonar(Square sq){
        for(Integer i = 0; i < sonars.size(); i++){
            if(sonars.get(i).equals(sq)){
                return true;
            }
        }
        return false;
    }

    private boolean lessThanTwoSonars(){
        if(sonars.size() < 2) {
            return true;
        }
        return false;
    }

    private int hitsAllowed (Square sq) {
        for (var ship : ships) {
            if (sq.equals(ship.getCaptainsQuarters())) {
                if (ship.isCaptainsReinforced()) {
                    return 2;
                }
            }
        }
        return 1; // Miss and regular squares
    }

}
