package experiment;
import java.util.*;

public class IntBoard {
	//set number of rows and columns in board
	static final int NUMROWS = 4;
	static final int NUMCOLUMNS = 4;
	//stores pointers to cells in board
	private BoardCell [][] grid;
	//stores the adjacency list
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	
	//constructor, initializes adjacency list, grid
	public IntBoard() {
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		grid = new BoardCell[NUMROWS][NUMCOLUMNS];
		for (int i = 0; i < NUMROWS; i++) {
			for (int j = 0; j < NUMCOLUMNS; j++) {
				grid[i][j] = new BoardCell(i, j);
			}
		}
		calcAdjacencies();
	}
	
	//generates adjacency list for board
	public void calcAdjacencies() {
		for (int i = 0; i < NUMROWS; i++) {
			for (int j = 0; j < NUMCOLUMNS; j++) {
				BoardCell cell = grid[i][j];
				Set<BoardCell> adjCells = new HashSet<BoardCell>();
				
				if (cell.getRow() - 1 >= 0) {
					adjCells.add(grid[cell.getRow() - 1][cell.getColumn()]);
				}
				
				if (cell.getRow() + 1 < NUMROWS) {
					adjCells.add(grid[cell.getRow() + 1][cell.getColumn()]);
				}
				if (cell.getColumn() - 1 >= 0) {
					adjCells.add(grid[cell.getRow()][cell.getColumn() - 1]);
				}
				
				if (cell.getColumn() + 1 < NUMCOLUMNS) {
					adjCells.add(grid[cell.getRow()][cell.getColumn() + 1]);
				}
				
				adjMtx.put(grid[i][j], adjCells);
			}
		}
	}
	
	//calculates targets for a move
	private Set visited = new HashSet<BoardCell>();
	private Set targets = new HashSet<BoardCell>();
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(startCell);
		calcRecurseTargets(startCell, pathLength);
	}	
	
	//recursively finds targets for calcTargets
	private void calcRecurseTargets(BoardCell startCell, int pathLength) {
		for (BoardCell adjCell : adjMtx.get(startCell)) {
			if (!visited.contains(adjCell)) {
				visited.add(adjCell);
				if (pathLength == 1) {
					targets.add(adjCell);
				}
				else {
					calcRecurseTargets(adjCell, pathLength - 1);
				}
				visited.remove(adjCell);	
			}
		}
	}
	
	//returns the targets for a move
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	//returns set of cells adjacent to cell that is passed in
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjMtx.get(cell);
	}
	
	//getter
	public BoardCell getCell(int row, int column) {
		return grid[row][column];
	}

}
