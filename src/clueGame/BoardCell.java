package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private DoorDirection doorDirection;
	private char initial;
	
	public BoardCell() {
		row = 0;
		column = 0;
	}
	public BoardCell(int row, int column, char initial) {
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.doorDirection = DoorDirection.NONE;
	}
	
	public BoardCell(int row, int column, char initial, char doorDirection) {
		this.row = row;
		this.column = column;
		this.initial = initial;
		if (doorDirection == 'R') {
			this.doorDirection = DoorDirection.RIGHT;
		}
		if (doorDirection == 'L') {
			this.doorDirection = DoorDirection.LEFT;
		}
		if (doorDirection == 'U') {
			this.doorDirection = DoorDirection.UP;
		}
		if (doorDirection == 'D') {
			this.doorDirection = DoorDirection.DOWN;
		}
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}
	
	public boolean isDoorway() {
		return doorDirection != DoorDirection.NONE;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public char getInitial() {
		return initial;
	}
	
	
}
