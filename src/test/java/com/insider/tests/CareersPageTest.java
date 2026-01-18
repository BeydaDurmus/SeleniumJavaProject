package com.insider.tests;

import com.insider.base.BaseTest;
import com.insider.pages.CareersPage;
import com.insider.pages.JobsListPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CareersPageTest extends BaseTest {

    private CareersPage careersPage;
    private JobsListPage jobsListPage;

    @BeforeMethod
    public void initPage() {
        careersPage = new CareersPage(driver);
        careersPage.navigateToQACareersPage();
    }

    @Test(description = "Verify careers page contents are loaded")
    public void testHeaderLoaded() {
        Assert.assertTrue(careersPage.isHeaderLoaded(), 
                "Header should be visible on careers page");
        Assert.assertTrue(careersPage.isFooterLoaded(), 
                "Footer should be visible on careers page");
        Assert.assertTrue(careersPage.isMainContentLoaded(), 
                "Main content should be visible on careers page");

    }

    @Test(description = "Navigate to QA jobs and verify jobs list is present")
    public void testFilterJobsAndVerifyList() throws InterruptedException {
        careersPage.clickSeeAllQAJobs();

        jobsListPage = new JobsListPage(driver);
        jobsListPage.filterByLocation("Istanbul, Turkiye");
        jobsListPage.filterByDepartment("Quality Assurance");

        Thread.sleep(3000);

        Assert.assertTrue(jobsListPage.isJobsListPresent(), 
                "Jobs list should be present after filtering");
    }

    @Test(description = "Verify all jobs contain correct Department and Location")
    public void testVerifyJobDetails() throws InterruptedException {
        careersPage.clickSeeAllQAJobs();

        jobsListPage = new JobsListPage(driver);
        jobsListPage.filterByLocation("Istanbul, Turkiye");
        jobsListPage.filterByDepartment("Quality Assurance");

        Thread.sleep(3000);

        Assert.assertTrue(jobsListPage.isJobsListPresent(), 
                "Jobs list should be present");

        Assert.assertTrue(jobsListPage.verifyAllDepartmentsContain("Quality Assurance"),
                "All departments should contain 'Quality Assurance'");

        Assert.assertTrue(jobsListPage.verifyAllLocationsContain("Istanbul, Turkiye"),
                "All locations should contain 'Istanbul, Turkiye'");
    }

    @Test(description = "Click View Role button and verify redirect to Lever Application")
    public void testViewRoleRedirect() throws InterruptedException {
        careersPage.clickSeeAllQAJobs();

        jobsListPage = new JobsListPage(driver);
        jobsListPage.filterByLocation("Istanbul, Turkiye");
        jobsListPage.filterByDepartment("Quality Assurance");

        Thread.sleep(3000);

        Assert.assertTrue(jobsListPage.isJobsListPresent(), 
                "Jobs list should be present before clicking View Role");

        jobsListPage.clickFirstViewRoleButton();

        Assert.assertTrue(jobsListPage.isRedirectedToLeverApplication(),
                "Should be redirected to Lever Application form page");
    }
}
