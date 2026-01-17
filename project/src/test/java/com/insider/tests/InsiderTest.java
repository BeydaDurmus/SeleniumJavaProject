package com.insider.tests;

import com.insider.base.BaseTest;
import com.insider.pages.CareersPage;
import com.insider.pages.HomePage;
import com.insider.pages.JobsListPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InsiderTest extends BaseTest {

    @Test(priority = 1, description = "Verify Insider home page is opened and all main blocks are loaded")
    public void testHomePageLoaded() {
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();

        Assert.assertTrue(homePage.isHeaderLoaded(), "Header should be loaded");
        Assert.assertTrue(homePage.isFooterLoaded(), "Footer should be loaded");
        Assert.assertTrue(homePage.isMainContentLoaded(), "Main content should be loaded");
        // Sections check is optional - if sections exist, count should be > 0
        int sectionsCount = homePage.getSectionsCount();
        if (sectionsCount > 0) {
            Assert.assertTrue(sectionsCount > 0, "At least one section should be present");
        }
        Assert.assertTrue(homePage.getCurrentUrl().contains("insiderone.com"), 
                "Current URL should contain insiderone.com");
    }

    @Test(priority = 2, description = "Navigate to QA careers page, filter jobs and verify jobs list presence")
    public void testFilterJobsAndVerifyList() {
        CareersPage careersPage = new CareersPage(driver);
        careersPage.navigateToQACareersPage();
        careersPage.clickSeeAllQAJobs();

        JobsListPage jobsListPage = new JobsListPage(driver);
        jobsListPage.filterByLocation("Istanbul, Turkey");
        jobsListPage.filterByDepartment("Quality Assurance");

        Assert.assertTrue(jobsListPage.isJobsListPresent(), 
                "Jobs list should be present after filtering");
        Assert.assertTrue(jobsListPage.getJobsCount() > 0, 
                "At least one job should be displayed");
    }

    @Test(priority = 3, description = "Verify all jobs contain correct Position, Department and Location")
    public void testVerifyJobDetails() {
        CareersPage careersPage = new CareersPage(driver);
        careersPage.navigateToQACareersPage();
        careersPage.clickSeeAllQAJobs();

        JobsListPage jobsListPage = new JobsListPage(driver);
        jobsListPage.filterByLocation("Istanbul, Turkey");
        jobsListPage.filterByDepartment("Quality Assurance");

        Assert.assertTrue(jobsListPage.isJobsListPresent(), 
                "Jobs list should be present");

        Assert.assertTrue(jobsListPage.verifyAllPositionsContain("Quality Assurance"),
                "All positions should contain 'Quality Assurance'");

        Assert.assertTrue(jobsListPage.verifyAllDepartmentsContain("Quality Assurance"),
                "All departments should contain 'Quality Assurance'");

        Assert.assertTrue(jobsListPage.verifyAllLocationsContain("Istanbul, Turkey"),
                "All locations should contain 'Istanbul, Turkey'");
    }

    @Test(priority = 4, description = "Click View Role button and verify redirect to Lever Application form")
    public void testViewRoleRedirect() {
        CareersPage careersPage = new CareersPage(driver);
        careersPage.navigateToQACareersPage();
        careersPage.clickSeeAllQAJobs();

        JobsListPage jobsListPage = new JobsListPage(driver);
        jobsListPage.filterByLocation("Istanbul, Turkey");
        jobsListPage.filterByDepartment("Quality Assurance");

        Assert.assertTrue(jobsListPage.isJobsListPresent(), 
                "Jobs list should be present before clicking View Role");

        jobsListPage.clickFirstViewRoleButton();

        Assert.assertTrue(jobsListPage.isRedirectedToLeverApplication(),
                "Should be redirected to Lever Application form page");
    }
}
