package nairn.solitaire.grid.util;

import nairn.solitaire.grid.SolitaireGrid;
import nairn.solitaire.grid.SquareGrid;

public final class SolitaireGridUtil {

	public static boolean isValidSolitaireGrid(final SquareGrid grid) {
		final int size = grid.getSize();
		final int armLength = (size-3)/2;
		boolean valid = true;

		RowLoop: for(int y = 0; y < armLength; y++) {
			for(int x = 0; x < armLength; x++) {
				if(grid.get(x, y) != null || grid.get(size-x-1, y) != null
						|| grid.get(x, size-y-1) != null || grid.get(size-x-1, size-y-1) != null) {
					valid = false;
					break RowLoop;
				}
			}
			for(int x = armLength; x < armLength+3; x++) {
				if(grid.get(x,y) == null || grid.get(x,size-y-1) == null) {
					valid = false;
					break RowLoop;
				}
			}
		}
		final int armLengthPlus1 = armLength+1;
		final int armLengthPlus2 = armLengthPlus1+1;
		MiddleLoop: for(int x = 0; x < size; x++) {
			if(grid.get(x, armLength) == null || grid.get(x, armLengthPlus1) == null
					|| grid.get(x,armLengthPlus2) == null) {
				valid = false;
				break MiddleLoop;
			}
		}

		return valid;
	}

	public static SolitaireGrid convertSquareGridToSolitaireGrid(final SquareGrid squareGrid) {
		final int size = squareGrid.getSize();
		final int armLength = (size-3)/2;
		boolean valid = true;

		final SolitaireGrid solitaireGrid = new SolitaireGrid(armLength);

		RowLoop: for(int y = 0; y < armLength; y++) {
			for(int x = 0; x < armLength; x++) {
				if(squareGrid.get(x, y) != null || squareGrid.get(size-x-1, y) != null
						|| squareGrid.get(x, size-y-1) != null || squareGrid.get(size-x-1, size-y-1) != null) {
					valid = false;
					break RowLoop;
				}
			}
			for(int x = armLength; x < armLength+3; x++) {
				if(squareGrid.get(x,y) == null || squareGrid.get(x,size-y-1) == null) {
					valid = false;
					break RowLoop;
				} else {
					solitaireGrid.set(x, y, squareGrid.get(x,y));
					solitaireGrid.set(x, size-y-1, squareGrid.get(x,size-y-1));
				}
			}
		}
		final int armLengthPlus1 = armLength+1;
		final int armLengthPlus2 = armLengthPlus1+1;
		MiddleLoop: for(int x = 0; x < size; x++) {
			if(squareGrid.get(x, armLength) == null || squareGrid.get(x, armLengthPlus1) == null
					|| squareGrid.get(x,armLengthPlus2) == null) {
				valid = false;
				break MiddleLoop;
			} else {
				solitaireGrid.set(x, armLength, squareGrid.get(x, armLength));
				solitaireGrid.set(x, armLengthPlus1, squareGrid.get(x, armLengthPlus1));
				solitaireGrid.set(x, armLengthPlus2, squareGrid.get(x, armLengthPlus2));
			}
		}

		if(!valid) {
			throw new IllegalStateException("Attempted to convert an invalid solitaire grid:\n" + squareGrid);
		}

		return solitaireGrid;
	}
}
