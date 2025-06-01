package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class QualityAssurancePage {

    private final WebDriver driver;
    private final By seeAllJobs = By.xpath("//div[@class='button-group d-flex flex-row']/a[contains(@class, 'btn')]");

    public QualityAssurancePage(WebDriver driver) {
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

    public boolean isSeeAllJobsVisible() {
        return isElementVisible(seeAllJobs);
    }

    public void waitUntilSeeAllJobsClickable(int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(seeAllJobs));
    }

    public void clickSeeAllJobs() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(seeAllJobs));
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
}