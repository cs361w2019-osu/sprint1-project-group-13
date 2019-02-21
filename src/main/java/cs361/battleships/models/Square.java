package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Square {

	@JsonProperty int x;
	@JsonProperty int y;

	@SuppressWarnings("unused")
	Square() { }

	Square(int x, int y) {
		this.x = x;
		this.y = y;
	}

	boolean isAllowed() {
		return (x >= 0 && x <= 9 && y >= 0 && y <= 9);
	}

	boolean equals(Square sq) {
		return sq.x == x && sq.y == y;
	}

}
