package nairn.solitaire.grid;

public abstract class Grid {
	private final Boolean[][] grid;
	private final int height;
	protected final int width;

	public Grid(final int width, final int height) {
		grid = new Boolean[height][width];
		this.width = width;
		this.height = height;
	}

	protected Grid(final Grid grid) {
		this.width = grid.width;
		this.height = grid.height;
		this.grid = cloneGrid(grid.grid);
	}

	private Boolean[][] cloneGrid(final Boolean[][] originalGrid) {
		final Boolean[][] newGrid = new Boolean[width][height];
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				newGrid[y][x] = originalGrid[y][x];
			}
		}
		return newGrid;
	}

	public Boolean get(final int x, final int y) {
		return grid[y][x];
	}

	public void set(final int x, final int y, final Boolean value) {
		grid[y][x] = value;
	}

	public String toString() {
		final StringBuilder sb = new StringBuilder();

		if(grid.length > 0) {
			for (final Boolean[] line : grid) {
				for (final Boolean element : line) {

					if (element == null) {
						sb.append(" ");
					} else if (element) {
						sb.append("*");
					} else {
						sb.append("o");
					}
				}
				sb.append("\n");
			}
			sb.delete(sb.length()-1, sb.length());
		}
		return sb.toString();
	}
}
