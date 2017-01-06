package jigspuzzle.cucumber.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.*;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
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

    // preview for the image of the puzzle
    @Then("^I should( not)? see a preview of the puzzle$")
    public void i_should_see_a_preview_of_the_puzzle(String negation) {
        String previewName = "puzzle-preview";
        JPanel previrePanel = null;

        for (Component comp : get_puzzle_area().getComponents()) {
            if (previewName.equals(comp.getName())) {
                previrePanel = (JPanel) comp;
                break;
            }
        }
        assertThat(previrePanel).isNotNull();
        if (negation != null) {
            assertThat(previrePanel.isVisible()).isTrue();
        } else {
            assertThat(previrePanel.isVisible()).isFalse();
        }
        //todo: find out how to check the image
        throw new PendingException();
    }
    // -- preview for the image of the puzzle end

    private FrameFixture get_puzzle_window() {
        return (FrameFixture) windowsSteps.getPuzzleWindow();
    }

    private JLayeredPane get_puzzle_area() {
        return (JLayeredPane) get_puzzle_window().panel("puzzlearea-panel").target().getComponent(0);
    }

}
