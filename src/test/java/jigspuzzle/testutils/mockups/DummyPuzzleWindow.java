package jigspuzzle.testutils.mockups;

import java.awt.Rectangle;
import jigspuzzle.model.puzzle.Puzzle;
import jigspuzzle.model.puzzle.PuzzlepieceGroup;
import jigspuzzle.view.IPuzzleWindow;

/**
 * A dummy implementation of a puzzle window. All methods are empty.
 *
 * @author RoseTec
 */
public class DummyPuzzleWindow implements IPuzzleWindow {

    int puzzleareaWidth;
    int puzzleareaHeight;

    public DummyPuzzleWindow() {
        this(1680, 1050);
    }

    public DummyPuzzleWindow(int puzzleareaWidth, int puzzleareaHeight) {
        this.puzzleareaWidth = puzzleareaWidth;
        this.puzzleareaHeight = puzzleareaHeight;
    }

    @Override
    public void bringToFront(PuzzlepieceGroup puzzlepieceGroup) {
    }

    @Override
    public void displayFatalError(String message) {
    }

    @Override
    public Rectangle[] getPuzzleareaBounds() {
        return new Rectangle[]{new Rectangle(0, 0, puzzleareaWidth, puzzleareaHeight)};
    }

    @Override
    public void showPuzzleWindow() {
    }

    @Override
    public void setNewPuzzle(Puzzle puzzle) {
    }

}
