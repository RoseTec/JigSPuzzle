package jigspuzzle.cucumber.steps.util;

import cucumber.api.PendingException;
import cucumber.api.java.en.*;

/**
 * Steps that are practical to have for temporarry steps in scenarios. They
 * should <b>not</b> be used over a long timespan.
 *
 * @author RoseTec
 */
public class StepExecution {

    public StepExecution() {
    }

    @When("^I wait (\\d+)(?:ms| milliseconds)?$")
    public void wait_ms(int ms) throws InterruptedException {
        Thread.sleep(ms);
    }

    @When("^I wait (\\d+)(?:s| seconds)?$")
    public void wait_s(int s) throws InterruptedException {
        wait_ms(s * 1000);
    }

    @Then("^pending$")
    public void pending() {
        throw new PendingException();
    }

}
