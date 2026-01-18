package com.insider.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CareersPage extends BasePage {

    @FindBy(xpath = "//a[contains(text(), 'See all QA jobs')]")
    private WebElement seeAllQAJobsButton;

    public CareersPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToQACareersPage() {
        driver.get("https://insiderone.com/careers/quality-assurance/");
    }

    public void clickSeeAllQAJobs() {
        wait.until(ExpectedConditions.elementToBeClickable(seeAllQAJobsButton));
        seeAllQAJobsButton.click();
    }
}
