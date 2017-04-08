package nairn.solitaire.grid;

import com.google.common.collect.Lists;
import nairn.solitaire.move.Direction;
import nairn.solitaire.move.Move;

import java.util.List;

public class SolitaireGrid extends SquareGrid {
	private final int armLength;

	public SolitaireGrid(final int armLength) {
		super(2 * armLength + 3);
		this.armLength = armLength;
		initialiseSolitaireGrid();
	}

	private SolitaireGrid(final SolitaireGrid grid) {
		super(grid);
		this.armLength = (width - 3) / 2;
	}

	SolitaireGrid move(final int x, final int y, final Direction direction) {
		final SolitaireGrid updatedGrid;

		if(moveIsValid(x, y, direction)) {
			final int newX = getNewX(x, direction);
			final int newY = getNewY(y, direction);

			updatedGrid = new SolitaireGrid(this);
			updatedGrid.set(x, y, false);
			updatedGrid.set((x + newX) / 2, (y + newY) / 2, false);
			updatedGrid.set(newX, newY, true);

		} else {
			updatedGrid = this;
		}

		return updatedGrid;
	}

	public SolitaireGrid move(final Move move) {
		return move(move.getX(), move.getY(), move.getDirection());
	}

	boolean moveIsValid(final int x, final int y, final Direction direction) {
		final boolean moveIsValid;

		if (x < 0 || x >= width || y < 0 || y >= width) {
			moveIsValid = false;
		} else {
			final Boolean originalPos = get(x, y);
			if (originalPos != null && originalPos) {
				final int newX = getNewX(x, direction);
				final int newY = getNewY(y, direction);

				if (newX < 0 || newX >= width || newY < 0 || newY >= width) {
					moveIsValid = false;

				} else {
					final Boolean newPos = get(newX, newY);
					if (newPos != null && !newPos) {
						moveIsValid = get((x + newX) / 2, (y + newY) / 2);

					} else {
						moveIsValid = false;
					}
				}

			} else {
				moveIsValid = false;
			}
		}
		return moveIsValid;
	}

	boolean moveIsValid(final Move move) {
	    return moveIsValid(move.getX(), move.getY(), move.getDirection());
    }

	boolean hasValidMove(final int x, final int y) {
		return moveIsValid(x,y,Direction.UP) || moveIsValid(x,y,Direction.DOWN) || moveIsValid(x,y,Direction.LEFT) ||
				moveIsValid(x,y,Direction.RIGHT);
	}

	public boolean isComplete() {
		boolean isComplete = true;
		final int centre = width/2;

		if(get(centre, centre)) {
			RowLoop: for (int y = 0; y < armLength; y++) {
				for (int x = armLength; x < armLength + 3; x++) {
					if (get(x, y) || get(x, width - y - 1)) {
						isComplete = false;
						break RowLoop;
					}
				}
			}
			final int armLengthPlus2 = armLength + 2;
			TopBottomOfMiddleRowLoop: for(int x = 0; x < width; x++) {
				if(get(x, armLength) || get(x, armLengthPlus2)) {
					isComplete = false;
					break TopBottomOfMiddleRowLoop;
				}
			}
			final int armLengthPlus1 = armLength + 1;
			MiddleRowLoop: for(int x = 0; x < centre; x++) {
				if(get(x, armLengthPlus1) || get(width-x-1,armLengthPlus1)) {
					isComplete = false;
					break MiddleRowLoop;
				}
			}
		} else {
			isComplete = false;
		}
		return isComplete;
	}

	boolean hasValidMoves() {
		boolean hasValidMoves = false;

		RowLoop: for(int y = 0; y < width; y++) {
			for(int x = 0; x < width; x++) {
				if(hasValidMove(x,y)) {
					hasValidMoves = true;
					break RowLoop;
				}
			}
		}
		return hasValidMoves;
	}

	public List<Move> getValidMoves() {
		final List<Move> validMoves = Lists.newArrayList();

		for(int y = 0; y < width; y++) {
			for(int x = 0; x < width; x++) {
				validMoves.addAll(createValidMoves(x,y));
			}
		}

		return validMoves;
	}

	private List<Move> createValidMoves(final int x, final int y) {
		final List<Move> moves = Lists.newArrayList();

		addMoveIfValid(moves, x, y, Direction.UP);
		addMoveIfValid(moves, x, y, Direction.DOWN);
		addMoveIfValid(moves, x, y, Direction.LEFT);
		addMoveIfValid(moves, x, y, Direction.RIGHT);

		return moves;
	}

	private void addMoveIfValid(final List<Move> moves, final int x, final int y, final Direction direction) {
		final Move move = createMoveIfValid(x,y,direction);
		if(move != null) {
			moves.add(move);
		}
	}

	private Move createMoveIfValid(final int x, final int y, final Direction direction) {
		final Move move;
		if(moveIsValid(x, y, direction)) {
			move = new Move(x,y,direction);
		} else {
			move = null;
		}
		return move;
	}

	int countPegs() {
		int numberOfPegs = 0;

		for(int y = 0; y < width; y++) {
			for(int x = 0; x < width; x++) {
				final Boolean pos = get(x,y);
				if(pos!= null && pos) {
					numberOfPegs++;
				}
			}
		}

		return numberOfPegs;
	}

	private int getNewX(final int x, final Direction direction) {
		final int newX;
		switch (direction) {
			case UP:
			case DOWN:
				newX = x;
				break;
			case LEFT:
				newX = x - 2;
				break;
			case RIGHT:
				newX = x + 2;
				break;
			default:
				throw new IllegalStateException("Invalid direction " + direction + " entered");
		}
		return newX;
	}

	private int getNewY(final int y, final Direction direction) {
		final int newY;
		switch (direction) {
			case UP:
				newY = y - 2;
				break;
			case DOWN:
				newY = y + 2;
				break;
			case LEFT:
			case RIGHT:
				newY = y;
				break;
			default:
				throw new IllegalStateException("Invalid direction " + direction + " entered");
		}
		return newY;
	}

	private void initialiseSolitaireGrid() {
		for (int y = 0; y < armLength; y++) {
			setupNorthSouthArm(y);
		}
		setupFullEastWestArm(armLength);
		setupMiddleRow();
		setupFullEastWestArm(armLength + 2);
		for (int y = armLength + 3; y < width; y++) {
			setupNorthSouthArm(y);
		}
	}

	private void setupNorthSouthArm(final int y) {
		int x = 0;
		for (; x < armLength; x++) {
			set(x, y, null);
		}
		for (; x < armLength + 3; x++) {
			set(x, y, true);
		}
		for (; x < width; x++) {
			set(x, y, null);
		}
	}

	private void setupFullEastWestArm(final int y) {
		for (int x = 0; x < width; x++) {
			set(x, y, true);
		}
	}

	private void setupMiddleRow() {
		final int y = armLength + 1;
		for (int x = 0; x < y; x++) {
			set(x, y, true);
		}
        //noinspection SuspiciousNameCombination -- this is valid: the centre of the grid is empty to begin with
        set(y, y, false);
		for (int x = y + 1; x < width; x++) {
			set(x, y, true);
		}
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final SolitaireGrid that = (SolitaireGrid) o;
		return armLength == that.armLength && toString().equals(that.toString());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
