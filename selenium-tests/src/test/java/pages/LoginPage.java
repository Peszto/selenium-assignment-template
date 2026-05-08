package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private static final By USERNAME_FIELD = By.id("LoginUserName");
    private static final By PASSWORD_FIELD = By.id("LoginPassword");
    private static final By SUBMIT_BUTTON = By.cssSelector("button.submitbutton.okbutton");
    private static final By ERROR_MESSAGE = By.id("jquery-msg-content");
    private static final By LOGIN_PANEL = By.id("popup-login_panel");
    private static final By LOGIN_LINK = By.xpath(
            "//div[contains(@class,'popup-login_link') and contains(@class,'popup-login_button_on')]" +
            " | //div[contains(@class,'main_menu_login2')]//div[contains(@class,'menu_divitem_click')]"
    );
    private static final By LOGIN_NAV_BUTTON = By.cssSelector("div.main_menu_login2 div.menu_divitem_click");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void open() {
        driver.get(baseUrl);
        waitForPageToLoad();
        acceptCookiesIfPresent();
        waitForClickable(LOGIN_NAV_BUTTON).click();
        waitForVisible(USERNAME_FIELD);
    }

    public void loginAs(String email, String password) {
        clearAndType(USERNAME_FIELD, email);
        fillPassword(password);
        waitForClickable(SUBMIT_BUTTON).click();

        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.not(ExpectedConditions.attributeContains(
                            LOGIN_PANEL, "class", "rb-popuppanel-visibled")),
                    ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE)
            ));
        } catch (TimeoutException ignored) {}
        waitForPageToLoad();
    }

    // The jQuery placeholder plugin hides the real password field behind a fake text input.
    // We force-show the real field via JS, then type into it.
    private void fillPassword(String password) {
        js.executeScript(
            "var fake = document.getElementById('LoginPassword-placeholder');" +
            "var real = document.getElementById('LoginPassword');" +
            "if (fake) fake.style.display = 'none';" +
            "if (real) real.style.display = '';"
        );

        WebElement field = waitForVisible(PASSWORD_FIELD);
        field.clear();
        field.sendKeys(password);

        js.executeScript(
            "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
            "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));",
            field
        );
    }

    public void logout() {
        wait.until(ExpectedConditions.presenceOfElementLocated(PROFILE_NAV_ITEM));
        js.executeScript("RBLoadPage('/kijelentkezes');");
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(LOGIN_LINK));
        } catch (TimeoutException ignored) {}
    }

    public boolean isErrorMessageDisplayed() {
        return isVisibleWithShortWait(ERROR_MESSAGE);
    }

    public boolean isLoggedOut() {
        return isPresentWithShortWait(LOGIN_LINK);
    }
}
