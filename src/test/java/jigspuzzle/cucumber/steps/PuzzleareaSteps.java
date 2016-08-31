package jigspuzzle.cucumber.steps;

import cucumber.api.java.en.*;
import java.awt.Color;
import java.awt.Component;
import jigspuzzle.cucumber.steps.util.ColorSteps;
import static org.assertj.core.api.Assertions.*;
import org.assertj.swing.fixture.FrameFixture;

/**
 * Step definitions for handeling the in- and output for the puzzlearea settings
 * window.
 *
 * @author RoseTec
 */
public class PuzzleareaSteps {

    private final WindowsSteps windowsSteps;

    public PuzzleareaSteps(WindowsSteps windowsSteps) {
        this.windowsSteps = windowsSteps;
    }

    // color of puzzle area
    @Then("^the background color of the puzzlearea should( not)? be \"([^\"]*)\"$")
    public void background_color_puzzlearea_should_be(String negation, String colorString) throws NoSuchMethodException {
        Color colorExpected = ColorSteps.getColorFromString(colorString);
        Color colorActual = get_puzzle_area().getBackground();

        if (negation != null) {
            assertThat(colorExpected.getRGB()).isNotEqualTo(colorActual.getRGB());
        } else {
            assertThat(colorExpected.getRGB()).isEqualTo(colorActual.getRGB());
        }
    }
    // -- color of puzzle area end

    private FrameFixture get_puzzle_window() {
        return (FrameFixture) windowsSteps.getPuzzleWindow();
    }

    private Component get_puzzle_area() {
        return get_puzzle_window().panel("puzzlearea-panel").target().getComponent(0);
    }

}
