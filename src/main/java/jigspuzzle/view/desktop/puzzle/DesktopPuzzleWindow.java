package jigspuzzle.view.desktop.puzzle;

import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import jigspuzzle.controller.PuzzleController;
import jigspuzzle.controller.SettingsController;
import jigspuzzle.model.puzzle.Puzzle;
import jigspuzzle.model.puzzle.PuzzlepieceGroup;
import jigspuzzle.view.IPuzzleWindow;
import jigspuzzle.view.desktop.about.AboutDialog;
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
     * The windows, taht are used for fullscreen.
     */
    private DesktopPuzzleMainWindow[] fullscreenPuzzleWindows;

    /**
     * The window for detailed informations for JigSPuzzle.
     */
    private final AboutDialog aboutWidnow;

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
        settingsWindow = new SettingsWindow();
        aboutWidnow = new AboutDialog(mainWindow);
        versionCheckWindow = new VersionCheckDialog(mainWindow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bringToFront(PuzzlepieceGroup puzzlepieceGroup) {
        mainWindow.bringToFront(puzzlepieceGroup);
        if (fullscreenPuzzleWindows != null) {
            for (DesktopPuzzleMainWindow w : fullscreenPuzzleWindows) {
                w.bringToFront(puzzlepieceGroup);
            }
        }
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
    public Rectangle[] getPuzzleareaBounds() {
        if (isFullscreenActive()) {
            Rectangle[] ret = new Rectangle[fullscreenPuzzleWindows.length];

            for (int i = 0; i < fullscreenPuzzleWindows.length; i++) {
                ret[i] = fullscreenPuzzleWindows[i].getPuzzleareaBounds();
            }
            return ret;
        } else {
            return new Rectangle[]{mainWindow.getPuzzleareaBounds()};
        }
    }

    /**
     * Checks if the fullsceen is active.
     *
     * @return
     */
    boolean isFullscreenActive() {
        return fullscreenPuzzleWindows != null && fullscreenPuzzleWindows.length > 0;
    }

    /**
     * @see AboutDialog#showAboutWindow()
     */
    public void showAboutWindow() {
        aboutWidnow.showAboutWindow();
    }

    /**
     * @see SettingsWindow#showUiSettings()
     */
    public void showUiSettings() {
        settingsWindow.showUiSettings();
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
        if (isFullscreenActive()) {
            if (fullscreenPuzzleWindows != null) {
                for (DesktopPuzzleMainWindow w : fullscreenPuzzleWindows) {
                    w.showPuzzleWindow();
                }
            }
        } else {
            mainWindow.showPuzzleWindow();
        }
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
        if (fullscreenPuzzleWindows != null) {
            for (DesktopPuzzleMainWindow w : fullscreenPuzzleWindows) {
                w.setNewPuzzle(puzzle);
            }
        }
    }

    /**
     * Triggers this view to be on fullscreen.
     *
     * If the view is allready on fullscreen, the fullscreen is exited.
     */
    void triggerFullscreen() {
        // position windows
        if (isFullscreenActive()) {
            for (DesktopPuzzleMainWindow w : fullscreenPuzzleWindows) {
                w.dispose();
            }
            fullscreenPuzzleWindows = null;
            mainWindow.setVisible(true);

            mainWindow.fullscreenTriggered();
        } else {
            mainWindow.setVisible(false);

            // get monitors from the settings
            List<Integer> allMonitorIndex = SettingsController.getInstance().getMonitorsForFullscreen();

            // get all real monitors
            List<GraphicsDevice> monitorsForFullsceen = new ArrayList<>();
            GraphicsDevice[] allAvailableMonitors = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

            allAvailableMonitors = Arrays.copyOf(allAvailableMonitors, allAvailableMonitors.length); // copy, because, detektion of default monitor will brake otherwise
            Arrays.sort(allAvailableMonitors, (GraphicsDevice t, GraphicsDevice t1) -> {
                // sort the monitors depending on the x-coordinate
                int x1 = t.getDefaultConfiguration().getBounds().x;
                int x2 = t1.getDefaultConfiguration().getBounds().x;

                return Integer.compare(x1, x2);
            });

            // get the real monitors used becuse of the settings
            for (Integer monitorIndex : allMonitorIndex) {
                GraphicsDevice monitorToAdd = allAvailableMonitors[monitorIndex];

                monitorsForFullsceen.add(monitorToAdd);
            }

            // trigger fullscreen for the seleced monitors
            fullscreenPuzzleWindows = new DesktopPuzzleMainWindow[monitorsForFullsceen.size()];
            for (int i = 0; i < monitorsForFullsceen.size(); i++) {
                GraphicsDevice gd = monitorsForFullsceen.get(i);
                DesktopPuzzleMainWindow newWindow = new DesktopPuzzleMainWindow(this);

                newWindow.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        // When one window is focused back again, the other windows also should be in foreground, when they are not already
                        if (e.getOppositeComponent() == null) {
                            showPuzzleWindow();
                        }
                    }
                });
                newWindow.setPuzzleareaStart(gd.getDefaultConfiguration().getBounds().getLocation());
                newWindow.dispose();
                newWindow.setUndecorated(true);
                newWindow.setLocation(gd.getDefaultConfiguration().getBounds().getLocation());
                newWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
                newWindow.setVisible(true);
                fullscreenPuzzleWindows[i] = newWindow;

                newWindow.fullscreenTriggered();
                EventQueue.invokeLater(() -> { //invoke later, so that the window is finished with size before puzzlepieces are added
                    newWindow.setNewPuzzle(PuzzleController.getInstance().getPuzzle());
                });
            }
        }
    }

    /**
     * Gets the rectangles of the multiple monitors that are used for displaying
     * puzzlepieces on a puzzlearea.
     *
     * @return
     */
    private Rectangle[] getMultiMonitorRectangles() {
        if (fullscreenPuzzleWindows == null) {
            return null;
        }

        Rectangle[] ret = new Rectangle[fullscreenPuzzleWindows.length];

        for (int i = 0; i < fullscreenPuzzleWindows.length; i++) {
            DesktopPuzzleMainWindow monitor = fullscreenPuzzleWindows[i];

            ret[i] = monitor.getBounds();
        }
        return ret;
    }

}
