package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares = new ArrayList<>();

	@JsonProperty private String kind;

	public Ship(String shipType) {
		kind = shipType;
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

	public boolean setLocation(int x, char y) {
		return false;
	}

	public Ship dup() {
		return new Ship(kind);
	}
}
