# Insider Automation Test Project

This project contains automated tests for the Insider website using Selenium WebDriver, TestNG, and Page Object Model (POM) pattern.

## Technologies Used

- Java
- Maven
- TestNG
- Selenium WebDriver
- Page Object Model (POM)

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
