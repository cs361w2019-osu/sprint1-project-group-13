package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Board {

	@JsonProperty List<Ship> ships = new ArrayList<>();
	@JsonProperty List<Square> attacks = new ArrayList<>();
	@JsonProperty List<Square> sonars = new ArrayList<>();
	@JsonProperty Boolean canSonar;

	/** Add a ship to the board, if it won't collide with an edge or another ship. */
	public boolean placeShip(Ship ship) {
		// Build a list of taken squares
		var occupied = new ArrayList<Square>();
		for (var existing : ships) occupied.addAll(existing.getSquares());

		// Reject if any of ship's squares are out of bounds
		List<Square> proposed = ship.getSquares();
		for (var sq : proposed) {
			if (!sq.isAllowed()) {
				return false;
			}
		}

		// Reject if any of ship's squares are already occupied
		for (var taken : occupied) {
			for (var sq : proposed) {
				if (taken.equals(sq)) {
					return false;
				}
			}
		}

		ships.add(ship);
		return true;
	}

	/** Add attack to board, if valid. */
	public boolean attack(Square sq) {
		// Reject any attack overlapping with a previous one
		if (attacksAt(sq) > 0) return false;

		// TODO allow captains quarters double hit

		attacks.add(sq);

		// Set any ships sunk if they are, to make the client simpler
		for (var ship : ships) if (isSunk(ship)) ship.markSunk();

		// true means the server should return the new game state
		return true;
	}

	/** Add sonar pulse to board, if valid. */
	public boolean sonar(Square sq) {

		// TODO disallow if no ships have sank
		if(ships.isEmpty()){
			canSonar = false;
			return canSonar;
		}

		if(!isAnySunk()) {
			canSonar = false;
			return canSonar;
		}

		// TODO disallow sonar on same square
		if(isSameSonar(sq)){
			canSonar = false;
			return canSonar;
		}

		// TODO limit two
		if(!lessThanTwoSonars()){
			canSonar = false;
			return canSonar;
		}


		// TODO add sonar to list
		sonars.add(sq);

		canSonar = true;
		return canSonar;
	}

	private int attacksAt(Square sq) {
		int count = 0;
		for (var past : attacks) if (past.equals(sq)) count++;
		return count;
	}

	private boolean isSunk(Ship ship) {
		for (var sq : ship.getSquares()) if (attacksAt(sq) == 0) return false;
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

}
