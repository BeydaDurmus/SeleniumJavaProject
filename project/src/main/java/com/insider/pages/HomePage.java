package com.insider.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "header")
    private WebElement header;

    @FindBy(css = "footer")
    private WebElement footer;

    @FindBy(css = "main, [role='main']")
    private WebElement mainContent;

    @FindBy(css = "section, .section")
    private List<WebElement> sections;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void navigateToHomePage() {
        driver.get("https://insiderone.com/");
    }

    public boolean isHeaderLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(header));
            return header.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFooterLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(footer));
            return footer.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isMainContentLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(mainContent));
            return mainContent.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getSectionsCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(sections));
            return sections.size();
        } catch (Exception e) {
            // If sections not found, try alternative selectors
            try {
                List<WebElement> altSections = driver.findElements(By.cssSelector("div[class*='section'], article, .content-block"));
                return altSections.size();
            } catch (Exception ex) {
                return 0;
            }
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
