package jigspuzzle.testutils.mockups;

import java.awt.Rectangle;
import jigspuzzle.model.puzzle.Puzzle;
import jigspuzzle.model.puzzle.PuzzlepieceGroup;
import jigspuzzle.view.IPuzzleWindow;

/**
 * A dummy implementation of a puzzle window, that can spread over several
 * monitors. All methods are empty.
 *
 * @author RoseTec
 */
public class DummyMultiMonitorPuzzleWindow implements IPuzzleWindow {

    Rectangle[] screens;

    public DummyMultiMonitorPuzzleWindow(Rectangle[] screens) {
        this.screens = screens;
    }

    @Override
    public void bringToFront(PuzzlepieceGroup puzzlepieceGroup) {
    }

    @Override
    public void displayFatalError(String message) {
    }

    @Override
    public Rectangle[] getPuzzleareaBounds() {
        return screens;
    }

    @Override
    public void showPuzzleWindow() {
    }

    @Override
    public void setNewPuzzle(Puzzle puzzle) {
    }

}
