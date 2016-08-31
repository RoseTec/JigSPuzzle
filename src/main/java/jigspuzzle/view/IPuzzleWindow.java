package jigspuzzle.view;

import jigspuzzle.model.puzzle.Puzzle;
import jigspuzzle.model.puzzle.PuzzlepieceGroup;

/**
 * This is the main window for the game. It serves a a plazground where the user
 * can plaz with puyylepieces.
 *
 * @author RoseTec
 */
public interface IPuzzleWindow {

    /**
     * Brings the given puzzlepiecegroup to the front, so that it will be
     * displayed above all other groups.
     *
     * @param puzzlepieceGroup
     */
    public void bringToFront(PuzzlepieceGroup puzzlepieceGroup);

    /**
     * Gets the height of one puzzlepiece in the puzzleare.
     *
     * @return
     */
    public int getPuzzlepieceHeight();

    /**
     * Gets the width of one puzzlepiece in the puzzleare.
     *
     * @return
     */
    public int getPuzzlepieceWidth();

    /**
     * Shows the settings window to the user.
     */
    public void showPuzzleWindow();

    /**
     * When a new puzzle is created, this method is called. It shows the new
     * puzzle on the UI.
     *
     * @param puzzle
     */
    public void setNewPuzzle(Puzzle puzzle);

}
