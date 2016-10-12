package clueGame;

import java.util.*;

import java.io.*;

public class Board {
	
	private int numRows;
	private int numColumns;
	private static final int MAXBOARDSIZE = 50;
	private BoardCell board[][];
	private Map<Character, String> rooms;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	//private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	
	// ctor is private to ensure only one can be created
	private Board() {
		rooms = new HashMap<Character, String>();
		board = new BoardCell[MAXBOARDSIZE][MAXBOARDSIZE];
	}
	
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public void setConfigFiles(String layout, String legend) {
		boardConfigFile = layout;
		roomConfigFile = legend;
		
	}
	
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException{
		FileReader reader = new FileReader(boardConfigFile);
		Scanner in = new Scanner(reader);
		int row = 0;
		int column = 0;
		int oldColumn = 0;
		boolean afterFirst = false;
		while (in.hasNextLine()) {
			column = 0;
			String line = in.nextLine();
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) != ',') {
					if ((i + 1) < line.length() && (line.charAt(i+1) == 'R' || line.charAt(i+1) == 'L' || line.charAt(i+1) == 'U' || line.charAt(i+1) == 'D') && rooms.containsKey(line.charAt(i))) {
						board[row][column] = new BoardCell(row, column, line.charAt(i), line.charAt(i+1));
						i++;
						column++;
					}
					else if ((i + 1) < line.length() && (line.charAt(i+1) == 'N') && rooms.containsKey(line.charAt(i))) {
						board[row][column] = new BoardCell(row, column, line.charAt(i));
						i++;
						column++;
					}
					else if (rooms.containsKey(line.charAt(i))){
						board[row][column] = new BoardCell(row, column, line.charAt(i));
						column++;
					}
					else {
						throw new BadConfigFormatException("ERROR: Bad file format! Config not loaded! Line:" + line + " Item: " + line.charAt(i) + " i=" + i);
					}
				}
			}
			if (oldColumn != column && afterFirst) {
				throw new BadConfigFormatException();
			} else {
				row++;
				oldColumn = column;
				afterFirst = true;
			}

		}
		numColumns = column;
		numRows = row;
	}
	
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException{
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader);
		rooms = new HashMap<Character, String>();
		while (in.hasNextLine()) {
			String line = in.nextLine();
			char initial = line.charAt(0);
			String roomName = "";
			boolean card = false;
			int i;
			for (i = 3; line.charAt(i) != ','; i++) {
				roomName += line.charAt(i);
			}
			if (line.charAt(i+2) == 'C') {
				card = true;
			}
			else if (line.charAt(i+2) != 'O') {
				throw new BadConfigFormatException("ERROR: Bad file format! Config not loaded! Line:" + line);
			}
			rooms.put(initial, roomName);
		}
		in.close();
	}

	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calcAdjacencies();
	}

	public Map<Character, String> getLegend() {
		return rooms;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public BoardCell getCellAt(int row, int column) {
		return board[row][column];
	}
	
	public void calcAdjacencies() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				BoardCell cell = board[i][j];
				Set<BoardCell> adjCells = new HashSet<BoardCell>();
				
				BoardCell adjCell;
				
				if (cell.getRow() - 1 >= 0) {
					adjCell = board[cell.getRow() - 1][cell.getColumn()];
					if (adjValid(cell, adjCell)) {
						adjCells.add(adjCell);
					}
				}
				
				if (cell.getRow() + 1 < numRows) {
					adjCell = board[cell.getRow() + 1][cell.getColumn()];
					if (adjValid(cell, adjCell)) {
						adjCells.add(adjCell);
					}
				}
				
				if (cell.getColumn() - 1 >= 0) {
					adjCell = board[cell.getRow()][cell.getColumn() - 1];
					if (adjValid(cell, adjCell)) {
						adjCells.add(adjCell);
					}
				}
				
				if (cell.getColumn() + 1 < numColumns) {
					adjCell = board[cell.getRow()][cell.getColumn() + 1];
					if (adjValid(cell, adjCell)) {
						adjCells.add(adjCell);
					}
				}
				
				adjMatrix.put(board[i][j], adjCells);
			}
		}
	}
	
	public boolean adjValid(BoardCell cell, BoardCell adjCell) {
		if (cell.isDoorway()) {
			switch (cell.getDoorDirection()) {
			case RIGHT:
				return cell.getColumn() == adjCell.getColumn() + 1;
			case LEFT:
				return cell.getColumn() == adjCell.getColumn() - 1;
			case UP:
				return cell.getColumn() == adjCell.getColumn() + 1;
			case DOWN:
				return cell.getColumn() == adjCell.getColumn() - 1;
			case NONE:
				break;
			}
		} else if (adjCell.isDoorway()) {
			switch (adjCell.getDoorDirection()) {
			case RIGHT:
				return cell.getColumn() == adjCell.getColumn() - 1;
			case LEFT:
				return cell.getColumn() == adjCell.getColumn() + 1;
			case UP:
				return cell.getColumn() == adjCell.getColumn() - 1;
			case DOWN:
				return cell.getColumn() == adjCell.getColumn() + 1;
			case NONE:
				break;
			}
		} else if (cell.getInitial() != 'W' || adjCell.getInitial() != 'W') {
			return false;
		}
		return true;
	}
	
	private Set visited = new HashSet<BoardCell>();
	private Set targets = new HashSet<BoardCell>();

	public void calcTargets(int row, int column, int steps) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		BoardCell cell = board[row][column];
		visited.add(cell);
		calcRecurseTargets(cell, steps);
	}
	
	//recursively finds targets for calcTargets
	private void calcRecurseTargets(BoardCell cell, int steps) {
		for (BoardCell adjCell : adjMatrix.get(cell)) {
			if (!visited.contains(adjCell)) {
				visited.add(adjCell);
				if (steps == 1) {
					targets.add(adjCell);
				}
				else {
					calcRecurseTargets(adjCell, steps - 1);
				}
				visited.remove(adjCell);	
			}
		}
	}
	
	public Set<BoardCell> getAdjList(int row, int column) {
		return adjMatrix.get(board[row][column]);
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
}
