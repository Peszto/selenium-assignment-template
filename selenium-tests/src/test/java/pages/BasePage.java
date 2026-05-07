package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Actions actions;
    protected final JavascriptExecutor js;

    private static final Duration DEFAULT_TIMEOUT = ConfigReader.getExplicitWait();
    private static final Duration SHORT_TIMEOUT = ConfigReader.getShortWait();

    private static final By COOKIE_BANNER = By.cssSelector("div.rb-cookiealert-cover-page");
    private static final By COOKIE_ACCEPT_BTN = By.cssSelector(".rb-cookiealert-button-ok");

    public BasePage(WebDriver driver) {
        this.driver  = driver;
        this.wait    = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        this.actions = new Actions(driver);
        this.js      = (JavascriptExecutor) driver;
    }

    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected boolean isPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    protected boolean isPresentWithShortWait(By locator) {
        try {
            new WebDriverWait(driver, SHORT_TIMEOUT)
                    .until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected boolean isVisibleWithShortWait(By locator) {
        try {
            new WebDriverWait(driver, SHORT_TIMEOUT)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void clearAndType(By locator, String text) {
        WebElement field = waitForClickable(locator);

        scrollToElement(field);

        field.clear();
        field.sendKeys(text);
    }

    public void waitForPageToLoad() {
        // This waits until the browser says the DOM is complete
        // AND the Cloudflare guard has naturally turned itself to true.
        wait.until(d -> (Boolean) js.executeScript(
                "return document.readyState === 'complete' && (window.__cfRLUnblockHandlers === true || typeof window.__cfRLUnblockHandlers === 'undefined');"
        ));
    }

    protected void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    public void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public long getVerticalScrollPosition() {
        return ((Number) js.executeScript("return window.scrollY;")).longValue();
    }

    protected void hoverOver(WebElement element) {
        actions.moveToElement(element).perform();
    }

    protected void hoverOver(By locator) {
        hoverOver(waitForVisible(locator));
    }

    protected void acceptCookiesIfPresent() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(COOKIE_BANNER));
            driver.findElement(COOKIE_ACCEPT_BTN).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(COOKIE_BANNER));
        } catch (Exception ignored) {}
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }

}
