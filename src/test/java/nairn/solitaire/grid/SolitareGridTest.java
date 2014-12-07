package nairn.solitaire.grid;

import nairn.solitaire.grid.parser.SolitaireGridParser;
import nairn.solitaire.move.Direction;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class SolitareGridTest {

	@Test
	public void testSolitareGrid_ArmLength0() {
		final String grid = "***\n" +
				"*o*\n" +
				"***";
		testSolitareGrid(0, grid);
	}

	@Test
	public void testSolitareGrid_ArmLength1() {
		final String grid = "" +
				" *** \n" +
				"*****\n" +
				"**o**\n" +
				"*****\n" +
				" *** ";
		testSolitareGrid(1, grid);
	}

	@Test
	public void testSolitareGrid_ArmLength2() {
		final String grid = "" +
				"  ***  \n" +
				"  ***  \n" +
				"*******\n" +
				"***o***\n" +
				"*******\n" +
				"  ***  \n" +
				"  ***  ";
		testSolitareGrid(2, grid);
	}

	@Test
	public void testSolitareGrid_ArmLength3() {
		final String grid = "" +
				"   ***   \n" +
				"   ***   \n" +
				"   ***   \n" +
				"*********\n" +
				"****o****\n" +
				"*********\n" +
				"   ***   \n" +
				"   ***   \n" +
				"   ***   ";
		testSolitareGrid(3, grid);
	}

	private void testSolitareGrid(final int armSize, final String expectedGrid) {
		final Grid solitareGrid = new SolitaireGrid(armSize);
		assertThat(solitareGrid.toString(), equalTo(expectedGrid));
	}

	@Test
	public void test9x9SolitaireMoveIsValid1() {
		test9x9SolitaireMoveIsValid(4, 2, Direction.DOWN, true);
	}

	@Test
	public void test9x9SolitaireMoveIsValid2() {
		test9x9SolitaireMoveIsValid(4, 2, Direction.UP, false);
	}

	@Test
	public void test9x9SolitaireMoveIsValid3() {
		test9x9SolitaireMoveIsValid(4, 2, Direction.LEFT, false);
	}

	@Test
	public void test9x9SolitaireMoveIsValid4() {
		test9x9SolitaireMoveIsValid(4, 2, Direction.RIGHT, false);
	}

	@Test
	public void test9x9SolitaireMoveIsValid5() {
		test9x9SolitaireMoveIsValid(6, 4, Direction.DOWN, false);
	}

	@Test
	public void test9x9SolitaireMoveIsValid6() {
		test9x9SolitaireMoveIsValid(6, 4, Direction.UP, false);
	}

	@Test
	public void test9x9SolitaireMoveIsValid7() {
		test9x9SolitaireMoveIsValid(6, 4, Direction.LEFT, true);
	}

	@Test
	public void test9x9SolitaireMoveIsValid8() {
		test9x9SolitaireMoveIsValid(6, 4, Direction.RIGHT, false);
	}

	@Test
	public void test9x9SolitaireMoveIsInvalid_centreHole_up() {
		test9x9SolitaireMoveIsValid(4, 4, Direction.UP, false);
	}

	@Test
	public void test9x9SolitaireMoveIsInvalid_centreHole_down() {
		test9x9SolitaireMoveIsValid(4, 4, Direction.DOWN, false);
	}

	@Test
	public void test9x9SolitaireMoveIsInvalid_centreHole_left() {
		test9x9SolitaireMoveIsValid(4, 4, Direction.LEFT, false);
	}

	@Test
	public void test9x9SolitaireMoveIsInvalid_centreHole_right() {
		test9x9SolitaireMoveIsValid(4, 4, Direction.RIGHT, false);
	}

	private void test9x9SolitaireMoveIsValid(final int x, final int y, final Direction direction, final boolean valid) {
		final SolitaireGrid grid = new SolitaireGrid(3);
		final boolean moveIsValid = grid.moveIsValid(x, y, direction);
		assertThat(moveIsValid, equalTo(valid));
	}

	@Test
	public void test9x9SolitaireMove1() {
		final String grid = "" +
				"   ***   \n" +
				"   ***   \n" +
				"   *o*   \n" +
				"****o****\n" +
				"*********\n" +
				"*********\n" +
				"   ***   \n" +
				"   ***   \n" +
				"   ***   ";
		test9x9SolitaireMove(4, 2, Direction.DOWN, grid);
	}

	@Test
	public void test9x9SolitaireMove2() {
		final String grid = "" +
				"   ***   \n" +
				"   ***   \n" +
				"   ***   \n" +
				"*********\n" +
				"****o****\n" +
				"*********\n" +
				"   ***   \n" +
				"   ***   \n" +
				"   ***   ";
		test9x9SolitaireMove(4, 2, Direction.UP, grid);
	}

	@Test
	public void test9x9SolitaireMove3() {
		final String grid = "" +
				"   ***   \n" +
				"   ***   \n" +
				"   ***   \n" +
				"*********\n" +
				"****o****\n" +
				"*********\n" +
				"   ***   \n" +
				"   ***   \n" +
				"   ***   ";
		test9x9SolitaireMove(4, 2, Direction.LEFT, grid);
	}

	@Test
	public void test9x9SolitaireMove4() {
		final String grid = "" +
				"   ***   \n" +
				"   ***   \n" +
				"   ***   \n" +
				"*********\n" +
				"****o****\n" +
				"*********\n" +
				"   ***   \n" +
				"   ***   \n" +
				"   ***   ";
		test9x9SolitaireMove(4, 2, Direction.RIGHT, grid);
	}

	private void test9x9SolitaireMove(final int x, final int y, final Direction direction, final String expectedGrid) {
		final SolitaireGrid grid = new SolitaireGrid(3);
		final SolitaireGrid newGrid = grid.move(x, y, direction);
		assertThat(newGrid.toString(), equalTo(expectedGrid));
	}

	@Test
	public void test3x3SolitaireGridIsComplete() {
		final String grid = "" +
				"ooo\n" +
				"o*o\n" +
				"ooo";
		testSolitaireGridIsComplete(grid, true);
	}

	@Test
	public void test3x3SolitaireGridIsIncomplete1() {
		final String grid = "" +
				"*oo\n" +
				"ooo\n" +
				"ooo";
		testSolitaireGridIsComplete(grid, false);
	}

	@Test
	public void test3x3SolitaireGridIsIncomplete2() {
		final String grid = "" +
				"*o*\n" +
				"o*o\n" +
				"*oo";
		testSolitaireGridIsComplete(grid, false);
	}

	@Test
	public void test9x9SolitaireGridIsComplete() {
		final String grid = "" +
				"   ooo   \n" +
				"   ooo   \n" +
				"   ooo   \n" +
				"ooooooooo\n" +
				"oooo*oooo\n" +
				"ooooooooo\n" +
				"   ooo   \n" +
				"   ooo   \n" +
				"   ooo   ";
		testSolitaireGridIsComplete(grid, true);
	}

	private void testSolitaireGridIsComplete(final String grid, final boolean expectedResult) {
		final SolitaireGrid solitaireGrid = SolitaireGridParser.parseSolitaireGrid(grid);
		assertThat(solitaireGrid.isComplete(), equalTo(expectedResult));
	}

	@Test
	public void test9x9Grid1HasValidMoveAt3_0() {
		test9x9Grid1HasValidMove(3, 0, false);
	}

	@Test
	public void test9x9Grid1HasValidMoveAt4_0() {
		test9x9Grid1HasValidMove(4, 0, false);
	}

	@Test
	public void test9x9Grid1HasValidMoveAt5_0() {
		test9x9Grid1HasValidMove(5, 0, true);
	}

	@Test
	public void test9x9Grid1HasValidMoveAt0_3() {
		test9x9Grid1HasValidMove(0, 3, true);
	}

	@Test
	public void test9x9Grid1HasValidMoveAt1_3() {
		test9x9Grid1HasValidMove(1, 3, true);
	}

	@Test
	public void test9x9Grid1HasValidMoveAt2_3() {
		test9x9Grid1HasValidMove(2, 3, false);
	}

	@Test
	public void test9x9Grid1HasValidMoveAt3_3() {
		test9x9Grid1HasValidMove(3, 3, true);
	}

	@Test
	public void test9x9Grid1HasValidMoveAt4_3() {
		test9x9Grid1HasValidMove(4, 3, true);
	}

	@Test
	public void test9x9Grid1HasValidMoveAt5_3() {
		test9x9Grid1HasValidMove(5, 3, false);
	}

	@Test
	public void test9x9Grid1HasValidMoveAt6_3() {
		test9x9Grid1HasValidMove(6, 3, false);
	}

	@Test
	public void test9x9Grid1HasValidMoveAt7_3() {
		test9x9Grid1HasValidMove(7, 3, false);
	}

	@Test
	public void test9x9Grid1HasValidMoveAt8_3() {
		test9x9Grid1HasValidMove(8, 3, false);
	}

	@Test
	public void test9x9GridHasInvalidMoveAtInvalidPosition() {
		test9x9Grid1HasValidMove(1, 1, false);
	}

	private void test9x9Grid1HasValidMove(final int x, final int y, final boolean expectedResult) {
		final String grid = "" +
				"   ***   \n" +
				"   *o*   \n" +
				"   *oo   \n" +
				"**o******\n" +
				"*****o***\n" +
				"*ooo*****\n" +
				"   ***   \n" +
				"   *o*   \n" +
				"   **o   ";
		testHasValidMove(grid, x, y, expectedResult);
	}

	private void testHasValidMove(final String grid, final int x, final int y, final boolean expectedResult) {
		final SolitaireGrid solitaireGrid = SolitaireGridParser.parseSolitaireGrid(grid);
		final boolean hasValidMove = solitaireGrid.hasValidMove(x,y);
		assertThat(hasValidMove, equalTo(expectedResult));
	}

	@Test
	public void testCountPegs1() {
		final String grid = "" +
				"   ***   \n" +
				"   *o*   \n" +
				"   *oo   \n" +
				"**o******\n" +
				"*****o***\n" +
				"*ooo*****\n" +
				"   ***   \n" +
				"   *o*   \n" +
				"   **o   ";
		testCountPegs(grid, 35);
	}

	private void testCountPegs(final String grid, final int numberOfPegs) {
		final SolitaireGrid solitaireGrid = SolitaireGridParser.parseSolitaireGrid(grid);
		final int count = solitaireGrid.countPegs();
		assertThat(count, equalTo(numberOfPegs));
	}

	@Test
	public void testEqual9x9Grid() {
		final String grid = "" +
				"   ***   \n" +
				"   *o*   \n" +
				"   *oo   \n" +
				"**o******\n" +
				"*****o***\n" +
				"*ooo*****\n" +
				"   ***   \n" +
				"   *o*   \n" +
				"   **o   ";
		testSolitaireGridEquality(grid, grid, true);
	}

	private void testSolitaireGridEquality(final String grid1, final String grid2, final boolean expectedResult) {
		final SolitaireGrid solitaireGrid1 = SolitaireGridParser.parseSolitaireGrid(grid1);
		final SolitaireGrid solitaireGrid2 = SolitaireGridParser.parseSolitaireGrid(grid2);
		assertThat(solitaireGrid1.equals(solitaireGrid2), equalTo(expectedResult));
		assertThat(solitaireGrid2.equals(solitaireGrid1), equalTo(expectedResult));
	}

	@Test
	public void testGridWithValidMoves() {
		final String grid = "" +
				"   ***   \n" +
				"   *o*   \n" +
				"   *oo   \n" +
				"**o******\n" +
				"*****o***\n" +
				"*ooo*****\n" +
				"   ***   \n" +
				"   *o*   \n" +
				"   **o   ";
		testGridHasValidMoves(grid, true);
	}

	@Test
	public void testGridWithNoValidMoves() {
		final String grid = "" +
				"   ***   \n" +
				"   ooo   \n" +
				"   *oo   \n" +
				"*ooooooo*\n" +
				"*ooo*oooo\n" +
				"*ooooo*oo\n" +
				"   ***   \n" +
				"   ooo   \n" +
				"   o*o   ";
		testGridHasValidMoves(grid, false);
	}

	private void testGridHasValidMoves(final String grid, final boolean expectedResult) {
		final SolitaireGrid solitaireGrid = SolitaireGridParser.parseSolitaireGrid(grid);
		assertThat(solitaireGrid.hasValidMoves(), equalTo(expectedResult));
	}
}
