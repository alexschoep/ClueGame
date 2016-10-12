package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class OurTests2 {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		board.setConfigFiles("map.csv", "legend.txt");		
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(17, 8);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(8, 8);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(7, 18);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(14, 3);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(17, 19);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(15, 3);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 4)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(7, 16);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(7, 15)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(19, 11);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(20, 11)));
		//TEST DOORWAY UP, this doorway has two tests so the color is marked on the cell below it
		testList = board.getAdjList(9, 12);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(8, 12)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are OLIVE GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(8, 4);
		assertTrue(testList.contains(board.getCellAt(8, 3)));
		assertTrue(testList.contains(board.getCellAt(7, 4)));
		assertTrue(testList.contains(board.getCellAt(9, 4)));
		assertTrue(testList.contains(board.getCellAt(8, 5)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(20, 11);
		System.out.println(board.getAdjList(20,  11));
		assertTrue(testList.contains(board.getCellAt(20, 10)));
		assertTrue(testList.contains(board.getCellAt(20, 12)));
		assertTrue(testList.contains(board.getCellAt(21, 11)));
		assertTrue(testList.contains(board.getCellAt(19, 11)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(16, 15);
		assertTrue(testList.contains(board.getCellAt(16, 16)));
		assertTrue(testList.contains(board.getCellAt(16, 14)));
		assertTrue(testList.contains(board.getCellAt(15, 15)));
		assertTrue(testList.contains(board.getCellAt(17, 15)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(8, 12);
		assertTrue(testList.contains(board.getCellAt(8, 11)));
		assertTrue(testList.contains(board.getCellAt(8, 13)));
		assertTrue(testList.contains(board.getCellAt(9, 12)));
		assertTrue(testList.contains(board.getCellAt(7, 12)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are BLUE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{		
		// Test on bottom edge of board, three walkway pieces
		Set<BoardCell> testList = board.getAdjList(21, 7);
		assertTrue(testList.contains(board.getCellAt(21, 6)));
		assertTrue(testList.contains(board.getCellAt(21, 8)));
		assertTrue(testList.contains(board.getCellAt(20, 7)));
		assertEquals(3, testList.size());

		// Test between two rooms, walkways right and down
		testList = board.getAdjList(3, 9);
		assertTrue(testList.contains(board.getCellAt(4, 9)));
		assertTrue(testList.contains(board.getCellAt(3, 10)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(6,8);
		assertTrue(testList.contains(board.getCellAt(6, 9)));
		assertTrue(testList.contains(board.getCellAt(6, 7)));
		assertTrue(testList.contains(board.getCellAt(7, 8)));
		assertTrue(testList.contains(board.getCellAt(5, 8)));
		assertEquals(4, testList.size());
		
		// Test on corner of walkway, next to closet, 2 walkways
		testList = board.getAdjList(0, 15);
		assertTrue(testList.contains(board.getCellAt(0, 14)));
		assertTrue(testList.contains(board.getCellAt(1, 15)));
		assertEquals(2, testList.size());
	}
	
	// Tests of just walkways, 1 step
	// These are GREY on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(3, 4, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 4)));
		assertTrue(targets.contains(board.getCellAt(2, 4)));	
		assertTrue(targets.contains(board.getCellAt(3, 5)));	
		
		board.calcTargets(6, 14, 1);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 14)));
		assertTrue(targets.contains(board.getCellAt(7, 14)));	
		assertTrue(targets.contains(board.getCellAt(6, 15)));			
	}
	
	// Tests of just walkways, 2 steps
	// These are GREY on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(21, 4, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(21, 6)));
		assertTrue(targets.contains(board.getCellAt(20, 5)));	
		assertTrue(targets.contains(board.getCellAt(19, 4)));
	}
	
	// Tests of just walkways, 4 steps
	// These are GREY on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(21, 15, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(21, 11)));
		assertTrue(targets.contains(board.getCellAt(20, 12)));
		assertTrue(targets.contains(board.getCellAt(17, 15)));
		assertTrue(targets.contains(board.getCellAt(18, 14)));
		assertTrue(targets.contains(board.getCellAt(20, 14)));
		assertTrue(targets.contains(board.getCellAt(19, 15)));
		assertTrue(targets.contains(board.getCellAt(21, 13)));
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are GREY on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(6, 9, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(16, targets.size());
		assertTrue(targets.contains(board.getCellAt(7, 8)));
		assertTrue(targets.contains(board.getCellAt(5, 4)));	
		assertTrue(targets.contains(board.getCellAt(7, 4)));	
		assertTrue(targets.contains(board.getCellAt(4, 5)));	
		assertTrue(targets.contains(board.getCellAt(8, 5)));	
		assertTrue(targets.contains(board.getCellAt(6, 5)));	
		assertTrue(targets.contains(board.getCellAt(6, 7)));
		assertTrue(targets.contains(board.getCellAt(7, 8)));
		assertTrue(targets.contains(board.getCellAt(5, 8)));
		assertTrue(targets.contains(board.getCellAt(7, 10)));
		assertTrue(targets.contains(board.getCellAt(2, 11)));
		assertTrue(targets.contains(board.getCellAt(1, 10)));
		assertTrue(targets.contains(board.getCellAt(8, 11)));
		assertTrue(targets.contains(board.getCellAt(7, 12)));
		assertTrue(targets.contains(board.getCellAt(8, 13)));
		assertTrue(targets.contains(board.getCellAt(7, 14)));
	}	
	
	// Test getting into a room
	// These are GREY on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(3, 4, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		// directly right (can't go left)
		assertTrue(targets.contains(board.getCellAt(3, 6)));
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(1, 4)));
		assertTrue(targets.contains(board.getCellAt(5, 4)));
		// one up/right, one down/right
		assertTrue(targets.contains(board.getCellAt(2, 5)));
		assertTrue(targets.contains(board.getCellAt(4, 5)));
	}

	// Test getting out of a room
	// These are GREY on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(17, 16, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 15)));
		// Take two steps
		board.calcTargets(17, 16, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 14)));
		assertTrue(targets.contains(board.getCellAt(18, 15)));
		assertTrue(targets.contains(board.getCellAt(16, 15)));
	}

}