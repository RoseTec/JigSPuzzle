package jigspuzzle.cucumber.steps.util;

import java.awt.Color;

/**
 * Step definitions for easier gettings color of the step definitions.
 *
 * @author RoseTec
 */
public class ColorSteps {

    public ColorSteps() {
    }

    public static Color getColorFromString(String color) throws NoSuchMethodException {
        switch (color) {
            case "red":
                return Color.RED;
            case "blue":
                return Color.BLUE;
            case "yellow":
                return Color.YELLOW;
        }
        throw new NoSuchMethodException("The color " + color + " is not supported in the tests. Please add it to " + ColorSteps.class.getSimpleName());
    }
}
