package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Board {

	@JsonProperty List<Ship> ships = new ArrayList<>();
	@JsonProperty List<Square> attacks = new ArrayList<>();
	@JsonProperty List<Square> sonars = new ArrayList<>();

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
		return true;
	}

	/** Add sonar pulse to board, if valid. */
	public boolean sonar(Square sq) {

		// TODO disallow if no ships have sank
		// TODO disallow sonar on same square
		// TODO limit two
		// TODO add sonar to list

		return false;
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

}
