package jigspuzzle.view.desktop;

import javax.swing.JOptionPane;
import jigspuzzle.model.puzzle.Puzzle;
import jigspuzzle.model.puzzle.PuzzlepieceGroup;
import jigspuzzle.view.IPuzzleWindow;
import jigspuzzle.view.desktop.puzzle.DesktopPuzzleMainWindow;
import jigspuzzle.view.desktop.settings.SettingsWindow;
import jigspuzzle.view.desktop.version.VersionCheckDialog;

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
     * The window in that the user can change the settings.
     */
    private final SettingsWindow settingsWindow;

    /**
     * The window in that the user can check the current version.
     */
    private final VersionCheckDialog versionCheckWindow;

    public DesktopPuzzleWindow() {
        mainWindow = new DesktopPuzzleMainWindow(this);
        settingsWindow = new SettingsWindow(mainWindow, false);
        versionCheckWindow = new VersionCheckDialog(mainWindow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bringToFront(PuzzlepieceGroup puzzlepieceGroup) {
        mainWindow.bringToFront(puzzlepieceGroup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayFatalError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPuzzlepieceHeight() {
        return mainWindow.getPuzzlepieceHeight();
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPuzzleWindow() {
        mainWindow.showPuzzleWindow();
    }

    /**
     * @see VersionCheckDialog#showVersionCheckWindow()
     */
    public void showVersionCheckWindow() {
        versionCheckWindow.showVersionCheckWindow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNewPuzzle(Puzzle puzzle) {
        mainWindow.setNewPuzzle(puzzle);
    }

}
