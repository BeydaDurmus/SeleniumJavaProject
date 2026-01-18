# Insider Automation Test Project

This project contains automated tests for the Insider website using Selenium WebDriver, TestNG, and Page Object Model (POM) pattern.

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Chrome browser installed

## Project Structure

```
src/
├── main/java/com/insider/
│   ├── base/
│   │   └── BaseTest.java          # Base test class with setup/teardown
│   └── pages/
│       ├── HomePage.java           # Home page object
│       ├── CareersPage.java        # Careers page object
│       └── JobsListPage.java       # Jobs list page object
└── test/java/com/insider/
    └── tests/
        └── InsiderTest.java        # TestNG test cases
```

## Test Cases

1. **testHomePageLoaded**: Verifies that the Insider home page is opened and all main blocks (header, footer, main content, sections) are loaded.

2. **testFilterJobsAndVerifyList**: Navigates to QA careers page, clicks "See all QA jobs", filters by Location (Istanbul, Turkey) and Department (Quality Assurance), and verifies the jobs list is present.

3. **testVerifyJobDetails**: Verifies that all displayed jobs contain:
   - Position: "Quality Assurance"
   - Department: "Quality Assurance"
   - Location: "Istanbul, Turkey"

4. **testViewRoleRedirect**: Clicks "View Role" button and verifies redirect to Lever Application form page.

## Running Tests

### Using Maven

```bash
mvn clean test
```

### Using TestNG XML

```bash
mvn test -DsuiteXmlFile=testng.xml
```

### Running specific test

```bash
mvn test -Dtest=InsiderTest#testHomePageLoaded
```

## Features

- **Page Object Model**: Each page has its own class with page-specific methods
- **Optimized Selectors**: Uses CSS selectors and XPath where appropriate
- **TestNG Framework**: Uses TestNG for test execution and assertions
- **WebDriverManager**: Automatically manages ChromeDriver
- **Clean Code**: Well-structured, readable, and maintainable code

## Dependencies

- Selenium WebDriver 4.15.0
- TestNG 7.8.0
- WebDriverManager 5.6.2

## Notes

- The project uses WebDriverManager to automatically download and manage the ChromeDriver
- Tests run in sequence (not parallel) to ensure proper execution order
- All assertions use TestNG's Assert class for verification
# SeleniumJavaProject
