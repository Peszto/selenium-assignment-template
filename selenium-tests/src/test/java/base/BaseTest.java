package base;

import logic.UserActions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.ConfigReader;
import utils.DriverFactory;


public class BaseTest {

    protected WebDriver driver;
    protected UserActions userActions;

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        driver = DriverFactory.createDriver(browser, ConfigReader.isHeadless());
        driver.manage().timeouts().implicitlyWait(ConfigReader.getImplicitWait());

        userActions = new UserActions(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
