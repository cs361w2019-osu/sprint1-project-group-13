package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	private String kind;

	public Ship() {
		occupiedSquares = new ArrayList<>();
	}

	public Ship(String shipType) {
		this.kind = shipType;
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

}
