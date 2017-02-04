package jigspuzzle.testutils.mockups;

import java.awt.Rectangle;
import java.awt.geom.Area;
import jigspuzzle.model.puzzle.Puzzle;
import jigspuzzle.model.puzzle.PuzzlepieceGroup;
import jigspuzzle.view.IPuzzleWindow;

/**
 * A dummy implementation of a puzzle window. All methods are empty.
 *
 * @author RoseTec
 */
public class DummyPuzzleWindow implements IPuzzleWindow {

    @Override
    public void bringToFront(PuzzlepieceGroup puzzlepieceGroup) {
    }

    @Override
    public void displayFatalError(String message) {
    }

    @Override
    public int getPuzzlepieceHeight() {
        return 100;
    }

    @Override
    public int getPuzzlepieceWidth() {
        return 100;
    }

    @Override
    public void showPuzzleWindow() {
    }

    @Override
    public void setNewPuzzle(Puzzle puzzle) {
    }

    @Override
    public Area getPuzzleareaBounds() {
        return new Area(new Rectangle(0, 0, 1680, 1050));
    }

}
