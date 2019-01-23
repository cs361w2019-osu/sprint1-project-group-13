package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Board {

	@JsonProperty private List<Ship> ships = new ArrayList<>();
	@JsonProperty private List<Result> attacks = new ArrayList<>();

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() { }

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		// TODO
		return false;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {

		var att = new Result();
		att.setLocation(new Square(x, y));
		att.setResult(AttackStatus.MISS);

		if (x < 1 || x > 10 || y < 'A' || y > 'J') {
			att.setResult(AttackStatus.INVALID);
			return att;
		}

		findShip:
		for (Ship ship : ships) {
			for (Square sq : ship.getOccupiedSquares()) {
				if (sq.getRow() == x && sq.getColumn() == y) {

					att.setShip(ship);
					att.setResult(AttackStatus.HIT);

					if (itWillSink(ship, x, y)) {
						att.setResult(AttackStatus.SUNK);

						if (itWillSinkAll(x, y)) {
							att.setResult(AttackStatus.SURRENDER);
						}
					}

					break findShip;
				}
			}
		}

		return att;
	}

	private boolean isHit(int x, char y) {
		for (Result past : attacks) {
			if (past.getLocation().getRow() == x && past.getLocation().getColumn() == y) {
				switch (past.getResult()) {
					case HIT:
					case SUNK:
					case SURRENDER:
						return true;
				}
			}
		}
		return false;
	}

	private boolean itWillSink(Ship ship, int x, char y) {
		for (Square sq : ship.getOccupiedSquares()) {
			if (sq.getRow() == x && sq.getColumn() == y) {
				// yes, look for another un-hit square on the ship
			} else if (isHit(sq.getRow(), sq.getColumn())) {
				// yes, look for another un-hit square on the ship
			} else {
				return false;
			}
		}
		return true;
	}

	private boolean itWillSinkAll(int x, char y) {
		for (Ship s : ships) {
			if (!itWillSink(s, x, y)) {
				return false;
			}
		}
		return true;
	}
}
