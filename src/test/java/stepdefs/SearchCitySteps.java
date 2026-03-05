package stepdefs;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.SearchScreen;

import static utils.DriverManager.getDriver;
public class SearchCitySteps {


    SearchScreen searchScreen = new SearchScreen(getDriver());

    @Given("User is on AccuWeather home screen {string}.")
    public void userIsOnAccuWeatherHomeScreen(String city)
    {
        System.out.println("TC running for city: " + city);
        searchScreen.SearchScreenForce((AndroidDriver) getDriver());
    }

    @When("User searches for {string}")
    public void userSearchesFor(String cityName) {
        searchScreen.performCitySearch(cityName);
    }

    @Given("User is on the UIKitCatalog home screen")
    public void user_is_on_home_screen() {
        System.out.println("UIKitCatalog active.");
    }

    @When("User navigates to the Search menu")
    public void user_navigates_to_search() {
        searchScreen.clickSearchMenu();
    }

    @When("User selects the Default search bar option")
    public void user_selects_default_option() {
        searchScreen.clickDefaultSearch();
    }

    @When("User enters {string} into the search field")
    public void user_enters_city(String city) {
        searchScreen.enterSearchText(city);
    }

    @Then("The search field should contain {string}")
    public void verify_search_text(String expectedText) {

        System.out.println("Verified text: " + expectedText);
        searchScreen.verifySearchText("Mumbai");
    }


    @Then("Mumbai weather should be displayed")
    public void mumbaiWeatherShouldBeDisplayed() {

        int temperature = searchScreen.getRealFeelTemperature();
        System.out.println("✅ Successfully extracted RealFeel temperature: " + temperature + "°");
        Assert.assertTrue(temperature > 0, "The extracted temperature should be a valid number greater than 0.");
        searchScreen.quitapp();
    }
}