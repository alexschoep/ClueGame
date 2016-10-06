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
	}
	
	public BoardCell(int row, int column, char initial, DoorDirection doorDirection) {
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.doorDirection = doorDirection;
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
		// TODO Auto-generated method stub
		return false;
	}
	
	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public char getInitial() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
