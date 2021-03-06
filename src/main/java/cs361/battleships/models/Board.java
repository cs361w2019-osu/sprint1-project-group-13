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
    @JsonProperty List<Square> lasers = new ArrayList<>();
    @JsonProperty Boolean canSonar = false;
    @JsonProperty Boolean usingLaser = false;

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
    public boolean moveFleet(String direction){
        for (int existing = 0; existing < ships.size(); existing++) {
            if (direction == "North") {
                for ( var i : ships.get(existing).squares()){
                    ships.get(existing).squares().get(i).Square(ships.get(existing).squares().get(i).col, ships.get(existing).squares().get(i).row + 1);
                }
            } else if (direction == "South") {
                for ( var i : ships.get(existing).squares()){
                    ships.get(existing).squares().get(i).Square(ships.get(existing).squares().get(i).col, ships.get(existing).squares().get(i).row - 1);
                }
            } else if (direction == "East") {
                for ( var i : ships.get(existing).squares()){
                    ships.get(existing).squares().get(i).Square(ships.get(existing).squares().get(i).col + 1, ships.get(existing).squares().get(i).row);
                }
            } else if (direction == "West") {
                for ( var i : ships.get(existing).squares()){
                    ships.get(existing).squares().get(i).Square(ships.get(existing).squares().get(i).col - 1, ships.get(existing).squares().get(i).row);
                }
            }
        }
        return false;
    }

    /** Add attack to board, if valid. */
    public boolean attack(Square sq) {

        if (!canAttack(sq)) return false;

        // If Any ship is sunk (ships sunk >= 1), then we add attacks to lasers
        // Else, we just attack as normal.
        if (usingLaser) {
            lasers.add(sq);
        } else {
            attacks.add(sq);
        }

        // Set any ships sunk if they are, to make the client simpler
        for (var ship : ships) if (isSunk(ship)) ship.sunk = true;

        // Check for newly sunk ships
        usingLaser = isAnySunk();

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

    private int attacksAt(Square sq, boolean submerged) {
        int count = 0;
        if (submerged) {
            for (var past : lasers) if (past.equals(sq)) count++;
        } else {
            for (var past : attacks) if (past.equals(sq)) count++;
            for (var past : lasers) if (past.equals(sq)) count++;
        }
        return count;
    }

    private boolean isSunk(Ship ship) {
        // Captains sunk
        int capHitsNeeded = ship.isCaptainsReinforced() ? 2 : 1;
        int capHitsActual = attacksAt(ship.getCaptainsQuarters(), ship.submerged);
        if (capHitsActual >= capHitsNeeded) return true;
        // Regular sunk
        for (var sq : ship.squares()) if (attacksAt(sq, ship.submerged) == 0) return false;
        return true;
    }

    private boolean canAttack(Square target) {
        var allowed = 1;

        for (var ship : ships) {
            if (usingLaser || !ship.submerged) {
                if (!isSunk(ship)) {
                    if (ship.getCaptainsQuarters().equals(target)) {
                        if (ship.isCaptainsReinforced()) {
                            allowed = 2;
                        }
                    }
                }
            }
        }

        return attacksAt(target, usingLaser) < allowed;
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

}
