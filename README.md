Activity Ranking & Weather Automation Framework

This repository contains an end-to-end test automation framework for the Activity Ranking API 
and AccuWeather mobile application.
It is built using a Page Object Model (POM) architecture to ensure modularity and maintainability

Language: Java

UI Automation: Appium, Selenium

API Testing: Rest-Assured

BDD Framework: Cucumber

Build Tool: Maven

iOS + Android Support

Framework structure:
src/
├── main/java/
│   ├── pages/         # Page Object Model classes (e.g., SearchScreen)
│   └── utils/         # Helpers (ActionUtils, DriverManager, NetworkLogTracer)
└── test/
├── java/
│   ├── runners/   # TestNG/Cucumber runners
│   └── stepdefs/  # Cucumber step definitions (ApiCodeStepDef)
└── resources/
└── features/  # Gherkin feature files (.feature)

