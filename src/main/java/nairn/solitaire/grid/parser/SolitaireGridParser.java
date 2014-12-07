package nairn.solitaire.grid.parser;

import nairn.solitaire.grid.SolitaireGrid;
import nairn.solitaire.grid.SquareGrid;
import nairn.solitaire.grid.util.SolitaireGridUtil;

public final class SolitaireGridParser {

	public static SolitaireGrid parseSolitaireGrid(final String grid) {
		final SquareGrid squareGrid = SquareGridParser.parseSquareGrid(grid);
		return SolitaireGridUtil.convertSquareGridToSolitaireGrid(squareGrid);
	}
}
