package clueGame;

import java.util.*;
import java.io.*;

public class Board {
	
	private int numRows;
	private int numColumns;
	private static final int MAXBOARDSIZE = 50;
	private BoardCell board[][];
	private Map rooms;
	private Map adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	
	// ctor is private to ensure only one can be created
	private Board() {}
	
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public void setConfigFiles(String layout, String legend) {
		boardConfigFile = layout;
		roomConfigFile = legend;
		
	}
	
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException{
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader);
		int row = 0;
		int column = 0;
		while (in.hasNextLine()) {
			column = 0;
			String line = in.nextLine();
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) != ',') {
					if ((line.charAt(i+1) == 'R' || line.charAt(i+1) == 'L' || line.charAt(i+1) == 'U' || line.charAt(i+1) == 'D') && rooms.containsKey(line.charAt(i))) {
						board[row][column] = new BoardCell(row, column, line.charAt(i), line.charAt(i+1));
						i++;
						column++;
					}
					else if (rooms.containsKey(line.charAt(i))){
						board[row][column] = new BoardCell(row, column, line.charAt(i));
						column++;
					}
					else {
						throw new BadConfigFormatException();
					}
				}
			}
			row++;
		}
		numColumns = column;
		numRows = row;
	}
	
	public void loadBoardConfig() throws FileNotFoundException{
		FileReader reader = new FileReader(boardConfigFile);
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()) {
			String line = in.nextLine();
			char initial = line.charAt(0);
			String roomName = "";
			boolean card = false;
			int i;
			for (i = 2; line.charAt(i) == ','; i++) {
				roomName += line.charAt(i);
			}
			if (line.charAt(i+2) == 'C') {
				card = true;
			}
			rooms.put(initial, roomName);
		}
	}

	public void initialize() {
		rooms = new HashMap<Character, String>();
		board = new BoardCell[MAXBOARDSIZE][MAXBOARDSIZE];
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
}
