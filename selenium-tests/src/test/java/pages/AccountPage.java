package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class AccountPage extends BasePage {

    private static final By PROFILE_ICON = By.xpath("//div[contains(@class, 'main_menu_profile')]//div[contains(@class, 'menu_divitem_click')]");
    private static final By MY_PAGE_ITEM = By.xpath("//div[contains(@class,'menu_childitems')]//div[contains(text(),'Saját oldalam')]");
    private static final By FIRST_NAME_FIELD = By.xpath(
            "//input[@name='FirstName' or @id='FirstName' or @name='KeresztNev' or @id='KeresztNev']"
    );
    private static final By SAVE_BUTTON = By.cssSelector(
            "button.submitbutton, button[type='submit'], input[type='submit']"
    );
    private static final By SUCCESS_MESSAGE = By.cssSelector(
            ".validation-summary-valid, .successmsg, .inputsuccess"
    );

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public void openViaNavItem() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(),'Belépés')]")));

        WebElement profileMenu = wait.until(ExpectedConditions.presenceOfElementLocated(PROFILE_ICON));
        js.executeScript("arguments[0].click();", profileMenu);

        WebElement subMenu = wait.until(ExpectedConditions.presenceOfElementLocated(MY_PAGE_ITEM));
        js.executeScript("arguments[0].click();", subMenu);

        wait.until(ExpectedConditions.urlContains("blogger"));
    }

    public void updateFirstName(String name) {
        WebElement field = waitForVisible(FIRST_NAME_FIELD);
        field.clear();
        field.sendKeys(name);
    }

    public void saveProfileChanges() {
        WebElement btn = waitForClickable(SAVE_BUTTON);
        scrollToElement(btn);
        btn.click();
    }

    public boolean isOnAccountPage() {
        return getCurrentUrl().contains("blogger");
    }

    public boolean isSuccessMessageDisplayed() {
        return isPresentWithShortWait(SUCCESS_MESSAGE);
    }

    public boolean isFirstNameFieldPresent() {
        return isPresent(FIRST_NAME_FIELD);
    }
}
