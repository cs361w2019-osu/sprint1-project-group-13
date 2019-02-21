package cs361.battleships.models;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty int size;
	@JsonProperty Square origin;
	@JsonProperty boolean vertical;
	@JsonProperty boolean sunk;

	@SuppressWarnings("unused")
	Ship() {}

	public Ship(int size, Square origin, boolean vertical) {
		this.size = size;
		this.origin = origin;
		this.vertical = vertical;
	}

	@JsonIgnore
	public List<Square> getSquares() {
		var list = new ArrayList<Square>();
		for (int i = 0; i < size; i++) {
			if (vertical) {
				list.add(new Square(origin.x, origin.y + i));
			} else {
				list.add(new Square(origin.x + i, origin.y));
			}
		}
		return list;
	}

	@JsonIgnore
	public Square getCaptainsQuarters() {
		if (vertical) {
			return new Square(origin.x, origin.y + size - 2);
		} else {
			return new Square(origin.x + size - 2, origin.y);
		}
	}

	@JsonIgnore
	public boolean isCaptainsReinforced() {
		return size > 2;
	}

	@JsonIgnore
	public void markSunk() {
		this.sunk = true;
	}

}
