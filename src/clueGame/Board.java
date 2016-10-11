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
	private Set<BoardCell> targets;
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

	public BoardCell getCellAt(int i, int j) {
		return board[i][j];
	}
	
	public void calcAdjacencies() {
		return;
	}
	
	public void calcTargets(int a, int b, int c) {
		return;
	}
	
	public Set<BoardCell> getAdjList(int a, int b) {
		return null;
	}
	
	public Set<BoardCell> getTargets() {
		return null;
	}
}
