package nairn.solitaire.grid;

import com.google.common.collect.Lists;
import nairn.solitaire.move.Move;

import java.util.Collections;
import java.util.List;

public class SolitaireGridWithMoves {
    private SolitaireGrid grid;
    private List<Move> moves;

    private SolitaireGridWithMoves(final SolitaireGrid grid, final List<Move> moves) {
        this.grid = grid;
        this.moves = Lists.newArrayList(moves);
    }

    public SolitaireGridWithMoves(final int armLength) {
        this.grid = new SolitaireGrid(armLength);
        this.moves = Lists.newArrayList();
    }

    public SolitaireGrid getGrid() {
        return grid;
    }

    public List<Move> getMoves() {
        return Collections.unmodifiableList(moves);
    }

    public SolitaireGridWithMoves move(final Move move) {
        if (!grid.moveIsValid(move)) {
            throw new IllegalStateException("Move " + move + " applied to " + grid + " is invalid");
        }

        final SolitaireGridWithMoves newGrid = new SolitaireGridWithMoves(grid.move(move), moves);
        newGrid.moves.add(move);

        return newGrid;
    }
}
