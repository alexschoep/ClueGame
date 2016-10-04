package clueGame;

public class BoardCell {
	private int row;
	private int column;
	
	public BoardCell() {
		row = 0;
		column = 0;
	}
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
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
