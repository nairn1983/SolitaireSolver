package nairn.solitaire.grid.parser;

import nairn.solitaire.grid.SquareGrid;

public final class SquareGridParser {

	public static SquareGrid parseSquareGrid(final String grid) {
		final String[] tokens = grid.split("\n");
		final SquareGrid squareGrid;

		if(tokens.length > 0 && !tokens[0].isEmpty()) {
			squareGrid = new SquareGrid(tokens.length);
			int y = 0;

			for (final String line : tokens) {
				for (int x = 0; x < tokens.length; x++) {
					switch (line.charAt(x)) {
						case '*':
							squareGrid.set(x, y, true);
							break;
						case 'o':
							squareGrid.set(x, y, false);
							break;
						default:
							squareGrid.set(x, y, null);
							break;
					}
				}
				y++;
			}
		} else {
			squareGrid = new SquareGrid(0);
		}

		return squareGrid;
	}
}
