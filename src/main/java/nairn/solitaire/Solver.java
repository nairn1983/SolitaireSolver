package nairn.solitaire;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import nairn.solitaire.grid.SolitaireGrid;
import nairn.solitaire.move.Move;

import java.util.List;
import java.util.Set;

public final class Solver {

	public static void main(final String[] args) {
		final List<List<Move>> solutions = solve(2, true);
		final int numSolutions = solutions.size();

		final StringBuilder sb = new StringBuilder();
		sb.append(numSolutions);
		sb.append(" solution");
		if(numSolutions != 1) {
			sb.append("s");
		}
		sb.append(" found:\n");
		System.out.println(sb.toString());

		for (final List<Move> moves : solutions) {
			boolean firstElement = true;
			for (final Move move : moves) {
				if (!firstElement) {
					System.out.print(", ");
				} else {
					firstElement = false;
				}
				System.out.print(move);
			}
			System.out.println("\n");
		}
	}

	public static List<List<Move>> solve(final int armLength, final boolean stopOnFirstCompletedPath) {
		final SolitaireGrid grid = new SolitaireGrid(armLength);
		final Set<SolitaireGrid> visitedStates = Sets.newHashSet(grid);
		final List<Move> path = Lists.newArrayList();
		final List<List<Move>> completedPaths = Lists.newArrayList();

		solve(grid, visitedStates, path, completedPaths, stopOnFirstCompletedPath);
		return completedPaths;
	}

	private static void solve(final SolitaireGrid grid, final Set<SolitaireGrid> visitedStates,
	                          final List<Move> path, final List<List<Move>> completedPaths,
	                          final boolean stopOnFirstCompletedPath) {

		if(!stopOnFirstCompletedPath || completedPaths.isEmpty()) {
			if (grid.isComplete()) {
				completedPaths.add(path);

			} else {
				final List<Move> validMoves = grid.getValidMoves();
				attemptMoves(grid, visitedStates, path, completedPaths, validMoves, stopOnFirstCompletedPath);
			}
		}
	}

	private static void attemptMoves(final SolitaireGrid grid, final Set<SolitaireGrid> visitedStates,
	                                 final List<Move> path, final List<List<Move>> completedPaths,
	                                 final List<Move> movesToAttempt, final boolean stopOnFirstCompletedPath) {

		for (final Move move : movesToAttempt) {
			solveForMove(grid, visitedStates, path, completedPaths, move, stopOnFirstCompletedPath);
		}
	}

	private static void solveForMove(final SolitaireGrid grid, final Set<SolitaireGrid> visitedStates,
	                                 final List<Move> path, final List<List<Move>> completedPaths,
	                                 final Move move, final boolean stopOnFirstCompletedPath) {

		final SolitaireGrid newGrid = grid.move(move);
		if (!visitedStates.contains(newGrid)) {
			final List<Move> newPath = Lists.newArrayList(path);
			newPath.add(move);
			visitedStates.add(newGrid);

			solve(newGrid, visitedStates, newPath, completedPaths, stopOnFirstCompletedPath);
		}
	}
}
