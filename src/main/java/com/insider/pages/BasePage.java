package com.insider.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @FindBy(css = "header")
    private WebElement header;

    @FindBy(css = "footer")
    private WebElement footer;

    @FindBy(css = "main, [role='main']")
    private WebElement mainContent;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
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
}
