package stepdefs;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.List;

public class ApiCodeStepDef {
    private String lat, lon;
    private Response weatherResponse;

    @Given("The user searches for the city {string}")
    public void theUserSearchesForTheCity(String cityName) {
        Response geoResponse = given()
                .queryParam("name", cityName)
                .queryParam("count", "1")
                .when()
                .get("https://geocoding-api.open-meteo.com/v1/search");

        lat = geoResponse.jsonPath().getString("results[0].latitude");
        lon = geoResponse.jsonPath().getString("results[0].longitude");
        System.out.println("Latitude: " + lat + ", Longitude: " + lon);
    }

    @When("the system retrieves coordinates and fetches the 7-day forecast")
    public void fetchWeather() {
        weatherResponse = given()
                .queryParam("latitude", lat)
                .queryParam("longitude", lon)
                .queryParam("daily", "temperature_2m_max,weathercode")
                .queryParam("timezone", "auto")
                .when()
                .get("https://api.open-meteo.com/v1/forecast");

        weatherResponse.then().statusCode(200);
    }

    @Then("the activity {string} should return a rank and reasoning")
    public void verifyRanking(String activity) {
        // Just a basic check to ensure API returned data before we process it
        weatherResponse.then().body("daily.time", hasSize(7));
    }

    @And("Verify response as per AC {string}")
    public void verifyResponseAsPerAC(String activity) {
        // Extract today's temperature (index 0) from the API response
        List<Float> maxTemps = weatherResponse.jsonPath().getList("daily.temperature_2m_max", Float.class);
        float tempToday = maxTemps.get(0);

        int calculatedRank = 0;
        String reasoning = "";

        // Calculate rank and reasoning based purely on today's temperature
        switch (activity.toLowerCase()) {
            case "running":
            case "outdoor sightseeing":
                if (tempToday > 25.0) {
                    calculatedRank = 4;
                    reasoning = "Temperature is a bit high (" + tempToday + "°C). Might be too warm.";
                } else {
                    calculatedRank = 9;
                    reasoning = "Temperature is pleasant (" + tempToday + "°C). Great conditions!";
                }
                break;

            case "skiing":
                if (tempToday < 5.0) {
                    calculatedRank = 10;
                    reasoning = "Temperature is cold (" + tempToday + "°C). Excellent for skiing!";
                } else {
                    calculatedRank = 2;
                    reasoning = "Temperature is too warm (" + tempToday + "°C) for good skiing conditions.";
                }
                break;

            default:
                calculatedRank = 5;
                reasoning = "No specific temperature rules defined for this activity.";
                break;
        }

        // Print the final results to the console
        System.out.println("\n--- ACTIVITY RANKING RESULT ---");
        System.out.println("City     : Latitude " + lat + ", Longitude " + lon);
        System.out.println("Activity : " + activity);
        System.out.println("Temp     : " + tempToday + "°C");
        System.out.println("Rank     : " + calculatedRank + "/10");
        System.out.println("Reasoning: " + reasoning);
        System.out.println("-------------------------------\n");
    }
}