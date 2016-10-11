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
		board.setConfigFiles("CR_ClueLayout.csv", "CR_ClueLegend.txt");		
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
		testList = board.getAdjList(17, 11);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(18, 11)));
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
		testList = board.getAdjList(18, 11);
		assertTrue(testList.contains(board.getCellAt(18, 10)));
		assertTrue(testList.contains(board.getCellAt(18, 12)));
		assertTrue(testList.contains(board.getCellAt(17, 11)));
		assertTrue(testList.contains(board.getCellAt(19, 11)));
		assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(14, 15);
		assertTrue(testList.contains(board.getCellAt(14, 16)));
		assertTrue(testList.contains(board.getCellAt(14, 13)));
		assertTrue(testList.contains(board.getCellAt(15, 15)));
		assertTrue(testList.contains(board.getCellAt(13, 15)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(8, 12);
		assertTrue(testList.contains(board.getCellAt(8, 11)));
		assertTrue(testList.contains(board.getCellAt(8, 13)));
		assertTrue(testList.contains(board.getCellAt(9, 12)));
		assertTrue(testList.contains(board.getCellAt(7, 11)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are BLUE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{		
		// Test on bottom edge of board, three walkway pieces
		Set<BoardCell> testList = board.getAdjList(19, 7);
		assertTrue(testList.contains(board.getCellAt(19, 6)));
		assertTrue(testList.contains(board.getCellAt(19, 8)));
		assertTrue(testList.contains(board.getCellAt(7, 18)));
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
	// These are RUST on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(21, 7, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 7)));
		assertTrue(targets.contains(board.getCellAt(20, 6)));		
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(21, 7, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 7)));
		assertTrue(targets.contains(board.getCellAt(19, 7)));
		assertTrue(targets.contains(board.getCellAt(18, 6)));
		assertTrue(targets.contains(board.getCellAt(20, 6)));
		
		// Includes a path that doesn't have enough length
		board.calcTargets(14, 0, 4);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 4)));
		assertTrue(targets.contains(board.getCellAt(15, 3)));	
		assertTrue(targets.contains(board.getCellAt(14, 2)));	
		assertTrue(targets.contains(board.getCellAt(15, 1)));	
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(14, 0, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 6)));
		assertTrue(targets.contains(board.getCellAt(15, 5)));	
		assertTrue(targets.contains(board.getCellAt(15, 3)));	
		assertTrue(targets.contains(board.getCellAt(14, 4)));	
		assertTrue(targets.contains(board.getCellAt(15, 1)));	
		assertTrue(targets.contains(board.getCellAt(14, 2)));	
		assertTrue(targets.contains(board.getCellAt(13, 4)));	
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(17, 16, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		// directly left (can't go right 2 steps)
		assertTrue(targets.contains(board.getCellAt(17, 14)));
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(15, 16)));
		assertTrue(targets.contains(board.getCellAt(19, 16)));
		// one up/down, one left/right
		assertTrue(targets.contains(board.getCellAt(18, 17)));
		assertTrue(targets.contains(board.getCellAt(18, 15)));
		assertTrue(targets.contains(board.getCellAt(16, 17)));
		assertTrue(targets.contains(board.getCellAt(16, 15)));
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(4, 20, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 19)));
		// Take two steps
		board.calcTargets(4, 20, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 19)));
		assertTrue(targets.contains(board.getCellAt(5, 19)));
		assertTrue(targets.contains(board.getCellAt(4, 18)));
	}

}