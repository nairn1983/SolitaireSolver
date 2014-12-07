package nairn.solitaire.grid.parser;

import nairn.solitaire.grid.Grid;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class SquareGridParserTest {

	@Test
	public void testSquareGrid0x0() {
		final String squareGrid1 = "";
		testSquareGridParser(squareGrid1);
	}

	@Test
	public void testSquareGrid1x1Null() {
		final String squareGrid = " ";
		testSquareGridParser(squareGrid);
	}

	@Test
	public void testSquareGrid1x1Full() {
		final String squareGrid = "*";
		testSquareGridParser(squareGrid);
	}

	@Test
	public void testSquareGrid1x1Empty() {
		final String squareGrid = "o";
		testSquareGridParser(squareGrid);
	}

	@Test
	public void testSquareGrid4x4() {
		final String squareGrid = " ** \n" +
				" *o \n" +
				" oo \n" +
				" *  ";
		testSquareGridParser(squareGrid);
	}

	private void testSquareGridParser(final String inputGrid) {
		final Grid grid = SquareGridParser.parseSquareGrid(inputGrid);
		assertThat(grid.toString(), equalTo(inputGrid));
	}
}
