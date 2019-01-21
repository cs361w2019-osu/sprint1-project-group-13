package cs361.battleships.models;

public class Result {
	private AttackStatus status;
	private Ship ship;
	private Square square;

	public AttackStatus getResult() {
		return status;
	}

	public void setResult(AttackStatus result) {
		status = result;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public Square getLocation() {
		return square;
	}

	public void setLocation(Square square) {
		this.square = square;
	}
}

