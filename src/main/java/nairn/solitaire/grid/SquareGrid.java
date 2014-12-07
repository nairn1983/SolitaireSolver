package nairn.solitaire.grid;

public class SquareGrid extends Grid {
	public SquareGrid(final int size) {
		super(size, size);
	}

	protected SquareGrid(final SquareGrid grid) {
		super(grid);
	}

	public int getSize() {
		return width;
	}
}
