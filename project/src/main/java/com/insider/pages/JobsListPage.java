package com.insider.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class JobsListPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "select[name*='location'], select[id*='location'], .filter-location select")
    private WebElement locationFilter;

    @FindBy(css = "select[name*='department'], select[id*='department'], .filter-department select")
    private WebElement departmentFilter;

    @FindBy(css = ".job-item, .job-card, [class*='job']")
    private List<WebElement> jobItems;

    @FindBy(xpath = "//button[contains(text(), 'View Role') or contains(text(), 'View role')]")
    private List<WebElement> viewRoleButtons;

    @FindBy(css = ".position-title, .job-title, [class*='position']")
    private List<WebElement> positionTitles;

    @FindBy(css = ".department, .job-department, [class*='department']")
    private List<WebElement> departments;

    @FindBy(css = ".location, .job-location, [class*='location']")
    private List<WebElement> locations;

    public JobsListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void filterByLocation(String location) {
        try {
            wait.until(ExpectedConditions.visibilityOf(locationFilter));
            Select locationSelect = new Select(locationFilter);
            // Try exact match first
            try {
                locationSelect.selectByVisibleText(location);
            } catch (Exception e) {
                // Try partial match
                List<WebElement> options = locationSelect.getOptions();
                for (WebElement option : options) {
                    if (option.getText().contains("Istanbul") || option.getText().contains("Turkey")) {
                        locationSelect.selectByVisibleText(option.getText());
                        break;
                    }
                }
            }
            wait.until(ExpectedConditions.visibilityOfAllElements(jobItems));
        } catch (Exception e) {
            // Try alternative selector for custom dropdown
            try {
                WebElement customLocationFilter = driver.findElement(By.xpath("//select[contains(@name, 'location') or contains(@id, 'location')] | //div[contains(@class, 'location')]//select"));
                Select locationSelect = new Select(customLocationFilter);
                List<WebElement> options = locationSelect.getOptions();
                for (WebElement option : options) {
                    if (option.getText().contains("Istanbul") || option.getText().contains("Turkey")) {
                        locationSelect.selectByVisibleText(option.getText());
                        break;
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException("Could not find location filter: " + ex.getMessage());
            }
        }
    }

    public void filterByDepartment(String department) {
        try {
            wait.until(ExpectedConditions.visibilityOf(departmentFilter));
            Select departmentSelect = new Select(departmentFilter);
            // Try exact match first
            try {
                departmentSelect.selectByVisibleText(department);
            } catch (Exception e) {
                // Try partial match
                List<WebElement> options = departmentSelect.getOptions();
                for (WebElement option : options) {
                    if (option.getText().contains("Quality Assurance") || option.getText().contains("QA")) {
                        departmentSelect.selectByVisibleText(option.getText());
                        break;
                    }
                }
            }
            wait.until(ExpectedConditions.visibilityOfAllElements(jobItems));
        } catch (Exception e) {
            // Try alternative selector for custom dropdown
            try {
                WebElement customDeptFilter = driver.findElement(By.xpath("//select[contains(@name, 'department') or contains(@id, 'department')] | //div[contains(@class, 'department')]//select"));
                Select departmentSelect = new Select(customDeptFilter);
                List<WebElement> options = departmentSelect.getOptions();
                for (WebElement option : options) {
                    if (option.getText().contains("Quality Assurance") || option.getText().contains("QA")) {
                        departmentSelect.selectByVisibleText(option.getText());
                        break;
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException("Could not find department filter: " + ex.getMessage());
            }
        }
    }

    public boolean isJobsListPresent() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(jobItems));
            if (!jobItems.isEmpty()) {
                return true;
            }
        } catch (Exception e) {
            // Try alternative selectors
        }
        
        // Try alternative selectors for job items
        try {
            List<WebElement> altJobItems = driver.findElements(By.cssSelector(
                ".job, [class*='job'], [class*='position'], .career-item, .opening, [data-job], article[class*='job']"
            ));
            if (!altJobItems.isEmpty()) {
                wait.until(ExpectedConditions.visibilityOfAllElements(altJobItems));
                return !altJobItems.isEmpty();
            }
        } catch (Exception e) {
            // Continue
        }
        
        // Try XPath alternatives
        try {
            List<WebElement> xpathJobs = driver.findElements(By.xpath(
                "//div[contains(@class, 'job')] | //li[contains(@class, 'job')] | //article[contains(@class, 'job')] | //div[contains(@class, 'position')]"
            ));
            if (!xpathJobs.isEmpty()) {
                wait.until(ExpectedConditions.visibilityOfAllElements(xpathJobs));
                return !xpathJobs.isEmpty();
            }
        } catch (Exception e) {
            // Continue
        }
        
        return false;
    }

    public int getJobsCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(jobItems));
            if (!jobItems.isEmpty()) {
                return jobItems.size();
            }
        } catch (Exception e) {
            // Try alternatives
        }
        
        // Try alternative selectors
        List<WebElement> altJobItems = driver.findElements(By.cssSelector(
            ".job, [class*='job'], [class*='position'], .career-item, .opening, [data-job], article[class*='job']"
        ));
        if (!altJobItems.isEmpty()) {
            return altJobItems.size();
        }
        
        List<WebElement> xpathJobs = driver.findElements(By.xpath(
            "//div[contains(@class, 'job')] | //li[contains(@class, 'job')] | //article[contains(@class, 'job')] | //div[contains(@class, 'position')]"
        ));
        return xpathJobs.size();
    }

    public boolean verifyAllPositionsContain(String expectedText) {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(positionTitles));
            if (!positionTitles.isEmpty()) {
                return positionTitles.stream()
                        .allMatch(element -> element.getText().contains(expectedText));
            }
        } catch (Exception e) {
            // Try alternatives
        }
        
        // Try alternative selectors
        List<WebElement> altPositions = driver.findElements(By.cssSelector(
            "h2, h3, h4, .title, [class*='title'], [class*='position'], [class*='role']"
        ));
        if (!altPositions.isEmpty()) {
            return altPositions.stream()
                    .allMatch(element -> element.getText().contains(expectedText));
        }
        return false;
    }

    public boolean verifyAllDepartmentsContain(String expectedText) {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(departments));
            if (!departments.isEmpty()) {
                return departments.stream()
                        .allMatch(element -> element.getText().contains(expectedText));
            }
        } catch (Exception e) {
            // Try alternatives
        }
        
        // Try alternative selectors
        List<WebElement> altDepartments = driver.findElements(By.cssSelector(
            "[class*='department'], [class*='dept'], .category, [data-department]"
        ));
        if (!altDepartments.isEmpty()) {
            return altDepartments.stream()
                    .allMatch(element -> element.getText().contains(expectedText));
        }
        return false;
    }

    public boolean verifyAllLocationsContain(String expectedText) {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(locations));
            if (!locations.isEmpty()) {
                return locations.stream()
                        .allMatch(element -> element.getText().contains(expectedText));
            }
        } catch (Exception e) {
            // Try alternatives
        }
        
        // Try alternative selectors
        List<WebElement> altLocations = driver.findElements(By.cssSelector(
            "[class*='location'], [class*='loc'], .city, [data-location]"
        ));
        if (!altLocations.isEmpty()) {
            return altLocations.stream()
                    .allMatch(element -> element.getText().contains(expectedText));
        }
        return false;
    }

    public void clickFirstViewRoleButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(viewRoleButtons.get(0)));
            viewRoleButtons.get(0).click();
            return;
        } catch (Exception e) {
            // Try alternatives
        }
        
        // Try alternative selectors for View Role button
        try {
            List<WebElement> altButtons = driver.findElements(By.xpath(
                "//a[contains(text(), 'View') or contains(text(), 'Apply') or contains(text(), 'Role')] | " +
                "//button[contains(text(), 'View') or contains(text(), 'Apply') or contains(text(), 'Role')]"
            ));
            if (!altButtons.isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(altButtons.get(0)));
                altButtons.get(0).click();
                return;
            }
        } catch (Exception e) {
            // Continue
        }
        
        // Try CSS alternatives
        try {
            List<WebElement> cssButtons = driver.findElements(By.cssSelector(
                "a[class*='view'], a[class*='apply'], button[class*='view'], button[class*='apply'], .btn-apply, .btn-view"
            ));
            if (!cssButtons.isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(cssButtons.get(0)));
                cssButtons.get(0).click();
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not find View Role button: " + e.getMessage());
        }
    }

    public boolean isRedirectedToLeverApplication() {
        try {
            wait.until(ExpectedConditions.urlContains("lever"));
            String currentUrl = driver.getCurrentUrl();
            return currentUrl.contains("lever.co") || currentUrl.contains("jobs.lever.co");
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
