package com.insider.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;

public class JobsListPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By locationFilterBy = By.xpath("//select[contains(@*,'location')]");
    private final By departmentFilterBy = By.xpath("//select[contains(@*,'department')]");
    private final By positionListItemsBy = By.xpath("//*[contains(@class,'position-list')]");
    private final By viewRoleButtonBy = By.xpath("//a[contains(text(),'View Role')]");
    private final By departmentInJobBy = By.xpath(".//*[contains(@class,'position-department')]");
    private final By locationInJobBy = By.xpath(".//*[contains(@class,'position-location')]");

    public JobsListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void filterByLocation(String location) {
        WebElement filter = wait.until(ExpectedConditions.visibilityOfElementLocated(locationFilterBy));
        new Select(filter).selectByVisibleText(location);
        waitForJobsReload();
    }

    public void filterByDepartment(String department) {
        WebElement filter = wait.until(ExpectedConditions.visibilityOfElementLocated(departmentFilterBy));
        new Select(filter).selectByVisibleText(department);
        waitForJobsReload();
    }

    public void clickFirstViewRoleButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(viewRoleButtonBy));
        btn.click();
    }

    public boolean isJobsListPresent() {
        waitForJobsReload();
        return !getJobItems().isEmpty();
    }

    public int getJobsCount() {
        waitForJobsReload();
        return getJobItems().size();
    }


    public boolean verifyAllDepartmentsContain(String expected) {
        return allPositionsItemsContain( expected);
    }

    public boolean verifyAllLocationsContain(String expected) {
        return allLocationsItemsContain(expected);
    }

    public boolean isRedirectedToLeverApplication() {
        String currentTab = driver.getWindowHandle();
    
        wait.until(driver -> driver.getWindowHandles().size() > 1);
    
        for (String tab : driver.getWindowHandles()) {
            if (!tab.equals(currentTab)) {
                driver.switchTo().window(tab);
                break;
            }
        }
        // Debug
        System.out.println("New tab URL → " + driver.getCurrentUrl());
    
        return wait.until(ExpectedConditions.urlContains("jobs.lever.co"));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }


    private void waitForJobsReload() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(positionListItemsBy));
    }

    private List<WebElement> getJobItems() {
        return driver.findElements(positionListItemsBy);
    }

    private List<WebElement> departmentnInJobItems() {
        driver.findElements(departmentInJobBy)
      .forEach(e ->
          System.out.println(
              "departmentnInJobItems actual text: [" + e.getText() + "]"
          )
      );
        return driver.findElements(departmentInJobBy);
        
    }

    private List<WebElement> locationInJobItems() {
        driver.findElements(locationInJobBy)
      .forEach(e ->
          System.out.println(
              "locationInJobItems actual text: [" + e.getText() + "]"
          )
      );
        return driver.findElements(locationInJobBy);
    }

    private boolean allPositionsItemsContain(String expected) {
        waitForJobsReload();
        List<WebElement> positionsItems = departmentnInJobItems();
        for(WebElement item : positionsItems) {
            if(!item.getText().contains(expected)) {
                return false;
            }
        }
        return true;
    }

    private boolean allLocationsItemsContain(String expected) {
        waitForJobsReload();
        List<WebElement> locationsItems = locationInJobItems();
        for(WebElement item : locationsItems) {
            if(!item.getText().contains(expected)) {
                return false;
            }
        }
        return true;
    }
}