package nairn.solitaire;

import com.google.common.collect.Lists;
import nairn.solitaire.grid.SolitaireGridWithMoves;
import nairn.solitaire.move.Move;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.min;

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
        final List<List<Move>> completedPaths = Collections.synchronizedList(Lists.newArrayList());
        final int pathsToProcessEachStep = 100;

        while (!pathQueue.isEmpty()) {
            final int numPathsToProcess = min(pathsToProcessEachStep, pathQueue.size());
            final List<SolitaireGridWithMoves> pathsToProcess = pathQueue.subList(0, numPathsToProcess);

            final List<SolitaireGridWithMoves> processedPaths = pathsToProcess.parallelStream().flatMap(path ->
                path.getGrid().getValidMoves().parallelStream().map(move -> {
                    final SolitaireGridWithMoves nextPath = path.move(move);
                    if (nextPath.getGrid().isComplete()) {
                        completedPaths.add(nextPath.getMoves());
                    }
                    return nextPath;
                })
            ).collect(Collectors.toList());

            if (stopOnFirstCompletedPath && !completedPaths.isEmpty()) {
                return completedPaths.subList(0, 1);
            }

            final List<SolitaireGridWithMoves> unprocessedPaths = pathQueue.subList(numPathsToProcess, pathQueue.size());
            pathQueue = Lists.newArrayList();
            Stream.of(processedPaths, unprocessedPaths).forEach(pathQueue::addAll);
        }
        return completedPaths;
    }

    private List<List<Move>> solve(final int armLength) {
        final SolitaireGridWithMoves initialState = new SolitaireGridWithMoves(armLength);
        return solve(initialState, stopOnFirstCompletedPath);
    }
}
