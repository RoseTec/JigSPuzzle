package jigspuzzle.cucumber.steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import java.awt.Color;
import java.io.File;
import javax.swing.JColorChooser;
import jigspuzzle.controller.SettingsController;
import jigspuzzle.cucumber.steps.util.ColorSteps;
import org.assertj.core.api.Assertions;
import org.assertj.swing.fixture.AbstractWindowFixture;
import org.assertj.swing.fixture.DialogFixture;

/**
 * Step definitions for handeling the in- and output for settings values in the
 * settings window.
 *
 * @author RoseTec
 */
public class SettingsSteps {

    private final WindowsSteps windowsSteps;

    public SettingsSteps(WindowsSteps windowsSteps) {
        this.windowsSteps = windowsSteps;
    }

    @Before
    public void setUp() {
        have_no_settings();
    }

    @After
    public void tearDown() {
        have_no_settings();
    }

    @Given("^(?:that )?I have no settings set up to now$")
    public void have_no_settings() {
        File settingsFile = new File(SettingsController.SETTINGS_FILE_NAME);

        if (settingsFile.isFile()) {
            settingsFile.delete();
        }
    }

    // Changing language
    private String getLanguageInLanguage(String language) {
        switch (language) {
            case "german":
                return "deutsch";
            case "english":
                return "english";
            default:
                return "";
        }
    }

    @When("^I change the language to \"([^\"]*)\"$")
    public void change_language_to(String language) {
        get_settings_window().comboBox("language-select").selectItem(getLanguageInLanguage(language));
    }

    @Then("^I should see the user interface in the language \"([^\"]*)\"$")
    public void should_see_language(String language) {
        get_settings_window().comboBox("language-select").requireSelection(getLanguageInLanguage(language));

        String settingsText;
        switch (language) {
            case "german":
                settingsText = "Einstellungen";
                break;
            default:
                settingsText = "Settings";
        }
        Assertions.assertThat(get_settings_window().target().getTitle()).isEqualTo(settingsText);
    }
    // --Changing language end

    // puzzleare background color
    @When("^I change the background color of the puzzlearea to \"([^\"]*)\"$")
    public void change_background_color_of_puzzlearea(String colorString) throws NoSuchMethodException {
        Color color = ColorSteps.getColorFromString(colorString);

        // yeah, it is a hack with the color-chooser
        JColorChooser chooser = (JColorChooser) get_settings_window().panel("puzzelarea-background-color").target().getParent().getParent().getComponents()[0];
        chooser.setColor(color);
    }
    // -- puzzleare background color end

    // number of puzzlepieces
    @When("^I change the number of puzzlepieces to \"(\\d+)\"$")
    public void change_number_of_puzzlepieces(int number) {
        get_settings_window().textBox("puzzlepiece-number-textfield").deleteText();
        get_settings_window().textBox("puzzlepiece-number-textfield").enterText(Integer.toString(number));
    }

    @Then("^I should see the number of puzzlepieces in the settings window to be \"(\\d+)\"$")
    public void number_of_puzzlepieces_should_be(int number) {
        int valueInSlider = get_settings_window().slider("puzzlepiece-number-slider").target().getValue();

        get_settings_window().textBox("puzzlepiece-number-textfield").requireText(Integer.toString(number));
        Assertions.assertThat(valueInSlider).isEqualTo(number);
    }
    // -- number of puzzlepieces end

    @When("^I save the settings$")
    public void save_settings() {
        get_settings_window().button("settings-save").click();
    }

    @When("^I cancel the settings window without saving$")
    public void cancel_settings() {
        get_settings_window().button("settings-cancel").click();
    }

    private DialogFixture get_settings_window() {
        return (DialogFixture) windowsSteps.getSettingsWindow();
    }

    private AbstractWindowFixture get_puzzle_window() {
        return windowsSteps.getPuzzleWindow();
    }
}
