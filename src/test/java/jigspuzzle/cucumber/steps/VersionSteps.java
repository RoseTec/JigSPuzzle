package jigspuzzle.cucumber.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.*;
import java.util.regex.Pattern;
import jigspuzzle.cucumber.RunCucumber;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.timing.Condition;
import org.assertj.swing.timing.Pause;
import org.assertj.swing.timing.Timeout;

/**
 * Step definitions for the input with versions.
 *
 * @author RoseTec
 */
public class VersionSteps {

    private final WindowsSteps windowsSteps;

    public VersionSteps(WindowsSteps windowsSteps) {
        this.windowsSteps = windowsSteps;
    }

    // controlling versions
    @Given("^a newer version is available on the internet$")
    public void new_version_avalable() {
        throw new PendingException();
    }

    @Given("^no newer version is available on the internet$")
    public void no_new_version_avalable() {
        // do nothing here, since the one in development is always newer then the released one
        //todo: find out how to do it right
    }
    // -- controlling versions end

    // viewing version results
    @Then("^I should see, that a newer version is available$")
    public void see_new_version_avalable() {
        get_version_window().label("this-version-number").requireText(Pattern.compile("[0-9]+(\\.[0-9]+)*(-SNAPSHOT)?"));
        Pause.pause(new Condition("notify-this-is-newest-version should be visible") {
            @Override
            public boolean test() {
                return GuiActionRunner.execute(get_version_window().button("navigate-to-new-version")::isEnabled);
            }
        }, RunCucumber.TIMEOUT_UI_UPDATED);
        get_version_window().button("navigate-to-new-version").requireEnabled();
        get_version_window().label("new-version-number").requireText(Pattern.compile("[0-9]+(\\.[0-9]+)*(-SNAPSHOT)?"));
    }

    @Then("^I should see, that this version is up to date$")
    public void see_no_new_version_avalable() {
        get_version_window().label("this-version-number").requireText(Pattern.compile("[0-9]+(\\.[0-9]+)*(-SNAPSHOT)?"));
        Pause.pause(new Condition("notify-this-is-newest-version should be visible") {
            @Override
            public boolean test() {
                try {
                    get_version_window().label(JLabelMatcher.withName("notify-this-is-newest-version").andShowing());
                    return true;
                } catch (ComponentLookupException ex) {
                    return false;
                }
            }
        }, RunCucumber.TIMEOUT_UI_UPDATED);
        get_version_window().label("new-version-number").requireText(Pattern.compile("[0-9]+(\\.[0-9]+)*(-SNAPSHOT)?"));
        get_version_window().label("notify-this-is-newest-version").requireVisible();
    }

    @Then("^I should be able to navigate to an internet link with the newer version$")
    public void naviagte_to_new_version() {
        get_version_window().button("navigate-to-new-version").requireEnabled();
        //todo: check, if clicking on the button opens a browser window with link to gitHub
        throw new PendingException();
    }
    // -- viewing version results end

    private DialogFixture get_version_window() {
        return (DialogFixture) windowsSteps.getVersionWindow();
    }

    private FrameFixture get_puzzle_window() {
        return (FrameFixture) windowsSteps.getPuzzleWindow();
    }

}
