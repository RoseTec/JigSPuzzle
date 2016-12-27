package jigspuzzle.view.desktop;

import jigspuzzle.model.puzzle.Puzzle;
import jigspuzzle.model.puzzle.PuzzlepieceGroup;
import jigspuzzle.view.IPuzzleWindow;
import jigspuzzle.view.desktop.puzzle.DesktopPuzzleMainWindow;
import jigspuzzle.view.desktop.settings.SettingsWindow;

/**
 * A class that manages the desktop version of JigSPuzzle
 *
 * @author RoseTec
 */
public class DesktopPuzzleWindow implements IPuzzleWindow {

    /**
     * The main window, where the user can puzzle.
     */
    private final DesktopPuzzleMainWindow mainWindow;

    /**
     * The window in that the user can change the settings
     */
    private final SettingsWindow settingsWindow;

    public DesktopPuzzleWindow() {
        mainWindow = new DesktopPuzzleMainWindow(this);
        settingsWindow = new SettingsWindow(mainWindow, false);
    }

    @Override
    public void bringToFront(PuzzlepieceGroup puzzlepieceGroup) {
        mainWindow.bringToFront(puzzlepieceGroup);
    }

    @Override
    public int getPuzzlepieceHeight() {
        return mainWindow.getPuzzlepieceHeight();
    }

    @Override
    public int getPuzzlepieceWidth() {
        return mainWindow.getPuzzlepieceWidth();
    }

    /**
     * @see SettingsWindow#showAppearanceSettings()
     */
    public void showAppearanceSettings() {
        settingsWindow.showAppearanceSettings();
    }

    /**
     * @see SettingsWindow#showPuzzleSettings()
     */
    public void showPuzzleSettings() {
        settingsWindow.showPuzzleSettings();
    }

    @Override
    public void showPuzzleWindow() {
        mainWindow.showPuzzleWindow();
    }

    @Override
    public void setNewPuzzle(Puzzle puzzle) {
        mainWindow.setNewPuzzle(puzzle);
    }

}
