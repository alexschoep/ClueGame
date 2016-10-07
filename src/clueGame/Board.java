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
	
	public void loadRoomConfig() throws FileNotFoundException{
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader);
		int row = 0;
		int column = 0;
		while (in.hasNextLine()) {
			column = 0;
			String line = in.nextLine();
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) != ',') {
					if (line.charAt(i+1) == 'R' || line.charAt(i+1) == 'L' || line.charAt(i+1) == 'U' || line.charAt(i+1) == 'D') {
						board[row][column] = new BoardCell(row, column, line.charAt(i), line.charAt(i+1));
						column++;
					}
					else {
						board[row][column] = new BoardCell(row, column, line.charAt(i));
						column++;
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
			
		}
	}

	public void initialize() {
		rooms = new HashMap<Character, String>();
	}

	public Map<Character, String> getLegend() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getNumRows() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNumColumns() {
		// TODO Auto-generated method stub
		return 0;
	}

	public BoardCell getCellAt(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
}
