package jigspuzzle.view;

import javax.swing.UIManager;
import jigspuzzle.model.puzzle.Puzzle;
import jigspuzzle.model.puzzle.PuzzlepieceGroup;
import jigspuzzle.view.desktop.puzzle.DesktopPuzzleWindow;

/**
 * A class for the puzzlewindow used for puzzeling. All methods are delegated to
 * a 'real' implementation for the UI.
 *
 * @author RoseTec
 */
public class PuzzleWindow implements IPuzzleWindow {

    /**
     * The real implementaion of the ui.
     */
    private IPuzzleWindow puzzleWindow;

    public PuzzleWindow() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }

        puzzleWindow = new DesktopPuzzleWindow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bringToFront(PuzzlepieceGroup puzzlepieceGroup) {
        puzzleWindow.bringToFront(puzzlepieceGroup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPuzzlepieceHeight() {
        return puzzleWindow.getPuzzlepieceHeight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPuzzlepieceWidth() {
        return puzzleWindow.getPuzzlepieceWidth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPuzzleWindow() {
        puzzleWindow.showPuzzleWindow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNewPuzzle(Puzzle puzzle) {
        puzzleWindow.setNewPuzzle(puzzle);
    }

}
