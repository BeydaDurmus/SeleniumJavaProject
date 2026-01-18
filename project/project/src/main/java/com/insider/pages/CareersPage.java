package com.insider.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CareersPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//a[contains(text(), 'See all QA jobs')]")
    private WebElement seeAllQAJobsButton;

    public CareersPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void navigateToQACareersPage() {
        driver.get("https://insiderone.com/careers/quality-assurance/");
    }

    public void clickSeeAllQAJobs() {
        wait.until(ExpectedConditions.elementToBeClickable(seeAllQAJobsButton));
        seeAllQAJobsButton.click();
    }
}
