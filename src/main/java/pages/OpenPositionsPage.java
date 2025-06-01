
package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class OpenPositionsPage {

    private final WebDriver driver;
    private final By locationDropdown = By.xpath("//span[@aria-labelledby='select2-filter-by-location-container']");
    private final By clickIstanbul = By.xpath("//li[@aria-selected='false'][1]");
    private final By departmentDropdown = By.xpath("//span[@aria-labelledby='select2-filter-by-department-container']");
    private final By clickQualityAssurance = By.xpath("//li[contains(text(), 'Quality Assurance')]");
    private final By jobsList = By.xpath("//div[@id='jobs-list']");
    private final By jobWrappers = By.xpath("//div[@id='jobs-list']//div[@class='position-list-item-wrapper bg-light']");
    // Updated locator to find any View Role button in the jobs list
    private final By viewRoleButton = By.xpath("//div[@id='jobs-list']//a[contains(@class, 'btn') and contains(text(), 'View Role')]");

    public OpenPositionsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensurePageIsReady() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
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

    public void clickLocationFilter() {
        clickElement(locationDropdown);
    }

    public void clickIstanbul() {
        clickElement(clickIstanbul);
    }

    public void clickDepartmentFilter() {
        clickElement(departmentDropdown);
    }

    public void clickQualityAssurance() {
        clickElement(clickQualityAssurance);
    }

    public void waitUntilSeeAllLocationsClickable(int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(clickIstanbul));
    }

    public void waitUntilAllDepartmentsClickable(int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(clickQualityAssurance));
    }

    public int getJobCount() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(jobsList));
            List<WebElement> jobs = driver.findElements(jobWrappers);
            return jobs.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void waitForJobsToLoad(int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(jobsList));
    }

    public boolean validateAllJobsMatchCriteria() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            wait.until(ExpectedConditions.visibilityOfElementLocated(jobsList));

            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {
            }

            List<WebElement> jobWrapperElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(jobWrappers));

            if (jobWrapperElements.size() == 0) {
                System.out.println("No jobs found after filtering");
                return false;
            }

            System.out.println("Found " + jobWrapperElements.size() + " job wrappers after filtering");

            for (int i = 0; i < jobWrapperElements.size(); i++) {
                WebElement jobWrapper = jobWrapperElements.get(i);

                String title = jobWrapper.findElement(By.xpath(".//p[@class='position-title font-weight-bold']")).getText();
                String department = jobWrapper.findElement(By.xpath(".//span[@class='position-department text-large font-weight-600 text-primary']")).getText();
                String location = jobWrapper.findElement(By.xpath(".//div[@class='position-location text-large']")).getText();

                System.out.println("Job " + (i+1) + ": " + title + " | " + department + " | " + location);

                boolean titleContainsQA = title.toLowerCase().contains("quality assurance") ||
                        title.toLowerCase().contains("qa") ||
                        title.toLowerCase().contains("test");

                boolean departmentIsQA = department.equals("Quality Assurance");

                boolean locationIsIstanbul = location.contains("Istanbul") && (location.contains("Turkey") || location.contains("Turkiye"));

                if (!titleContainsQA || !departmentIsQA || !locationIsIstanbul) {
                    System.out.println("Job validation failed for: " + title);
                    System.out.println("Title contains QA: " + titleContainsQA);
                    System.out.println("Department is QA: " + departmentIsQA);
                    System.out.println("Location is Istanbul: " + locationIsIstanbul);
                    return false;
                }
            }

            System.out.println("All " + jobWrapperElements.size() + " jobs match the criteria");
            return true;

        } catch (Exception e) {
            System.out.println("Error validating jobs: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean clickViewRoleAndValidateRedirect() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            // First, wait for jobs to be loaded
            wait.until(ExpectedConditions.visibilityOfElementLocated(jobsList));

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }

            // Get the current window handle
            String originalWindow = driver.getWindowHandle();

            // Find all View Role buttons and click the first one
            List<WebElement> viewRoleButtons = driver.findElements(viewRoleButton);

            if (viewRoleButtons.isEmpty()) {
                System.out.println("No View Role buttons found");
                return false;
            }

            System.out.println("Found " + viewRoleButtons.size() + " View Role buttons");

            WebElement firstViewRoleBtn = viewRoleButtons.get(0);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", firstViewRoleBtn);

            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }

            // Click using JavaScript to avoid interception issues
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstViewRoleBtn);

            // Wait for new window to open
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            // Get all window handles
            Set<String> allWindows = driver.getWindowHandles();

            // Switch to the new window
            for (String windowHandle : allWindows) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }

            // Wait for the new page to load and get the URL
            wait.until(ExpectedConditions.urlContains("lever.co"));
            String currentUrl = driver.getCurrentUrl();

            System.out.println("Redirected to URL: " + currentUrl);

            // Check if URL contains "jobs.lever.co"
            boolean isValidRedirect = currentUrl.contains("jobs.lever.co");

            if (isValidRedirect) {
                System.out.println("SUCCESS: URL contains 'jobs.lever.co'");
            } else {
                System.out.println("FAILED: URL does not contain 'jobs.lever.co'");
            }

            // Close the new window and switch back to original
            driver.close();
            driver.switchTo().window(originalWindow);

            return isValidRedirect;

        } catch (Exception e) {
            System.out.println("Error clicking View Role or validating redirect: " + e.getMessage());
            e.printStackTrace();

            // Try to get back to original window if something went wrong
            try {
                Set<String> allWindows = driver.getWindowHandles();
                for (String window : allWindows) {
                    driver.switchTo().window(window);
                    if (driver.getCurrentUrl().contains("useinsider.com")) {
                        break;
                    }
                }
            } catch (Exception ignored) {
            }

            return false;
        }
    }
}