package clueGame;

import java.util.*;


public class Board {
	/*
	private int numRows;
	private int numColumns;
	private static final int MAXBOARDSIZE = 50;
	private BoardCell board[][];
	private Map rooms;
	private Map adjMatrix;
	*/
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	
	// ctor is private to ensure only one can be created
	private Board() {}
	
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
}
