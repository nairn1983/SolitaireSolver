package nairn.solitaire.grid;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

public class SquareGridTest {

	@Test
	public void testSquareGrid_size1_x0_y0_expectNull() {
		testSquareGrid(1, 0, 0, null);
	}

	@Test
	public void testSquareGrid_size1_x1_y0_expectArrayIndexOutOfBoundsException() {
		testSquareGridExpectException(1, 1, 0, ArrayIndexOutOfBoundsException.class);
	}

	@Test
	public void testSquareGrid_size1_x0_y1_expectArrayIndexOutOfBoundsException() {
		testSquareGridExpectException(1, 0, 1, ArrayIndexOutOfBoundsException.class);
	}

	@Test
	public void testSquareGrid_size2_x0_y0_expectNull() {
		testSquareGrid(2, 0, 0, null);
	}

	@Test
	public void testSquareGrid_size2_x1_y0_expectNull() {
		testSquareGrid(2, 1, 0, null);
	}

	@Test
	public void testSquareGrid_size2_x0_y1_expectNull() {
		testSquareGrid(2, 0, 1, null);
	}

	@Test
	public void testSquareGrid_size2_x1_y1_expectNull() {
		testSquareGrid(2, 1, 1, null);
	}

	@Test
	public void testSquareGrid_fill1() {
		final Grid grid = new SquareGrid(5);
		grid.set(3,1,true);
		grid.set(3,2,true);
		grid.set(0,4,false);

		final String expectedString =
				"" +
						"     \n" +
						"   * \n" +
						"   * \n" +
						"     \n" +
						"o    ";

		assertThat(grid.toString(), equalTo(expectedString));
	}

	private void testSquareGrid(final int size, final int x, final int y, final Boolean expectedResult) {
		// GIVEN: a square grid is created
		final Grid grid = new SquareGrid(size);

		// WHEN: we get the information stored at cell (x,y)
		final Boolean gridXY = grid.get(x, y);

		// THEN: we expect the contents of cell (x,y) to match our expected result
		assertThat(gridXY, equalTo(expectedResult));
	}

	private <T extends Throwable> void testSquareGridExpectException(final int size, final int x, final int y,
	                                           final Class<T> expectedException) {
		// GIVEN: a square grid is created
		final Grid grid = new SquareGrid(size);

		// WHEN: we get the information stored at cell (x,y)
		try {
			final Boolean gridXY = grid.get(x, y);

		} catch(final Exception e) {
			// THEN: we expect an exception to be thrown
			assertThat(e, instanceOf(expectedException));
		}
	}
}
