package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    private final By declineAll = By.xpath("//a[@id='wt-cli-reject-btn']");
    private final By companyMenu = By.xpath("//a[contains(text(),'Company')]");
    private final By careersMenu = By.xpath("//a[contains(text(),'Careers')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void goTo() {
        driver.get("https://useinsider.com/");
    }

    public void openCompanyMenu() {
        driver.findElement(companyMenu).click();
    }

    public void rejectCookies() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        driver.findElement(declineAll).click();
    }

    public void clickCareers() {
        driver.findElement(careersMenu).click();
    }
} 