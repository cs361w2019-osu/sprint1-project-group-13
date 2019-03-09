package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Square {

	@JsonProperty public int col;
	@JsonProperty public int row;

	public Square() { }

	public Square(int col, int row) {
		this.col = col;
		this.row = row;
	}

	boolean isAllowed() {
		return (col >= 0 && col <= 9 && row >= 0 && row <= 9);
	}

	boolean equals(Square sq) {
		return sq.col == col && sq.row == row;
	}

}
