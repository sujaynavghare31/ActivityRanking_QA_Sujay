package stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.DriverManager;
import java.util.Collection;

public class Hooks {

    @Before("@mobile")
    public void setUp(Scenario scenario) {
        // Get all tags attached to the current scenario
        Collection<String> tags = scenario.getSourceTagNames();
        String platform = "";

        // Determine platform based on the Cucumber tags
        if (tags.contains("@android")) {
            platform = "android";
        } else if (tags.contains("@ios")) {
            platform = "ios";
        } else {
            throw new RuntimeException("A @mobile scenario requires either an @android or @ios tag.");
        }

        System.out.println("Initializing Mobile Driver for: " + platform.toUpperCase());

        // Pass the dynamically detected platform to the driver manager
        DriverManager.initializeDriver(platform);
    }

    @After("@mobile")
    public void tearDown() {
        System.out.println("Closing Mobile Driver...");
        DriverManager.quitDriver();
    }
}