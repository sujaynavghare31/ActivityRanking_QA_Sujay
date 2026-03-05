package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

public class DriverManager {
    private static AppiumDriver driver;



    public static AppiumDriver getDriver() {
        if (driver == null)
        {
            System.out.println("Warning: getDriver called before initializeDriver.");
        }
        return driver;
    }

    // ADDED the 'String platform' parameter here!
    public static void initializeDriver(String platform)
    {
        if (driver == null) {
            try {
                System.out.println("🚀 Starting Appium Session for: " + platform.toUpperCase());
                URL url = new URL("http://127.0.0.1:4723/");

                if (platform.equalsIgnoreCase("android")) {
                    UiAutomator2Options options = new UiAutomator2Options()
                            .setPlatformName("Android")
                            .setDeviceName("emulator-5554")
                            .setAutomationName("UiAutomator2")
                            .setAppPackage("com.accuweather.android")
                            .setAppActivity(".home.ui.HomeActivity")
                            .setNoReset(true)
                            .setNewCommandTimeout(Duration.ofSeconds(60))
                            .setAutoGrantPermissions(true);
                    options.setCapability("appium:forceAppLaunch", true);
                    options.setCapability("appium:loggingPrefs", Map.of("logcat", "ALL"));
                    options.setCapability("appium:appWaitActivity", "*");

                    driver = new AndroidDriver(url, options);

                } else if (platform.equalsIgnoreCase("ios")) {
                    String appPath = "/Users/Sujay/ios-uicatalog/UIKitCatalog/build/Build/Products/Debug-iphonesimulator/UIKitCatalog.app";

                    XCUITestOptions options = new XCUITestOptions()
                            .setPlatformName("iOS")
                            .setAutomationName("XCUITest")
                            .setDeviceName("iPhone 17 Pro")
                            .setPlatformVersion("26.2")
                            .setApp(appPath)
                            .setNoReset(false)
                            .setNewCommandTimeout(Duration.ofSeconds(60));

                    driver = new IOSDriver(url, options);
                }

                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            } catch (MalformedURLException e) {
                throw new RuntimeException("Appium URL is incorrect", e);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Could not start Appium.");
            }
        }
    }

    public static void quitDriver() {
        if (driver != null) {
            System.out.println("Shutting down Appium session...");
            driver.quit();
            driver = null;
        }
    }
}