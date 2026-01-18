package com.insider.tests;

import com.insider.base.BaseTest;
import com.insider.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void initPage() {
        homePage = new HomePage(driver);
        homePage.navigateToHomePage();
    }

    @Test(description = "Verify careers page contents are loaded")
    public void testHeaderLoaded() {
        Assert.assertTrue(homePage.isHeaderLoaded(), 
                "Header should be visible on careers page");
        Assert.assertTrue(homePage.isFooterLoaded(), 
                "Footer should be visible on careers page");
        Assert.assertTrue(homePage.isMainContentLoaded(), 
                "Main content should be visible on careers page");

    }

    @Test(description = "Verify home page URL is correct")
    public void testHomePageUrl() {
        Assert.assertTrue(homePage.getCurrentUrl().contains("insiderone.com"), 
                "URL should contain insiderone.com");
    }
}
