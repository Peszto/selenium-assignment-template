package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenshotListener implements ITestListener {

    private static final String SCREENSHOT_DIR = "screenshots";

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = extractDriverFrom(result.getInstance());
        if (driver != null) {
            saveScreenshot(driver, result.getName());
        }
    }

    private WebDriver extractDriverFrom(Object testInstance) {
        try {
            Field field = testInstance.getClass().getSuperclass().getDeclaredField("driver");
            field.setAccessible(true);
            return (WebDriver) field.get(testInstance);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.err.println("[ScreenshotListener] Could not access driver field: " + e.getMessage());
            return null;
        }
    }

    private void saveScreenshot(WebDriver driver, String testName) {
        if (!(driver instanceof TakesScreenshot screenshotter)) {
            return;
        }
        try {
            byte[] screenshot = screenshotter.getScreenshotAs(OutputType.BYTES);
            String fileName = testName + "_" + System.currentTimeMillis() + ".png";
            Path outputPath = Paths.get(SCREENSHOT_DIR, fileName);
            Files.createDirectories(outputPath.getParent());
            Files.write(outputPath, screenshot);
            System.out.println("[ScreenshotListener] Screenshot saved: " + outputPath);
        } catch (IOException e) {
            System.err.println("[ScreenshotListener] Failed to save screenshot: " + e.getMessage());
        }
    }

}
