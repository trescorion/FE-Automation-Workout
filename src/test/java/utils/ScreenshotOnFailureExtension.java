package utils;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;
import tests.BaseTest;

public class ScreenshotOnFailureExtension implements TestWatcher {
    
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.out.println("TEST FAILED - Screenshot alınıyor.");
        System.out.println("Hata: " + cause.getMessage());
        
        try {
            Object testInstance = context.getRequiredTestInstance();
            if (testInstance instanceof BaseTest baseTest) {
                WebDriver driver = baseTest.driver;
                
                if (driver != null) {
                    System.out.println("Driver bulundu - screenshot alınıyor.");
                    ScreenshotUtil.takeScreenshot(driver, context.getDisplayName());
                    System.out.println("Screenshot başarıyla alındı");
                } else {
                    System.out.println("Driver null - screenshot alınamıyor");
                }

                baseTest.cleanupDriver();
            }
        } catch (Exception e) {
            System.out.println("Screenshot alma hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.println("TEST BAŞARILI - Screenshot alınmayacak");

        try {
            Object testInstance = context.getRequiredTestInstance();
            if (testInstance instanceof BaseTest baseTest) {
                baseTest.cleanupDriver();
            }
        } catch (Exception e) {
            System.out.println("⚠Driver temizleme hatası: " + e.getMessage());
        }
    }
}