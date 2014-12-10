package nairn.solitaire;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import nairn.solitaire.grid.SolitaireGrid;
import nairn.solitaire.move.Move;

public class Solver {

	public static void main(final String[] args) {
		final Solver solver = new Solver();

		final List<List<Move>> solutions = solver.solve(2, true);
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

	public List<List<Move>> solve(final int armLength, final boolean stopOnFirstCompletedPath) {
		final SolitaireGrid grid = new SolitaireGrid(armLength);
		final Set<SolitaireGrid> visitedStates = Sets.newHashSet(grid);
		final List<Move> path = Lists.newArrayList();
		final List<List<Move>> completedPaths = Lists.newArrayList();

		final SolverThreadExecutor solverThreadExecutor = new SolverThreadExecutor(grid, visitedStates, path, completedPaths, stopOnFirstCompletedPath);
		solverThreadExecutor.run();
		try {
			solverThreadExecutor.join();
		} catch(final InterruptedException e) {
			System.out.println("Solver thread executor interrupted: " + e);
			System.exit(-1);
		}

		return completedPaths;
	}

	private void solve(final SolitaireGrid grid, final Set<SolitaireGrid> visitedStates,
	                          final List<Move> path, final List<List<Move>> completedPaths,
	                          final boolean stopOnFirstCompletedPath) {

		if(!stopOnFirstCompletedPath || completedPaths.isEmpty()) {
			if (grid.isComplete()) {
				synchronized (completedPaths) {
					completedPaths.add(path);
				}

			} else {
				final List<Move> validMoves = grid.getValidMoves();
				attemptMoves(grid, visitedStates, path, completedPaths, validMoves, stopOnFirstCompletedPath);
			}
		}
	}

	private void attemptMoves(final SolitaireGrid grid, final Set<SolitaireGrid> visitedStates,
	                                 final List<Move> path, final List<List<Move>> completedPaths,
	                                 final List<Move> movesToAttempt, final boolean stopOnFirstCompletedPath) {

		for (final Move move : movesToAttempt) {
			solveForMove(grid, visitedStates, path, completedPaths, move, stopOnFirstCompletedPath);
		}
	}

	private void solveForMove(final SolitaireGrid grid, final Set<SolitaireGrid> visitedStates,
	                                 final List<Move> path, final List<List<Move>> completedPaths,
	                                 final Move move, final boolean stopOnFirstCompletedPath) {

		final SolitaireGrid newGrid = grid.move(move);
		final List<Move> newPath;

		synchronized (visitedStates) {
			if (!visitedStates.contains(newGrid)) {
				newPath = Lists.newArrayList(path);
				newPath.add(move);
				visitedStates.add(newGrid);
			} else {
				newPath=null;
			}
		}

		if(newPath!=null) {
			solve(newGrid, visitedStates, newPath, completedPaths, stopOnFirstCompletedPath);
		}
	}

	private class SolverThreadExecutor extends Thread {
		private SolitaireGrid initialGrid;
		private Set<SolitaireGrid> visitedStates;
		private List<Move> initialPath;
		private List<List<Move>> completedPaths;
		private boolean stopOnFirstCompletedPath;

		public SolverThreadExecutor(final SolitaireGrid initialGrid, final Set<SolitaireGrid> visitedStates, final List<Move> initialPath, final List<List<Move>> completedPaths,
				final boolean stopOnFirstCompletedPath) {
			this.initialGrid = initialGrid;
			this.visitedStates = visitedStates;
			this.initialPath = initialPath;
			this.completedPaths = completedPaths;
			this.stopOnFirstCompletedPath = stopOnFirstCompletedPath;
		}

		@Override
		public void run() {
			final SolverThread solverThread = new SolverThread();
			solverThread.start();
			try {
				solverThread.join();
			} catch(final InterruptedException e) {
				System.out.println("Solver thread interrupted: " + e);
				System.exit(-1);
			}
		}

		private class SolverThread extends Thread {
			@Override
			public void run() {
				solve(initialGrid, visitedStates, initialPath, completedPaths, stopOnFirstCompletedPath);
			}
		}
	}
}
