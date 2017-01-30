package jigspuzzle.cucumber.steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import jigspuzzle.JigSPuzzle;
import org.assertj.core.api.Assertions;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.Robot;
import org.assertj.swing.data.Index;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.security.NoExitSecurityManagerInstaller;
import static org.assertj.swing.finder.WindowFinder.findDialog;
import static org.assertj.swing.finder.WindowFinder.findFrame;
import org.assertj.swing.fixture.AbstractWindowFixture;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

/**
 * Step definitions for handeling frames and dialogs.
 *
 * @author RoseTec
 */
public class WindowsSteps {

    /**
     * travis ci-server has a problem with the wirst window...
     */
    private static boolean first_start = true;

    private Robot robot;

    private FrameFixture puzzleWindow;

    private DialogFixture settingsWindow;

    private DialogFixture versionWindow;

    private static NoExitSecurityManagerInstaller noExitSecurityManagerInstaller;

    public WindowsSteps() {
    }

    @Before
    public void setUp() {
//        FailOnThreadViolationRepaintManager.install();

        noExitSecurityManagerInstaller = NoExitSecurityManagerInstaller.installNoExitSecurityManager();
    }

    @After
    public void tearDown() {
        if (robot != null) {
            robot.cleanUp();
            robot = null;
        }
        if (settingsWindow != null) {
            settingsWindow.cleanUp();
            settingsWindow = null;
        }
        if (puzzleWindow != null) {
            puzzleWindow.cleanUp();
            puzzleWindow = null;
        }
        if (versionWindow != null) {
            versionWindow.cleanUp();
            versionWindow = null;
        }
        if (noExitSecurityManagerInstaller != null) {
            JigSPuzzle.getInstance().exitProgram();
            noExitSecurityManagerInstaller.uninstall();
            noExitSecurityManagerInstaller = null;
        }
    }

    public AbstractWindowFixture getSettingsWindow() {
        return settingsWindow;
    }

    public AbstractWindowFixture getPuzzleWindow() {
        return puzzleWindow;
    }

    public AbstractWindowFixture getVersionWindow() {
        return versionWindow;
    }

    public Robot getRobot() {
        return robot;
    }

    // the program itself
    @When("^restart the program$")
    public void restart_program() {
        tearDown();
        setUp();
        on_puzzle_window();
    }
    // -- the program itself end

    // Puzzlearea
    @Given("^(?:that )?I am on the puzzle window$")
    public void on_puzzle_window() {
        robot = BasicRobot.robotWithCurrentAwtHierarchy();

        application(JigSPuzzle.class).start();
        if (first_start) {
            first_start = false;
            restart_program();
            return;
        }
        puzzleWindow = findFrame(new GenericTypeMatcher<Frame>(Frame.class) {
            @Override
            protected boolean isMatching(Frame frame) {
                return "JigSPuzzle".equals(frame.getTitle()) && frame.isShowing();
            }
        }).using(robot);
    }
    // -- Puzzlearea end

    // size of a window
    @Given("^(?:that )?the size of the puzzle window is: width=(\\d+)(?:px)?, height=(\\d+)(?:px)?$")
    public void size_of_puzzle_window(int width, int height) {
        resize_puzzle_window_to(width, height);
    }

    @When("^I resize the puzzle window to: width=(\\d+)(?:px)?, height=(\\d+)(?:px)?$")
    public void resize_puzzle_window_to(int width, int height) {
        puzzleWindow.resizeTo(new Dimension(width + 30, height + 90));
    }
    // -- size of a window end

    // Menu Bar
    @When("^I should (not )?see the menu$")
    public void resize_puzzle_window_to(String negation) {
        // get the menu bar
        JPopupMenu popup = (JPopupMenu) puzzleWindow.menuItem("puzzle-create").target().getParent();
        JMenu tmp = (JMenu) popup.getInvoker();
        JMenuBar menu = (JMenuBar) tmp.getParent();

        // testing
        if (negation != null) {
            Assertions.assertThat(menu.getHeight()).isEqualTo(0);
        } else {
            Assertions.assertThat(menu.getHeight()).isGreaterThan(10);
        }
    }
    // -- Menu Bar end

    // Settings window: appearance
    @Given("^(?:that )?I am on the appearance-settings window$")
    public void on_settings_window_appearance() {
        on_puzzle_window();
        open_appearence_settings_window();
    }

    @When("^I open the appearance-settings window$")
    public void open_appearence_settings_window() {
        puzzleWindow.menuItem("appearance-settings").click();
        settingsWindow = findDialog("settings").using(robot);
    }

    @Then("^I should see the appearance-settings window$")
    public void see_appearence_settings_window() {
        settingsWindow.tabbedPane("main-tabbed-pane").requireSelectedTab(Index.atIndex(0));
    }
    // -- Settings window: appearance end

    // Settings window: puzzle
    @Given("^(?:that )?I am on the puzzle-settings window$")
    public void on_settings_window_puzzle() {
        on_puzzle_window();
        open_puzzle_settings_window();
    }

    @When("^I open the puzzle-settings window$")
    public void open_puzzle_settings_window() {
        puzzleWindow.menuItem("puzzle-settings").click();
        settingsWindow = findDialog("settings").using(robot);
    }

    @Then("^I should see the puzzle-settings window$")
    public void see_puzzle_settings_window() {
        settingsWindow.tabbedPane("main-tabbed-pane").requireSelectedTab(Index.atIndex(1));
    }
    // -- Settings window: puzzle end

    // Fullscreen
    @When("^I trigger fullscreen mode")
    public void fullscreen_trigger() {
        puzzleWindow.menuItem("fullscreen").click();
    }

    @When("^I leave fullscreen mode")
    public void fullscreen_leave() {
        fullscreen_trigger();
    }

    @Then("^I should (not )?see the puzzlearea in fullsceen")
    public void see_fullscreen(String negation) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        if (negation != null) {
            Assertions.assertThat(puzzleWindow.target().getWidth()).isNotEqualTo(width);
            Assertions.assertThat(puzzleWindow.target().getHeight()).isNotEqualTo(height);
        } else {
            Assertions.assertThat(puzzleWindow.target().getWidth()).isEqualTo(width);
            Assertions.assertThat(puzzleWindow.target().getHeight()).isEqualTo(height);
        }
    }
    // -- Fullscreen end

    // Version window
    @When("^I check for a newer version$")
    public void check_newer_version() {
        puzzleWindow.menuItem("check-new-version").click();
        versionWindow = findDialog("version-window").using(robot);
    }
    // -- Version window: end
}
