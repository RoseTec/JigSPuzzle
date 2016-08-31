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
        plugin = {"html:../target/cucumber-html-report"}
)
public class RunCucumber {
}
