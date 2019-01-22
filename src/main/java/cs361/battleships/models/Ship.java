package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares = new ArrayList<>();

	@JsonProperty private int size;

	public Ship(String shipType) {
		if("MINESWEEPER".equals(shipType)) size = 2;
		else if("DESTROYER".equals(shipType)) size = 3;
		else if("BATTLESHIP".equals(shipType)) size = 4;
		else throw new IllegalArgumentException("shipType");

	}

	public Ship(int size) {
		this.size = size;
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

	public boolean setLocation(int x, char y, boolean isVertical) {
		if(isVertical){
			if(x < 1 || x > 11 - size || y < 'A' || y > 'J') return false;
		}

		else {
			if(x < 1 || x > 10 || y < 'A' || y > 'K' - size) return false;
		}

		for(var i = 0; i < size; i++){

			Square square = new Square(isVertical ? x + i : x, isVertical ? y : (char)(y + i));
			occupiedSquares.add(square);
		}
		return true;

	}

	public Ship dup() {
		return new Ship(size);
	}
}
