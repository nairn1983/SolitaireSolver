package nairn.solitaire.grid.parser;

import nairn.solitaire.grid.SolitaireGrid;
import nairn.solitaire.grid.SquareGrid;
import nairn.solitaire.grid.util.SolitaireGridUtil;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SolitaireGridParserTest {

	@Test
	public void testParseValid9x9SolitaireGrid1() {
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
		testParseSolitaireGrid(grid, true);
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
		testParseSolitaireGrid(grid, true);
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
		testParseSolitaireGrid(grid, false);
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
		testParseSolitaireGrid(grid, false);
	}

	private void testParseSolitaireGrid(final String inputGrid, final boolean expectedToPass) {
		if(expectedToPass) {
			final SolitaireGrid solitaireGrid = SolitaireGridParser.parseSolitaireGrid(inputGrid);

			assertThat(solitaireGrid, notNullValue());
			assertThat(solitaireGrid.toString(), equalTo(inputGrid));

		} else {
			boolean exceptionThrown = false;
			try {
				SolitaireGridParser.parseSolitaireGrid(inputGrid);

			} catch(final Exception e) {
				exceptionThrown = true;
			}
			assertThat(exceptionThrown, equalTo(true));
		}
	}
}
