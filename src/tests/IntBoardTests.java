package tests;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.Set;
import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {
	
	IntBoard board;
	// Setup the IntBoard()
	@Before public void init() {
		board = new IntBoard();
	}
	
	//tests top left corner
	@Test
	public void testAdjacency0()
	{
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	
	//tests bottom right corner
	@Test
	public void testAdjacency1()
	{
		BoardCell cell = board.getCell(3,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(2, testList.size());
	}
	
	//tests right edge
	@Test
	public void testAdjacency2()
	{
		BoardCell cell = board.getCell(1,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 3)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(3, testList.size());
	}
	
	//tests left edge
	@Test
	public void testAdjacency3()
	{
		BoardCell cell = board.getCell(1,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 0)));
		assertTrue(testList.contains(board.getCell(1, 1)));
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertEquals(3, testList.size());
	}
	
	//tests middle
	@Test
	public void testAdjacency5()
	{
		BoardCell cell = board.getCell(1,1);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertEquals(4, testList.size());
	}
	
	//tests middle
	@Test
	public void testAdjacency6()
	{
		BoardCell cell = board.getCell(2,2);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(4, testList.size());
	}
	
	@Test
	public void testTargets0_3()
	{
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<BoardCell> targetList = board.getTargets();
		assertEquals(6, targetList.size());
		assertTrue(targetList.contains(board.getCell(3, 0)));
		assertTrue(targetList.contains(board.getCell(2, 1)));
		assertTrue(targetList.contains(board.getCell(0, 1)));
		assertTrue(targetList.contains(board.getCell(1, 2)));
		assertTrue(targetList.contains(board.getCell(0, 3)));
		assertTrue(targetList.contains(board.getCell(1, 0)));
	}
	
	@Test
	public void testTargets1_3()
	{
		BoardCell cell = board.getCell(3, 3);
		board.calcTargets(cell, 3);
		Set<BoardCell> targetList = board.getTargets();
		assertEquals(6, targetList.size());
		assertTrue(targetList.contains(board.getCell(3, 0)));
		assertTrue(targetList.contains(board.getCell(0, 3)));
		assertTrue(targetList.contains(board.getCell(2, 1)));
		assertTrue(targetList.contains(board.getCell(1, 2)));
		assertTrue(targetList.contains(board.getCell(2, 3)));
		assertTrue(targetList.contains(board.getCell(3, 2)));
	}
	
	@Test
	public void testTargets2_2()
	{
		BoardCell cell = board.getCell(2, 2);
		board.calcTargets(cell, 2);
		Set<BoardCell> targetList = board.getTargets();
		assertEquals(6, targetList.size());
		assertTrue(targetList.contains(board.getCell(3, 3)));
		assertTrue(targetList.contains(board.getCell(1, 1)));
		assertTrue(targetList.contains(board.getCell(1, 3)));
		assertTrue(targetList.contains(board.getCell(0, 2)));
		assertTrue(targetList.contains(board.getCell(2, 0)));
		assertTrue(targetList.contains(board.getCell(3, 1)));
	}
	
	@Test
	public void testTargets1_2()
	{
		BoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 2);
		Set<BoardCell> targetList = board.getTargets();
		assertEquals(6, targetList.size());
		assertTrue(targetList.contains(board.getCell(0, 0)));
		assertTrue(targetList.contains(board.getCell(2, 2)));
		assertTrue(targetList.contains(board.getCell(1, 3)));
		assertTrue(targetList.contains(board.getCell(0, 2)));
		assertTrue(targetList.contains(board.getCell(2, 0)));
		assertTrue(targetList.contains(board.getCell(3, 1)));
	}
	
	@Test
	public void testTargets1_1()
	{
		BoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 1);
		Set<BoardCell> targetList = board.getTargets();
		assertEquals(4, targetList.size());
		assertTrue(targetList.contains(board.getCell(1, 0)));
		assertTrue(targetList.contains(board.getCell(1, 2)));
		assertTrue(targetList.contains(board.getCell(0, 1)));
		assertTrue(targetList.contains(board.getCell(2, 1)));
	}
	
	@Test
	public void testTargets2_1()
	{
		BoardCell cell = board.getCell(2, 2);
		board.calcTargets(cell, 1);
		Set<BoardCell> targetList = board.getTargets();
		assertEquals(4, targetList.size());
		assertTrue(targetList.contains(board.getCell(3, 2)));
		assertTrue(targetList.contains(board.getCell(1, 2)));
		assertTrue(targetList.contains(board.getCell(2, 3)));
		assertTrue(targetList.contains(board.getCell(2, 1)));
	}
}
