package jigspuzzle.view;

import java.awt.Rectangle;
import java.awt.geom.Area;
import jigspuzzle.model.puzzle.Puzzle;
import jigspuzzle.model.puzzle.PuzzlepieceGroup;

/**
 * This is the main window for the game. It serves a a playground where the user
 * can play with puzzlepieces.
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
     * Displays a fatal error, because of that the program has to exit.
     *
     * This method can be called any time. EVen when no UI hs been loaded up to
     * now.
     *
     * When calling this method, it keeps active, until the message for the user
     * is closed. Means, a call to this method will not return until the user
     * closed the dialog. After that the program will probable be exited.
     *
     * @param message The text to be displayed.
     */
    public void displayFatalError(String message);

    /**
     * Gets an array of the bounds of the puzzlearea. Normally this is delegated
     * to return the bounds of the component that acts as the puzzlearea.
     *
     * If the puzzlearea consists of several monitors, the bounds of each
     * monitor will be added, such that this method returns an array of all
     * monitor bounds.
     *
     * @return
     */
    public Rectangle[] getPuzzleareaBounds();

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
