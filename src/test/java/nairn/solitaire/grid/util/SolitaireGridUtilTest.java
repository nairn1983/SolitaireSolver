package nairn.solitaire.grid.util;

import nairn.solitaire.grid.SolitaireGrid;
import nairn.solitaire.grid.SquareGrid;
import nairn.solitaire.grid.parser.SquareGridParser;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class SolitaireGridUtilTest {

	@Test
	public void testValid9x9SolitaireGrid1() {
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
		testSolitaireGridIsValid(grid, true);
	}

	@Test
	public void testValid9x9SolitaireGrid2() {
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
		testSolitaireGridIsValid(grid, true);
	}

	@Test
	public void testInvalid9x9SolitaireGrid1() {
		final String grid = "" +
				"   ***   \n" +
				"   * *   \n" +
				"   *oo   \n" +
				"**o******\n" +
				"*****o **\n" +
				"*ooo*****\n" +
				"    **   \n" +
				"   *o*   \n" +
				"   **o   ";
		testSolitaireGridIsValid(grid, false);
	}

	@Test
	public void testInvalid9x9SolitaireGrid2() {
		final String grid = "" +
				"   ***   \n" +
				"   *** o \n" +
				"   ***   \n" +
				"*********\n" +
				"****o****\n" +
				"*********\n" +
				"   ****  \n" +
				"   ***   \n" +
				"*  ***   ";
		testSolitaireGridIsValid(grid, false);
	}

	private void testSolitaireGridIsValid(final String inputGrid, final boolean expectedResult) {
		final SquareGrid grid = SquareGridParser.parseSquareGrid(inputGrid);
		final boolean isValidSolitaireGrid = SolitaireGridUtil.isValidSolitaireGrid(grid);
		assertThat(isValidSolitaireGrid, equalTo(expectedResult));
	}

	@Test
	public void testConvertValid9x9SolitaireGrid1() {
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
		testConvertSquareGridToSolitaireGrid(grid, true);
	}

	@Test
	public void testConvertValid9x9SolitaireGrid2() {
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
		testConvertSquareGridToSolitaireGrid(grid, true);
	}

	@Test
	public void testConvertInvalid9x9SolitaireGrid1() {
		final String grid = "" +
				"   ***   \n" +
				"   * *   \n" +
				"   *oo   \n" +
				"**o******\n" +
				"*****o **\n" +
				"*ooo*****\n" +
				"    **   \n" +
				"   *o*   \n" +
				"   **o   ";
		testConvertSquareGridToSolitaireGrid(grid, false);
	}

	@Test
	public void testConvertInvalid9x9SolitaireGrid2() {
		final String grid = "" +
				"   ***   \n" +
				"   *** o \n" +
				"   ***   \n" +
				"*********\n" +
				"****o****\n" +
				"*********\n" +
				"   ****  \n" +
				"   ***   \n" +
				"*  ***   ";
		testConvertSquareGridToSolitaireGrid(grid, false);
	}

	private void testConvertSquareGridToSolitaireGrid(final String inputGrid, final boolean expectedToPass) {
		final SquareGrid squareGrid = SquareGridParser.parseSquareGrid(inputGrid);
		if(expectedToPass) {
			final SolitaireGrid solitaireGrid = SolitaireGridUtil.convertSquareGridToSolitaireGrid(squareGrid);

			assertThat(solitaireGrid, notNullValue());
			assertThat(solitaireGrid.toString(), equalTo(inputGrid));

		} else {
			boolean exceptionThrown = false;
			try {
				SolitaireGridUtil.convertSquareGridToSolitaireGrid(squareGrid);

			} catch(final Exception e) {
				exceptionThrown = true;
			}
			assertThat(exceptionThrown, equalTo(true));
		}

	}
}
