package nairn.solitaire;

import com.google.common.collect.Lists;
import nairn.solitaire.grid.SolitaireGrid;
import nairn.solitaire.grid.SolitaireGridWithMoves;
import nairn.solitaire.move.Move;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import java.util.List;

public class Solver {
    private static final XLogger logger = XLoggerFactory.getXLogger(Solver.class);
    
    private final boolean stopOnFirstCompletedPath;

    private Solver(final boolean stopOnFirstCompletedPath) {
        logger.debug("Creating Solver object; stopOnFirstCompletedPath = {}", stopOnFirstCompletedPath);
        this.stopOnFirstCompletedPath = stopOnFirstCompletedPath;
    }

    public static void main(final String[] args) {
        logger.info("Starting Solver.main");

        final Solver solver = new Solver(true);
        final List<List<Move>> solutions = solver.solve(2);
        System.out.println(solver.describeNumberOfSolutions(solutions) + ":\n");

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

    private String describeNumberOfSolutions(final List<List<Move>> solutions) {
        final int numSolutions = solutions.size();
        return String.valueOf(numSolutions) + " solution" + (numSolutions != 1 ? "s" : "") + " found";
    }

    private List<List<Move>> solve(final SolitaireGridWithMoves initialState, final boolean stopOnFirstCompletedPath) {
        List<SolitaireGridWithMoves> pathQueue = Lists.newArrayList(initialState);
        final List<List<Move>> completedPaths = Lists.newArrayList();

        while (!pathQueue.isEmpty()) {
            final List<SolitaireGridWithMoves> newPathQueue = Lists.newArrayList();

            final SolitaireGridWithMoves firstPath = pathQueue.get(0);
            final SolitaireGrid grid = firstPath.getGrid();
            if (grid.isComplete()) {
                completedPaths.add(firstPath.getMoves());
                if (stopOnFirstCompletedPath) {
                    return completedPaths;
                }
            } else {
                final List<Move> validMoves = grid.getValidMoves();
                for (final Move validMove : validMoves) {
                    final SolitaireGridWithMoves nextPath = firstPath.move(validMove);
                    newPathQueue.add(nextPath);
                }
            }
            newPathQueue.addAll(pathQueue.subList(1, pathQueue.size()));
            pathQueue = newPathQueue;
        }
        return completedPaths;
    }

    private List<List<Move>> solve(final int armLength) {
        final SolitaireGridWithMoves initialState = new SolitaireGridWithMoves(armLength);
        return solve(initialState, stopOnFirstCompletedPath);
    }
}
