package utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ActionUtils {
    private WebDriverWait wait;

    public ActionUtils(AppiumDriver driver) {
        // Explicit wait of 10 seconds for dynamic mobile elements
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

}