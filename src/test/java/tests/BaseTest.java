package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;

public class BaseTest {
    public WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.out.println("=== SETUP BAŞLADI ===");
        driver = DriverFactory.createDriver();
        driver.manage().window().maximize();
        System.out.println("Driver başlatıldı: " + driver);
    }

    @AfterEach  
    public void tearDown(TestInfo testInfo) {
        System.out.println("=== TEARDOWN BAŞLADI ===");
        System.out.println("🔄 tearDown çalışıyor: " + testInfo.getDisplayName());

        System.out.println("ℹ️ Driver açık bırakıldı - Extension için");
        System.out.println("=== TEARDOWN BİTTİ ===");
    }
    
    public void cleanupDriver() {
        if (driver != null) {
            driver.quit();
            System.out.println("✅ Driver temizlendi");
        }
    }
}