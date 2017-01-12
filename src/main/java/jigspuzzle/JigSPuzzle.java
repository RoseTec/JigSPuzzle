package jigspuzzle;

import jigspuzzle.controller.*;
import jigspuzzle.view.IPuzzleWindow;
import jigspuzzle.view.ISoundPlayer;
import jigspuzzle.view.PuzzleWindow;
import jigspuzzle.view.SoundPlayer;

/**
 * The main class to start JigSPuzzle
 *
 * @author RoseTec
 */
public class JigSPuzzle {

    private static JigSPuzzle instance;

    public static JigSPuzzle getInstance() {
        if (instance == null) {
            instance = new JigSPuzzle();
        }
        return instance;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        getInstance().startGame();
    }

    /**
     * The main window, in which the user can play
     */
    IPuzzleWindow puzzleWindow;

    /**
     * The sound player, that can play sounds in the UI.
     */
    ISoundPlayer soundPlayer;

    private JigSPuzzle() {
        puzzleWindow = new PuzzleWindow();
        soundPlayer = new SoundPlayer();
    }

    /**
     * Exits the complete program
     */
    public void exitProgram() {
        try {
            System.exit(0);
        } catch (Exception ex) {
            // in the assertJ - tests it is not possible to exit...
            // instead, we 'reset' this class and the controller.
            instance = null;
            PuzzleController.getInstance().resetInstance();
            SettingsController.getInstance().resetInstance();
            VersionController.getInstance().resetInstance();
        }
    }

    /**
     * Returns the window on that the user can puzzle.
     *
     * @return
     */
    public IPuzzleWindow getPuzzleWindow() {
        return puzzleWindow;
    }

    /**
     * Returns the player, that is used for playing sounds on the UI.
     *
     * @return
     */
    public ISoundPlayer getSoundPlayer() {
        return soundPlayer;
    }

    /**
     * Sets the window on that the user can puzzle.
     *
     * Use only, in special cases, e.g. tests.
     *
     * @param puzzleWindow
     */
    public void setPuzzleWindow(IPuzzleWindow puzzleWindow) {
        this.puzzleWindow = puzzleWindow;
    }

    /**
     * Starts the game and shows the user the UI.
     */
    private void startGame() {
        puzzleWindow.showPuzzleWindow();
    }
}
