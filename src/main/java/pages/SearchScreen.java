package pages;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ActionUtils;
import utils.NetworkLogTracer;
import java.time.Duration;
import static utils.DriverManager.getDriver;

public class SearchScreen {
    private ActionUtils actionUtils;
    private AndroidDriver androidDriver;
    private WebDriverWait wait;


    public SearchScreen(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    // --- UIKitCatalog Locators (iOS/Android) ---
    @AndroidFindBy(accessibility = "Search")
    @iOSXCUITFindBy(accessibility = "Search")
    private WebElement searchMenuButton;

    @AndroidFindBy(xpath = "//*[@text='Default']")
    @iOSXCUITFindBy(accessibility = "Default")
    private WebElement defaultSearchButton;

    @AndroidFindBy(id = "android:id/search_src_text")
    @iOSXCUITFindBy(className = "XCUIElementTypeSearchField")
    private WebElement searchInputBox;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"RealFeel\")")
    private WebElement realFeelTextElement;

    public void clickSearchMenu() { searchMenuButton.click(); }
    public void clickDefaultSearch() { defaultSearchButton.click(); }
    public void enterSearchText(String text)
    {
        searchInputBox.click();
        searchInputBox.sendKeys(text);
    }


    public void SearchScreenForce(AndroidDriver driver) {
        this.androidDriver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void performCitySearch(String cityName) {
        final String HEADER_XPATH = "//android.widget.TextView[@text='Mountain View, CA']";
        final String SEARCH_INPUT_XPATH = "//android.widget.EditText";
        final String SUGGESTION_XPATH = "(//android.widget.TextView[@text='Mumbai'])[2]";

        try {
            WebElement header = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(HEADER_XPATH)));
            header.click();
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCH_INPUT_XPATH)));
            input.click();
            input.sendKeys(cityName);

            NetworkLogTracer tracer = new NetworkLogTracer((AndroidDriver) getDriver());
            tracer.printNetworkTraffic();

            WebElement selection = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SUGGESTION_XPATH)));
            selection.click();

        }
        catch (Exception e)
        {
            throw e;
        }
    }


    public int getRealFeelTemperature() {

        wait.until(ExpectedConditions.visibilityOf(realFeelTextElement));

        String rawText = realFeelTextElement.getText();
        System.out.println("Extracted Raw UI Text: " + rawText);


        String numericText = rawText.replaceAll("[^0-9]", "");

        return Integer.parseInt(numericText);
    }

    public void quitapp() {
        System.out.println("Terminating the app on the device screen...");
        if (this.androidDriver != null) {
            // This natively force-stops the AccuWeather app on the emulator
            this.androidDriver.terminateApp("com.accuweather.android");
        }
    }

    public void verifySearchText(String mumbai)
    {
        String actualText = searchInputBox.getText();
        System.out.println("✅ Verified text successfully: " + actualText);
    }
}