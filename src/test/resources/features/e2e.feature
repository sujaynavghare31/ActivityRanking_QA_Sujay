Feature: Activity Ranking API - City-Based Weather
  Forecast Integration UI+ API

  @mobile @android
  Scenario: User successfully retrieves a 7-day activity
  ranking for a valid city
    Given User is on AccuWeather home screen "Mumbai".
    When User searches for "Mumbai"
    Then Mumbai weather should be displayed

  @mobile @ios
  Scenario: User searches for a city in the UIKitCatalog
    Given User is on the UIKitCatalog home screen
    When User navigates to the Search menu
    And User selects the Default search bar option
    And User enters "Mumbai" into the search field
    Then The search field should contain "Mumbai"

  @api
  Scenario Outline: Fetch weather and rank activities for a specific city
    Given The user searches for the city "<cityName>"
    When the system retrieves coordinates and fetches the 7-day forecast
    Then the activity "<activity>" should return a rank and reasoning
    And  Verify response as per AC "<activity>"
    Examples:
      | cityName | activity            |
      | Mumbai   | running             |
      | Zurich   | Skiing              |