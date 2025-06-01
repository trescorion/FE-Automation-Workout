package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class CareersPage {
    private WebDriver driver;
    private final By locationsBlock = By.xpath("//h3[contains(text(),'Locations')]");
    private final By teamsBlock = By.xpath("//h3[contains(text(),'Find your calling')]");
    private final By lifeAtInsiderBlock = By.xpath("//h2[contains(text(),'Life at Insider')]");
    private final By seeAllTeams = By.xpath("//div[@class='row']/a[contains(@class, 'btn')]");
    private final By qualityAssurance = By.xpath("//div[contains(@class, 'job-item')][12]//h3[contains(@class, 'text-center')]");

    public CareersPage(WebDriver driver) {
        this.driver = driver;
    }

    private boolean isElementVisible(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void clickElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public boolean isLocationsBlockVisible() {
        return isElementVisible(locationsBlock);
    }

    public boolean isTeamsBlockVisible() {
        return isElementVisible(teamsBlock);
    }

    public boolean isLifeAtInsiderBlockVisible() {
        return isElementVisible(lifeAtInsiderBlock);
    }

    public boolean isSeeAllTeamsVisible() {
        return isElementVisible(seeAllTeams);
    }

    public boolean isQualityAssuranceVisible() {
        return isElementVisible(qualityAssurance);
    }

    private void waitUntilTeamClickable(String teamName, int timeoutSeconds) {
        By teamBlock = By.xpath(String.format("//a[.//h3[normalize-space(text())='%s']]", teamName));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(teamBlock));
    }


    public void waitUntilCustomerSuccessClickable(int timeoutSeconds) {
        waitUntilTeamClickable("Customer Success", timeoutSeconds);
    }

    public void waitUntilSalesClickable(int timeoutSeconds) {
        waitUntilTeamClickable("Sales", timeoutSeconds);
    }

    public void waitUntilProductEngineeringClickable(int timeoutSeconds) {
        waitUntilTeamClickable("Product & Engineering", timeoutSeconds);
    }

    public void waitUntilQualityAssuranceClickable(int timeoutSeconds) {
        waitUntilTeamClickable("Quality Assurance", timeoutSeconds);
    }

    public void clickSeeAllTeams() {
        clickElement(seeAllTeams);
    }

    public void clickQualityAssurance() {
        clickElement(qualityAssurance);
    }
}