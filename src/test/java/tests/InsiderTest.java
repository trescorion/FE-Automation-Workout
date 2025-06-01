package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.CareersPage;
import pages.HomePage;
import pages.OpenPositionsPage;
import pages.QualityAssurancePage;
import utils.ScreenshotOnFailureExtension;

@ExtendWith( ScreenshotOnFailureExtension.class)
public class InsiderTest extends BaseTest {
    @Test
    public void testQAJobListingAndRedirectToJobsLeverCo () {
        HomePage homePage = new HomePage(driver);
        CareersPage careersPage = new CareersPage(driver);
        QualityAssurancePage qualityAssurancePage = new QualityAssurancePage(driver);
        homePage.goTo();
        homePage.rejectCookies();
        Assertions.assertTrue(driver.getTitle().toLowerCase().contains("insider"), "Home page title should contain 'insider'");
        //homePage.openCompanyMenu();
        homePage.clickCareers();
        Assertions.assertTrue(careersPage.isLocationsBlockVisible(), "Locations block should be visible");
        Assertions.assertTrue(careersPage.isTeamsBlockVisible());
        careersPage.waitUntilCustomerSuccessClickable(5);
        Assertions.assertTrue(careersPage.isLifeAtInsiderBlockVisible(), "Life at Insider block should be visible");

        Assertions.assertTrue(careersPage.isSeeAllTeamsVisible(), "All Teams button should be visible");
        careersPage.clickSeeAllTeams();
        Assertions.assertTrue(careersPage.isQualityAssuranceVisible(), "Quality Assurance team should be visible");
        careersPage.waitUntilQualityAssuranceClickable(5);
        careersPage.clickQualityAssurance();

        Assertions.assertTrue(qualityAssurancePage.isSeeAllJobsVisible(),"See all jobs button should be visible");
        qualityAssurancePage.waitUntilSeeAllJobsClickable(5);
        qualityAssurancePage.clickSeeAllJobs();


        OpenPositionsPage openPositionsPage = new OpenPositionsPage(driver);

        openPositionsPage.ensurePageIsReady();
        openPositionsPage.clickLocationFilter();
        openPositionsPage.waitUntilSeeAllLocationsClickable(10);
        openPositionsPage.clickIstanbul();
        openPositionsPage.ensurePageIsReady();


        Assertions.assertTrue(openPositionsPage.validateAllJobsMatchCriteria(),
                "All jobs should contain 'Quality Assurance' in position, department should be 'Quality Assurance', and location should be 'Istanbul, Turkey'");

        Assertions.assertTrue(openPositionsPage.clickViewRoleAndValidateRedirect(),
                "View Role button should redirect to jobs.lever.co");
    }
}