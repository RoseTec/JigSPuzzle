package jigspuzzle.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * A class to run all cucumber tests
 *
 * @author RoseTec
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "../src/test/java/jigspuzzle/cucumber/features",
        glue = "jigspuzzle.cucumber.steps",
        //tags = {"@test"}, // <- activate this if only scenarios with tag '@test' should be executed.
        plugin = {"pretty", "html:../target/cucumber-html-report"}
)
public class RunCucumber {

    /**
     * The timeout that is used, whn searching in a ui-element for an element to
     * change. Thgis can e.g. be, when is will become visible or change its text
     * because of the user input.
     */
    public static final int TIMEOUT_UI_UPDATED = 10000;
}
