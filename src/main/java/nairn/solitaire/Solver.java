package nairn.solitaire;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import nairn.solitaire.grid.SolitaireGrid;
import nairn.solitaire.move.Move;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import java.util.List;
import java.util.Set;

public class Solver {
	private static final XLogger logger = XLoggerFactory.getXLogger(Solver.class);

	private static final int MAX_NUM_ACTIVE_SOLVER_THREADS = 5;
	private final Set<SolveForMoveThread> activeSolverThreads = Sets.newHashSet();
	private final List<List<Move>> completedPaths = Lists.newArrayList();
	private final Set<SolitaireGrid> visitedStates = Sets.newHashSet();
	private final boolean stopOnFirstCompletedPath;

	private Solver(final boolean stopOnFirstCompletedPath) {
		logger.debug("Creating Solver object; stopOnFirstCompletedPath = {}", stopOnFirstCompletedPath);
		this.stopOnFirstCompletedPath = stopOnFirstCompletedPath;
	}

	public static void main(final String[] args) {
		logger.info("Starting Solver.main");

		final Solver solver = new Solver(true);

		solver.solve(2);
		final List<List<Move>> solutions = solver.getCompletedPaths();
		final int numSolutions = solutions.size();

		final StringBuilder sb = new StringBuilder();
		sb.append(numSolutions);
		sb.append(" solution");
		if (numSolutions != 1) {
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

	private void solve(final int armLength) {
		final SolitaireGrid grid = new SolitaireGrid(armLength);
		visitedStates.add(grid);
		final List<Move> path = Lists.newArrayList();

		solve(grid, path, null);
	}

	private synchronized List<List<Move>> getCompletedPaths() {
		return Lists.newArrayList(completedPaths);
	}

	private void solve(final SolitaireGrid grid, final List<Move> path, final SolveForMoveThread solveForMoveThread) {

		if (!stopOnFirstCompletedPath || completedPaths.isEmpty()) {
			if (grid.isComplete()) {
				synchronized (completedPaths) {
					completedPaths.add(path);
				}

				if (solveForMoveThread != null) {
					synchronized (activeSolverThreads) {
						activeSolverThreads.remove(solveForMoveThread);
					}
				}

			} else {
				final List<Move> validMoves = grid.getValidMoves();
				attemptMoves(grid, path, validMoves, solveForMoveThread);
			}
		}
	}

	private void attemptMoves(final SolitaireGrid grid, final List<Move> path, final List<Move> movesToAttempt, final SolveForMoveThread solveForMoveThread) {

		for (final Move move : movesToAttempt) {
			createAndRunSolveForMoveThread(grid, path, move, solveForMoveThread);
		}
	}

	private void createAndRunSolveForMoveThread(final SolitaireGrid grid, final List<Move> path, final Move move, final SolveForMoveThread existingSolveForMoveThread) {

		final SolveForMoveThread solveForMoveThread;
		synchronized (activeSolverThreads) {
			if (activeSolverThreads.size() < MAX_NUM_ACTIVE_SOLVER_THREADS) {
				solveForMoveThread = new SolveForMoveThread(grid, path, move);
				activeSolverThreads.add(solveForMoveThread);

			} else {
				solveForMoveThread = null;
			}
		}

		if (solveForMoveThread != null) {
			solveForMoveThread.run();
			try {
				solveForMoveThread.join();
			} catch (InterruptedException e) {
				System.exit(-1);
			}
			synchronized (activeSolverThreads) {
				activeSolverThreads.remove(solveForMoveThread);
			}

		} else {
			solveForMove(grid, path, move, existingSolveForMoveThread);
		}
	}

	private void solveForMove(final SolitaireGrid grid, final List<Move> path, final Move move, final SolveForMoveThread solveForMoveThread) {

		final SolitaireGrid newGrid = grid.move(move);
		final List<Move> newPath;

		synchronized (visitedStates) {
			if (!visitedStates.contains(newGrid)) {
				newPath = Lists.newArrayList(path);
				newPath.add(move);
				visitedStates.add(newGrid);
			} else {
				newPath = null;
			}
		}

		if (newPath != null) {
			solve(newGrid, newPath, solveForMoveThread);
		}
	}

	private class SolveForMoveThread extends Thread {
		private SolitaireGrid initialGrid;
		private List<Move> initialPath;
		private Move initialMove;

		SolveForMoveThread(final SolitaireGrid initialGrid, final List<Move> initialPath, final Move initialMove) {
			this.initialGrid = initialGrid;
			this.initialPath = initialPath;
			this.initialMove = initialMove;
		}

		@Override
		public void run() {
			solveForMove(initialGrid, initialPath, initialMove, this);
		}
	}
}
